#include <stdio.h>
#include <stdlib.h>

#include <sys/ipc.h>
#include <sys/sem.h>
#include <sys/types.h>

#define		IPL_LOCK_PATH	"/tmp/ipl.lock"
#define		IPL_NSEM		2

int sem_val_init[IPL_NSEM] = { 2, 1 };

int main(int argc, char *argv[])
{	key_t key_ipl_lock;
	int semid;
	struct sembuf sop;

	if( argc != 2 )
	{	fprintf(stderr,"Usage: %s sem\n",argv[0]);
		exit(1);
	}
	sop.sem_num = atoi(argv[1]);
	sop.sem_flg = 0;

	if( (key_ipl_lock = ftok(IPL_LOCK_PATH,0)) < 0 )
	{	perror("ftok");
		exit(1);
	}

	if( (semid = semget(key_ipl_lock,IPL_NSEM,IPC_CREAT|0644)) < 0 )
	{	perror("semget");
		exit(1);
	}

	sop.sem_op = -1;
	fprintf(stderr,"Pid %d - Trying to access ressource\n",getpid());
	if( semop(semid,&sop,1) < 0 )
	{	perror("semop");
		exit(1);
	}
	fprintf(stderr,"Pid %d - granted\n",getpid());
	sleep(5);
	sop.sem_op = 1;
	if( semop(semid,&sop,1) < 0 )
	{	perror("semop");
		exit(1);
	}
	fprintf(stderr,"Pid %d - Ressource released\n",getpid());
	
	exit(1);
}

// ex:ts=4
