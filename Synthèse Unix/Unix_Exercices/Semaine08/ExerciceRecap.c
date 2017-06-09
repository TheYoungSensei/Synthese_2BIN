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
	SYS(kill(getppid(), SIGUSR1));
	return;
}

void tempsPresqueEcoule(int pidSouffleur) {
	printf("\nTemps Presque Ecoule\n");
	alarm(0);
	SYS(kill(pidSouffleur, SIGUSR2));
	return;
}
		

int main(int argc, char **argv) {
	int pidEleve;
	int pidSouffleur;
	struct sigaction act;
	int quest[2];
	int rep[2];
	int ok[2];
	int psss[2];
	multiplication calcul;
	char reponse[TAILLE_REP];
	int nbrCar;
	char * ptr;
	int nbrByteALire;
	int nbrByteAEcrire;
	int repOk = 0;
	int repKo = 0;
	int repRetard = 0;
	int delai;
	if(argc != 2) {
		printf("exericeRecapitulatif [tempsReponse]");
		exit(15);
	}
	delai  = atoi(argv[1]);
	srand(time(NULL));
	SYS(pipe(ok));
	SYS(pipe(psss));
	pidSouffleur = fork();
	if(pidSouffleur == 0) {
		int nbrTard = 0;
		int reponseInt;
		SYS(close(ok[WRITE]));
		SYS(close(psss[READ]));
		act.sa_handler = tropLong;
		act.sa_flags = 0;
		SYS(sigemptyset(&act.sa_mask));
		SYS(sigaction(SIGUSR2, &act, NULL));
		while(nbrCar = read(ok[READ], &reponseInt, sizeof(int))) {
			if(nbrCar == -1) {
				if(errno == EINTR) {
					sleep(1);
					if(write(psss[WRITE], &reponseInt, sizeof(int)) != sizeof(int)) {
						perror("error write psss");
						exit(11);
					}
					nbrTard++;
					continue;
				}	 
				perror("read pipe ok");
				exit(10);
			}
		}
		printf("Nombre réponses soufflées : %d\n", nbrTard);
		exit(25);
	} else {
		SYS(pipe(quest));
		SYS(pipe(rep));
		pidEleve = fork();
		if(pidEleve == 0) {
			int questionSuivante;
			char reponse[TAILLE_REP];
			int reponseInt;
			struct timeval tpsDelai;
			struct timeval tpsCorbeille;
			fd_set ensLect;
			int retSelect;
			int reponseSouffleur;
			SYS(close(rep[READ]));
			SYS(close(quest[WRITE]));
			SYS(close(psss[WRITE]));
			SYS(close(ok[WRITE]));
			SYS(close(ok[READ]));
			while(nbrCar = read(quest[READ], &calcul, sizeof(struct multiplication))) {
				FD_ZERO(&ensLect);
				FD_SET(0, &ensLect);
				tpsCorbeille.tv_sec = 0;
				tpsCorbeille.tv_usec = 0;
				if(select(1, &ensLect, NULL, NULL, &tpsCorbeille) != 0) {
					read(psss[READ], &reponseInt, sizeof(int));
				}
				if(nbrCar == -1) {
					perror("read pipe quest");
					exit(10);
				}
				questionSuivante = 0;
				printf("%d * %d = ", calcul.a, calcul.b);
				fflush(stdout);
				FD_ZERO(&ensLect);
				FD_SET(0, &ensLect);
				tpsDelai.tv_sec = delai - 1;
				tpsDelai.tv_usec = 0;
				SYS(retSelect = select(1, &ensLect, NULL, NULL, &tpsDelai));
				if(retSelect == 0) {
					tempsPresqueEcoule(pidSouffleur);
					FD_ZERO(&ensLect);
					FD_SET(0, &ensLect);
					FD_SET(psss[READ], &ensLect);
					tpsDelai.tv_sec = 5;
					SYS(retSelect = select(psss[READ] + 1, &ensLect, NULL, NULL, &tpsDelai));
					if(FD_ISSET(0, &ensLect)) {
						if(!fgets(reponse, TAILLE_REP, stdin)) {
							if(feof(stdin)) {
								fprintf(stderr, "\nLe souffleur est meilleur que moi\n");
								SYS(close(rep[WRITE])); /* Effet de cascade envoie CTRL D au père*/
								SYS(close(quest[READ]));
								exit(25);
							}
							perror("error fgets fils");
							exit(14);
						}
						reponseInt = atoi(reponse);
					} else {
						if(!read(psss[READ], &reponseInt, sizeof(int))) {
							perror("error fgets fils");
							exit(14);						
						}
					}
					//questionSuivante = 1;
				} else {
					if(!fgets(reponse, TAILLE_REP, stdin)) {
						if(feof(stdin)) {
							fprintf(stderr, "\nLe fils s'en va vers l'horizon\n");
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
					reponseInt = atoi(reponse);
				}
				if(questionSuivante) continue;
				if(write(rep[WRITE], &reponseInt, sizeof(int)) != sizeof(int)) {
					perror("write pipe rep");
					exit(11);
				}
			}			
		} else {
			int retard;
			int reponseCalcul;
			int reponseInt;
			SYS(close(rep[WRITE]));
			SYS(close(quest[READ]));
			SYS(close(ok[READ]));
			act.sa_handler = tropLong;
			act.sa_flags = 0;
			SYS(sigemptyset(&act.sa_mask));
			SYS(sigaction(SIGUSR1, &act, NULL));
			while(TRUE) {
				calcul.a = aleatoire();
				calcul.b = aleatoire();
				reponseCalcul = calcul.a * calcul.b;
				retard = 0;
				if(write(quest[WRITE], &calcul, sizeof(struct multiplication)) != sizeof(struct multiplication)) {
					perror("error write quest");
					exit(13);
				}
				if(write(ok[WRITE], &reponseCalcul, sizeof(int)) != sizeof(int)) {
					perror("error write ok");
					exit(13);
				}
				if((nbrCar = read(rep[READ], &reponseInt, sizeof(int)))) {
					if(nbrCar == -1) {
						if(errno == EINTR) {
							repRetard++;
							retard = 1;
							continue;
						}
						perror("read pipe rep");
						exit(11);
					}
				} else {
					break;
				}
				if(reponseInt == reponseCalcul) repOk++;
				else repKo++;
			}
			SYS(close(quest[WRITE]));
			SYS(close(rep[READ]));
			if(pidEleve == wait(NULL)) {
				printf("Reponses correctes : %d\nReponses incorrectes : %d\n", repOk, repKo);
			}
			SYS(close(ok[WRITE]));
			exit(0);
			}
		}

	}

