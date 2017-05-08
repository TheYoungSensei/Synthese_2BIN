#include	<stdio.h>
#include	<stdlib.h>

void myOwnPrivateExit()
{	fprintf(stdout,"Giving up !\n");
	return;
}

int main(int argc, char *argv[])
{	atexit(myOwnPrivateExit);

	fprintf(stderr,"Main Process %d starting\n",getpid());
	fprintf(stdout,"Hello world ... ");

#ifdef _EXIT
	_exit(8);
#else
	exit(-1);
#endif
}
