#include	<stdio.h>
#include	<sys/types.h>
#include	<sys/stat.h>
#include	<fcntl.h>

int main(int argc, char *argv[])
{	int fd;

	if( (fd = open("messages",O_RDONLY)) == -1 )
	{	perror("messages");
		exit(1);
	}
	fprintf(stdout,"Pid %d - fd = %d\n",getpid(),fd);

	sleep(1000);

	close(fd);
	exit(0);
}
