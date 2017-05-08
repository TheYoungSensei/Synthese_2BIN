#include	<stdio.h>

#define	BUFSIZE	80
static char buf[BUFSIZE];

int main(int argc, char *argv[])
{
	fprintf(stderr,"Enter time: ");
	fgets(buf,BUFSIZE,stdin);
	int sl_time = atoi(buf);

	fprintf(stderr,"Will go to sleep for %d s\n",sl_time);
	
	while( sl_time-- > 0 )
	{	printf("%5d\n",sl_time);
		sleep(1);
		
	}

	exit(0);
}
