#include	<stdio.h>
#include	<stdlib.h>

int main(int argc, char **argv)
{	char *str;

	if( (str = getenv("GLOVAR")) != NULL )
	 	printf("%s: Global variable is %s\n",argv[0],str);
	else
	 	printf("%s: Global variable unset\n",argv[0]);

	printf("%s pid = %d\n",argv[0],getpid());
        sleep(120);

	exit(0);
}
