#include <stdio.h>

#include <sys/ipc.h>
#include <sys/sem.h>
#include <sys/types.h>

#define		IPL_LOCK_PATH	"/tmp/ipl.lock"
#define		IPL_NSEM		2

unsigned short sem_val_init[IPL_NSEM] = { 2, 1 };

int main(int argc, char *argv[])
{	key_t key_ipl_lock;
	int semid;

	if( (key_ipl_lock = ftok(IPL_LOCK_PATH,0)) < 0 )
	{	perror("ftok");
		exit(1);
	}

	if( (semid = semget(key_ipl_lock,IPL_NSEM,IPC_CREAT|0644)) < 0 )
	{	perror("semget");
		exit(1);
	}

	if( semctl(semid,0,SETALL,sem_val_init) < 0 )
	{	perror("semctl");
		exit(1);
	}
	
	while( 1 )
	{	int pid0, pid1, ncnt0, ncnt1, zcnt0, zcnt1;
		semctl(semid,0,GETALL,sem_val_init);
		pid0  = semctl(semid,0,GETPID);
		pid1  = semctl(semid,1,GETPID);
		ncnt0 = semctl(semid,0,GETNCNT);
		ncnt1 = semctl(semid,1,GETNCNT);
		zcnt0 = semctl(semid,0,GETZCNT);
		zcnt1 = semctl(semid,1,GETZCNT);
		fprintf(stderr,"%2d %2d %2d (%5d)	%2d %2d %2d (%5d)\n",
								sem_val_init[0],ncnt0,zcnt0,pid0,
								sem_val_init[1],ncnt1,zcnt1,pid1);
		sleep(1);
	}
}

// ex:ts=4
