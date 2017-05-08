#include	<stdio.h>
#include	<signal.h>
#include	<stdlib.h>

int counter = 16;

/*
 * According to Section 7.14.1.1 (signals) of the C Standard
 * [ISO/IEC 9899:2011], returning from a SIGSEGV, SIGILL, or
 * SIGFPE signal handler is undefined behavior:
 *
 * If and when the function returns, if the value of sig is
 * SIGFPE, SIGILL, SIGSEGV, or any other implementation-defined
 * value corresponding to a computational exception, the
 * behavior is undefined; otherwise, the program will resume
 * execution at the point it was interrupted.
 * 
 * Furthermore, SIGFPE may not be caught for a significant
 * number of instructions after the floating-point instruction
 * that creates it.
 */

void handler_int(int arg)
{	fprintf(stderr,"Got INT enter handler ... \n");
	sleep(2);
 	fprintf(stderr,"Exit handler ... \n");
	return;
}

void handler_fpe(int arg)
{	fprintf(stderr,"Zero divide ... counter=%d\n",counter);
	/* The only compliant way to leave FPE handler */
	abort();
	return;
}

int main(int argvc, char argv[0])
{	
	if( signal(SIGINT,handler_int) < 0 )
	{	perror("signal");
		exit(1);
	}

	if( signal(SIGFPE,handler_fpe) < 0 )
	{	perror("signal");
		exit(1);
	}

	myTestRoutine();

	exit(0);
}

int myTestRoutine()
{	int loop = 0;

	while( counter-- )
	{	int j = 1000 / counter;
		loop++;
		fprintf(stderr,"%2d: 1/%d = %d\n",loop,counter,j);
		sleep(1);
	}

	return 0;
}
	
// ex:ts=4
