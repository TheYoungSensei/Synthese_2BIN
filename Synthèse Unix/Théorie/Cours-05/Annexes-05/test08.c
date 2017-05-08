#include	<stdio.h>
#include	<stdlib.h>

int main(int argvc, char argv[0])
{	myTestRoutine();
	exit(0);
}

int myTestRoutine()
{	int counter = 10;
	int loop = 0;
	while( counter-- )
	{	int j = 1 / counter;
		fprintf(stderr,"%d\n",j);
		loop++;
	}

	return 0;
}
	
// ex:ts=4
