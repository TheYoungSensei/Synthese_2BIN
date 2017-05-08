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
		FILE *FDWriter = fdopen(fdp[1],"w");
		if( FDWriter == NULL )
		{	int status;
			perror("fdopen");
			exit(1);
		}
		for( i = 0 ; i < 4 ; i++ )
		{	
#ifndef NOCRLF
			fprintf(FDWriter,"%5d\n",i); fflush(FDWriter);
#else
		 	fprintf(FDWriter,"%5d",i); fflush(FDWriter);
#endif
			fprintf(stderr,"%5d: Parent wrote %5d\n",getpid(),i);
			sleep(1);
		}
	 	fprintf(stderr,"%5d: Parent process ending\n",getpid());
	}
	else
	{	fprintf(stderr,"%5d: Child  process will read\n",getpid());
		close(fdp[1]);	// Close the writer side of the pipe
		FILE *FDReader = fdopen(fdp[0],"r");
		if( FDReader == NULL )
		{	perror("fdopen");
			exit(1);
		}
#define BUFSIZE 80
		char buffer[BUFSIZE];
		while( fgets(buffer,BUFSIZE,FDReader) )
		{	fprintf(stderr,"%5d: Child  read  %s",getpid(),buffer);
		}
	 	fprintf(stderr,"\n%5d: Child  process ending\n",getpid());
	}
	
	exit(0);
}

// ex:ts=4
