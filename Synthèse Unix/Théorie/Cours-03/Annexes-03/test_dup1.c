#include	<stdio.h>
#include	<string.h>
#include	<unistd.h>
#include	<stdlib.h>
#include	<fcntl.h>

int main(int argc, char *argv[])
{	int fd1, fd2, fd3;

	fd1 = open("tmpfile",O_RDWR|O_TRUNC|O_CREAT,0644);
	printf("fd1 = %d (check file permission !)\n",fd1);
	write(fd1,"What's",6);

	fd2 = dup(fd1);
	printf("fd2 = %d (same file)\n",fd2);
	write(fd2," up",3);
	
	close(0);	/* Do the same with close(1) */

	fd3 = dup(fd1);
	printf("fd3 = %d (the lowest fd available)\n",fd3);
	write(fd3," doc ?\n",7);

	sleep(120);

	exit(0);
}
