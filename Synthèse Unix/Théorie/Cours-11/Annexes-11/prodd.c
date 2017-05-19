#include	<stdio.h>
#include	<stdlib.h>

#include	"prodd.h"

int main(int argc, char *argv[])
{	int n;

	if( argc != 2 )
	{	fprintf(stderr,"Usage: %s n\n",argv[0]);
		exit(-1);
	}

	n = atoi(argv[1])%NUMBER;

	exit(n);
}
