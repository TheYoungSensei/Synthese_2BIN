#include	<fcntl.h>
#include	<stdio.h>
#include	<string.h>
#include	<unistd.h>
#include	<stdlib.h>
#include	<sys/types.h>
#include	<sys/stat.h>

int main(int argc, char *argv[])
{	int fd1;
	char *m1 = "Simple line from write !\n";
	int   l1 = strlen(m1);

	fd1 = open("tmpfile",O_WRONLY|O_CREAT,0644);

	write(fd1,m1,l1);	/* Write to file    */
	write(  1,m1,l1);	/* Write to stdout  */

#ifdef DUP2
	printf("Atomic dup2 !\n");
 	dup2(fd1,1);
#else
	printf("Dual close/dup !\n");
	close(1);
	dup(fd1);
#endif

	printf("This line printed to the standard input !\n");
					/* but will reallly be printed on tmpfile !! */
	
	exit(0);
}
