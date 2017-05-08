#include	<stdio.h>

int main(int argc, char *argv[])
{	fprintf(stderr,"Main Process %d starting\n",getpid());

	int ret = fork();

	if( ret )	// I'm the parent
	{	fprintf(stderr,"Parent pid = %d - ret = %d starting\n",getpid(),ret);
#ifdef ZOMBIE
		sleep(15);
#endif
	 	fprintf(stderr,"Parent pid = %d - ret = %d ending\n",getpid(),ret);
	}
	else		// I'm the child
	{	fprintf(stderr,"Child pid = %d  - ret = %d starting\n",getpid(),ret);
#ifdef INIT
		sleep(15);
#endif
	 	fprintf(stderr,"Child pid = %d  - ret = %d ending\n",getpid(),ret);
	}

	exit(0);
}

// ex: ts=4
