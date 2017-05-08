#include	<stdio.h>

int main(int argc, char *argv[])
{	fprintf(stderr,"Process %d starting\n",getpid());
	sleep(120);
	exit(1);
}
