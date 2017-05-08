#include	<unistd.h>
#include	<stdio.h>
#include	<stdlib.h>

int main(int argc, char *argv[])
{	int pid = 0, nproc = 8;

	while( pid == 0 && nproc-- > 0 )
	{
		if( (pid = fork()) < 0 )
		{	perror("fork");
			exit(1);
		}

		if( pid )
		{	int status;
			fprintf(stderr,"Parent %d - child %d\n",getpid(),pid);
			wait(&status);
		}
		else
		{	fprintf(stderr,"Child %d - nproc = %d\n",getpid(),nproc);
			sleep(5);
		}
	}

}
// ex:ts=4
