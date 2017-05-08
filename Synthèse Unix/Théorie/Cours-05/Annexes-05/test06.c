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
	{	close(fdp[0]);	// Close the reader side of the pipe
		dup2(fdp[1],1);
		close(fdp[1]);	// Unused
		for( i = 0 ; i < 80 ; i++ )
		{	printf("%5d\n",i);
			fflush(stdout);
		}
		close(1);		// Close stdout so the child doesn't wait data
		int status = 0;
		wait(&status);
	}
	else
	{	close(fdp[1]);	// Close the writer side of the pipe
		dup2(fdp[0],0);
		if( execl("/bin/more","MyOwnMore","-c",(char*)0) < 0 )
		{	perror("execl");
			exit(1);
		}
	}
	
	exit(0);
}

// ex:ts=4
