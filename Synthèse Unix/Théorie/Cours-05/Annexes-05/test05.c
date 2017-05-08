#include	<stdio.h>
#include	<stdlib.h>
#include	<signal.h>

void handler(int arg)
{	fprintf(stderr,"Got SIGPIPE... \n");
	exit(1);
}

int main(int argc, char *argv[])
{	int i = 1, pid, fdp[2];

    if( signal(SIGPIPE,handler) < 0 )
	{	perror("signal");
		exit(1);
	}

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
#ifdef CLOSE
	   	close(fdp[0]);	// Close the reader side of the pipe
#endif
		dup2(fdp[1],1);
		while( i++ )
		{	printf("%5d\n",i); fflush(stdout);
			fprintf(stderr,"%5d: Parent wrote %5d\n",getpid(),i);
		}
	 	fprintf(stderr,"%5d: Parent process ending\n",getpid());
	}
	else
	{	fprintf(stderr,"%5d: Child  process will read\n",getpid());
		close(fdp[1]);	// Close the writer side of the pipe
		sleep(1);
	 	fprintf(stderr,"%5d: Child  process ending\n",getpid());
	}
	
	exit(0);
}

// ex:ts=4
