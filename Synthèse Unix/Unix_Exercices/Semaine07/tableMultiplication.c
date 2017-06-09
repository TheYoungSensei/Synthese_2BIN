#include <signal.h>
#include <sys/wait.h>
#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <errno.h>

#define SYS(call) ((call) == -1) ? perror(#call " : ERROR "), exit(1) : 0

#define TAILLE_REP 256
#define READ 0
#define WRITE 1
#define TRUE 1

typedef struct multiplication {
	int a;
	int b;
} multiplication;

void tropLong() {
	return;
}

int aleatoire() {
	int c = (int) ((double) rand()/(RAND_MAX) * (11 - 1)) + 1;
	return c;
}

void tempsEcoule() {
	alarm(0);
	printf("Trop lent\n");
	SYS(kill(getppid(), SIGUSR1));
	return;
}

int main(int argc, char **argv) {
	int newpid;
	struct sigaction act;
	int quest[2];
	int rep[2];
	int delai = atoi(argv[1]);
	multiplication calcul;
	char reponse[TAILLE_REP];
	int nbrCar;
	char * ptr;
	int nbrByteALire;
	int nbrByteAEcrire;
	int repOk = 0;
	int repKo = 0;
	int repRetard = 0;
	srand(time(NULL));
	SYS(pipe(quest));
	SYS(pipe(rep));
	newpid = fork();
	if(newpid == 0) {
		int questionSuivante;
		char reponse[TAILLE_REP];
		int reponseInt;
		struct timeval tpsDelai;
		fd_set ensLect;
		int retSelect;
		SYS(close(rep[READ]));
		SYS(close(quest[WRITE]));
		while(nbrCar = read(quest[READ], &calcul, sizeof(struct multiplication))) {
			if(nbrCar == -1) {
				perror("read pipe quest");
				exit(10);
			}
			questionSuivante = 0;
			printf("%d * %d = ", calcul.a, calcul.b);
			fflush(stdout);
			FD_ZERO(&ensLect);
			FD_SET(0, &ensLect);
			tpsDelai.tv_sec = delai;
			tpsDelai.tv_usec = 0;
			SYS(retSelect = select(1, &ensLect, NULL, NULL, &tpsDelai));
			if(retSelect == 0) {
				tempsEcoule();
				questionSuivante = 1;
			} else {
				if(!fgets(reponse, TAILLE_REP, stdin)) {
					if(feof(stdin)) {
						fprintf(stderr, "\n j'en ai marre\n");
						SYS(close(rep[WRITE])); /* Effet de cascade envoie CTRL D au père*/
						SYS(close(quest[READ]));
						exit(25);
					}
					if(errno == EINTR) {
						questionSuivante = 1;
						continue;
					}
					perror("error fgets fils");
					exit(14);
				}
			}
			if(questionSuivante) continue;
			reponseInt = atoi(reponse);
			if(write(rep[WRITE], &reponseInt, sizeof(int)) != sizeof(int)) {
				perror("write pipe rep");
				exit(11);
			}
		}
	} else {
		int retard;
		int reponse;
		int reponseRecue;
		SYS(close(rep[WRITE]));
		SYS(close(quest[READ]));
		act.sa_handler = tropLong;
		act.sa_flags = 0;
		SYS(sigemptyset(&act.sa_mask));
		SYS(sigaction(SIGUSR1, &act, NULL));
		while(TRUE) {
			calcul.a = aleatoire();
			calcul.b = aleatoire();
			reponse = calcul.a * calcul.b;
			retard = 0;
			if(write(quest[WRITE], &calcul, sizeof(struct multiplication)) != sizeof(struct multiplication)) {
				perror("error write quest");
				exit(13);
			}
			if((nbrCar = read(rep[READ], &reponseRecue, sizeof(int))) != 0) {
				if(nbrCar == -1) {
					if(errno == EINTR) {
						repRetard++;1
						retard = 1;
						continue;
					}
					perror("read pipe rep");
					exit(11);
				
				}
			} else {
				break;
			}
			
			if(reponseRecue == reponse) repOk++;
			else repKo++;
		}
		SYS(close(quest[WRITE]));
		SYS(close(rep[READ]));
		if(newpid == wait(NULL)) {
			printf("Reponses correctes : %d\nReponses incorrectes : %d\nReponses tardives : %d\n", repOk, repKo, repRetard);
		}
		exit(0);
	}
	
}
