#include	<stdio.h>
#include	<sys/types.h>
#include	<sys/wait.h>

int main(int argc, char *argv[])
{	fprintf(stderr,"Main Process %d starting\n",getpid());

	int ret = fork();

	if( ret )	// I'm the parent
	{	fprintf(stderr,"Parent pid %5d - ret %5d starting\n",getpid(),ret);
	 	fprintf(stderr,"Parent pid %5d waiting ...\n",getpid());
		int status, val = wait(&status);
		int full_status = status;
		status = WEXITSTATUS(status);
	 	fprintf(stderr,"Parent pid %5d ",getpid());
		fprintf(stderr,"status 0x%08x (ret=%d) from child %d\n",full_status,status,val);
	 	fprintf(stderr,"Parent pid %d - ret %5d ending\n",getpid(),ret);
	}
	else		// I'm the child
	{	fprintf(stderr,"Child pid  %5d - ret %5d starting\n",getpid(),ret);
		sleep(5);
	 	fprintf(stderr,"Child pid  %5d - ret %5d ending\n",getpid(),ret);
		exit(32);
	}

	exit(0);
}

// ex:ts=4
