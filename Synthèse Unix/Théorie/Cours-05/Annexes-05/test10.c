#include	<stdio.h>
#include	<signal.h>
#include	<unistd.h>

extern void handler_1s(int);
static int print_it = 0;

int main(int argc, char *argv[])
{	unsigned long cur_counter = 0;
	unsigned long old_counter = 0;
	
	signal(SIGALRM,handler_1s);
	alarm(1);
	while( 1 )
	{	cur_counter++;
		if( print_it )
		{	printf("%9d\n",cur_counter - old_counter);
			old_counter = cur_counter;
			alarm(1);
			print_it = 0;
		}
	}

}

void handler_1s(int unused)
{	print_it = 1;
	return;
}

// ex:ts=4
