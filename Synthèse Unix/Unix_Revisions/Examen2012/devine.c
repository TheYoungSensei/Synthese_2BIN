#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <string.h>

/* Macros éventuelles */

#define SYS(call) ((call) == -1) ? perror(#call " : ERROR"), exit(1) : 0

/* déclarations globales */

#define FALSE 0
#define TRUE 1
#define MAX 2
#define MAX_CAR 256

/* routine du traitement SIGINT */


void fils() {
	exit(0);
}

void pere() {
	return;
}

int generer(int nb) {
	return (int) (rand() / (RAND_MAX + 1.0) * (nb - 1) + 2);
}

int main(int argc, char **argv) {
	/* déclarations communes au père et au fils */
	int max;
	char *p;
	int pipePereFils[2];
	int pipeFilsPere[2];
	int pid;
	int i;
	/* traitement des arguments */
	if(argc != 2) {
		fprintf(stderr, "usage : %s max", argv[0]);
		exit(1);
	}
	max = strtoul(argv[1], &p, 10);
	if(*p != '\0') {
		fprintf(stderr, "le nombre de nombres a deviner doit être un entier positif\n");
		exit(2);
	}
	/* création du fils et des moyens de communication avec lui */
	SYS(pipe(pipePereFils));
	SYS(pipe(pipeFilsPere));
	SYS(pid = fork());
	/* traitement du père */
	if(pid) { /* père */
		/* déclarations propre au père */
		struct sigaction sa;
		sigset_t set;
		int *tabNombres;
		int nombreCorrects = 0;
		int nombreEssais = 0;
		char ligne[256];
		int nbrLu;
		int fin = 0;
		/* installation du traitement du signal SIGINT */
		/* A traiter dans le fils mais a bloquer dans le père */
		sa.sa_handler = pere;	
		sa.sa_flags = 0;
		SYS(sigemptyset(&sa.sa_mask));
		SYS(sigfillset(&set));
		SYS(sigprocmask(SIG_BLOCK, &set, NULL));
		/* gestion des moyens de communication */
		SYS(close(pipePereFils[0]));
		SYS(close(pipeFilsPere[1]));
		/* génération des questions */
		tabNombres = (int *) malloc(max * sizeof(int));
		srand(time(NULL));
		for(i = 0; i < max; i++) {
			int nombre = generer(MAX);
			/* envoi du message nouveau nombre au fils */
			char message[256] = "Devine un nouveau nombre\n";
			int compteur = 0;
			SYS(write(pipePereFils[1], message, strlen(message)));
			/* boucle d'essais */
			while(1) {
				/* déclarations éventuelles */
				int buffer;
				char pourMonFils[256];
				/* lire la proposition venant du fils */
				SYS(nbrLu = read(pipeFilsPere[0], &buffer, sizeof(int)));
				/* verifier si le fils a termine */
				if(nbrLu == 0) {
					fin = 1;
					break;
				}
				/* analyse de la proposition et contruction du message */
				if(buffer == nombre) {
					compteur++; /* Au moins 1 essai */
					sprintf(ligne, "Vous avez deviné le nombre %d en %d essais\n", nombre, compteur);
					SYS(write(1, ligne, strlen(ligne)));
					strcpy(pourMonFils, "Bravo, vous avez bien devine\n");
					nombreCorrects++;
					nombreEssais += compteur;
					break;
				} else if(nombre % buffer == 0) {
					strcpy(pourMonFils, "Vous avez propose un diviseur du nombre a deviner\n");
					compteur++;
				} else if (buffer % nombre == 0) {
					strcpy(pourMonFils, "Vous avez propose un multiple du nombre a deviner\n");
					compteur++;
				} else {
					strcpy(pourMonFils, "Votre proposition n'est ni un multiple ni un diviseur\n");
					compteur++;
				}
				/* envoyer le message au fils */
				SYS(write(pipePereFils[1], pourMonFils, strlen(pourMonFils)));
			}
			/* verifier si le fils a termine */
			if(fin == 1) {
				break;
			}
		} /* fin de la boucle de traitement du père */
		/* affichage des statistiques */
		if(nombreEssais != 0) { /* Division par 0 non autorisée ici */
			sprintf(ligne, "Vous avez devine %d nombres avec en moyenne %d essais par nombre\n", nombreCorrects, nombreEssais/ nombreCorrects);
		} else {
			sprintf(ligne, "Vous avez %d nombres avec en moyenne 0 essais par nombre\n", nombreCorrects);
		}
		SYS(write(1, ligne, strlen(ligne)));
		/* prevenir le fils que la session est finie */
		SYS(close(pipePereFils[1]));
		/* attendre la fin du fils */
		SYS(nbrLu = read(pipeFilsPere[0], ligne, MAX_CAR));
		if(nbrLu != 0) {
			perror("Erreur Fin Fils\n");
			exit(1);
		}
		SYS(close(pipeFilsPere[0]));
		SYS(sigprocmask(SIG_UNBLOCK, &set, NULL));
		exit(0);
		} /* fin du père */
		/*traitement du fils */
		/* declarations propres au fils */
		struct sigaction sa;
		sigset_t set;
		int fin = 1;
		/*installation du traitement du signal SIGINT */
		/* a faire ici (le fils s'occupe du clavier) */
		sa.sa_handler = fils;
		sa.sa_flags = 0;
		SYS(sigemptyset(&sa.sa_mask));
		SYS(sigfillset(&set));
		SYS(sigdelset(&set, SIGINT));
		SYS(sigprocmask(SIG_BLOCK, &set, NULL));
		/* gestion des moyens de communications */
		SYS(close(pipePereFils[1]));
		SYS(close(pipeFilsPere[0]));
		/* boucle de traitement */
		while(1) {
			/* declarations eventuelles */
			int lu;
			char ligne[256];
			char buffer[256];
			int pourMonPere;
			int trouve = 0;
			/* afficher le message du père */
			SYS((lu = read(pipePereFils[0], ligne, 255)));
			if(lu == 0)
				break;
			ligne[lu] = '\0';
			printf("%s", ligne);
			do { /* boucle de traitement du nombre courant */
				trouve = 0;
				/* attendre la proposition de l'utilisateur */
				SYS(lu = read(0, buffer, MAX_CAR));
				if(lu == 0) {
					fin = 0;
					continue;
				} 
				if(!strcmp(ligne, "<return>")) {
					fin = 0;
					continue;
				}	
				/* vérifier que c'est un entier entre 2 et MAX */
				pourMonPere = atoi(buffer);
				if(pourMonPere < 2) {
					strcpy(ligne, "Le nombre est trop petit\n");
					SYS(write(0, ligne, strlen(ligne)));
					continue;
				} else if (pourMonPere > 20) {
					strcpy(ligne, "Le nombre est trop grand\n");
					SYS(write(0, ligne, strlen(ligne)));
					continue;
				}
				/* envoyer la proposition au père */
				SYS(write(pipeFilsPere[1], &pourMonPere, sizeof(int)));
				/* lire le message envoyé du père */
				SYS(lu = read(pipePereFils[0], ligne, MAX_CAR));
				ligne[lu] = '\0';
				if(lu == 0) {
					fin = 0;
					continue;
				}
				write(1, ligne, strlen(ligne));
				if(!strcmp(ligne, "Bravo, vous avez bien devine\n")) {
					trouve = 1;
				}
			} while (fin == 1 && trouve == 0); 
			/* detecter fin du fils */
			if(fin == 0) {
				break;
			}
		} /* fin de la boucle de traitement du fils */
		SYS(sigprocmask(SIG_UNBLOCK, &set, NULL));
		/* terminer propement */
		SYS(close(pipePereFils[0]));
		SYS(close(pipeFilsPere[1]));
		exit(0);
}
