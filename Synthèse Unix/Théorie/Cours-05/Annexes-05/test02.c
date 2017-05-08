#include	<unistd.h>
#include	<stdio.h>
#include	<stdlib.h>

int main(int argc, char *argv[])
{	int fd1[2], fd2[2];
	int token, pid;

	if( pipe(fd1) < 0 )
	{	perror("pipe1");
		exit(1);
	}

	if( pipe(fd2) < 0 )
	{	perror("pipe2");
		exit(1);
	}

	if( (pid = fork()) < 0 )
	{	perror("fork");
		exit(1);
	}

	if( pid )			// Parent read on fd1 write on fd2
	{	close(fd1[1]);	// Close write side
		close(fd2[0]);	// CLose read side

		fprintf(stderr,"Parent starting\n");
		token = 0;
		while( 1 )
		{	write(fd2[1],(char*)&token,sizeof(token));
			read(fd1[0],(char*)&token,sizeof(token));
			fprintf(stderr,"%4d\n",token);
			sleep(1);
		}
	}
	else				// Child write on fd1 read on fd2
	{	fprintf(stderr,"Child starting\n");
		close(fd1[0]);	// Close read side
		close(fd2[1]);	// Close write side

		while( 1 ) 
		{	read(fd2[0],(char*)&token,sizeof(token));
			token++;
			write(fd1[1],(char*)&token,sizeof(token));
		}
	}
	
	exit(0);
}

// ex:ts=4
