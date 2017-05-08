#include	<stdio.h>
#include	<stdlib.h>
#include	<limits.h>
#include	<fcntl.h>

#define	MAX_FILES	65535

int main(int argc, char *argv[])
{	int i, nf = 0, fd[MAX_FILES];

	printf("Pid %d started ...\n",getpid());

	for( i = 0 ; i < MAX_FILES ; i++ )
	{	char fname[PATH_MAX];

		// Generates file names
		sprintf(fname,"xtmp%05d",i);

		// Try to open file
		fprintf(stderr,"Create file %s ... ",fname);
		fd[i] = open(fname,O_CREAT,0400);
		if( fd[i] < 0 )
		{	fprintf(stderr,"ERROR\n");
			perror(fname);	// Error creating tmp file
			break;
		}
		fprintf(stderr,"OK\n");
		nf++;
	}

	sleep(3600);	// Sleep for 1 hour

	exit(1);
}
