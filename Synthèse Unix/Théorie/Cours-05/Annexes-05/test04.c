#include	<stdio.h>
#include	<stdlib.h>
#include	<unistd.h>

int main(int argc, char *argv[])
{	int i, pid, fdp[2];

	if( pipe(fdp) < 0 )
	{	perror("pipe");
		exit(1);
	}

	if( (pid = fork()) < 0 )
	{	perror("fork");
		exit(1);
	}

	if( pid )
	{	fprintf(stderr,"%5d: Parent process will write\n",getpid());
		close(fdp[0]);	// Close the reader side of the pipe
		dup2(fdp[1],1);
		for( i = 0 ; i < 5 ; i++ )
		{	printf("%5d\n",i);
#ifdef FLUSH
			fflush(stdout);
#endif
			fprintf(stderr,"%5d: Parent wrote %5d\n",getpid(),i);
			sleep(1);
		}
	 	fprintf(stderr,"%5d: Parent process ending\n",getpid());
	}
	else
	{	fprintf(stderr,"%5d: Child  process will read\n",getpid());
		close(fdp[1]);	// Close the writer side of the pipe
		dup2(fdp[0],0);
#define BUFSIZE 80
		char buffer[BUFSIZE];
		while( fgets(buffer,BUFSIZE,stdin) )
		{	fprintf(stderr,"%5d: Child  read  %s",getpid(),buffer);
		}
	 	fprintf(stderr,"%5d: Child  process ending\n",getpid());
	}
	
	exit(0);
}

// ex:ts=4
