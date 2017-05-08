#include	<stdio.h>
#include	<unistd.h>
#include	<sys/types.h>
#include	<sys/stat.h>
#include	<fcntl.h>
#include	<stdlib.h>

int main(int argc, char *argv[])
{	if( argc !=  3 )
	{	fprintf(stderr,"Usage: %s start end\n",argv[0]);
		exit(1);
	}

	int start = atoi(argv[1]);
	int end   = atoi(argv[2]);

	int fd = open("lockedfile",O_CREAT|O_RDWR,0600);
	if( fd < 0 )
	{	perror("open");
		exit(1);
	}

	if( lseek(fd,start,SEEK_SET) < 0 )
	{	perror("lseek");
		exit(1);
	}

	fprintf(stderr,"%d: Trying to acquire lock on %d from %4d to %4d\n",
											getpid(),fd,start,end);

	if( lockf(fd,F_LOCK,end-start) < 0 )
	{	perror("lockf1");
		exit(1);
	}

	fprintf(stderr,"%d: Start locking on %d from %4d to %4d\n",
											getpid(),fd,start,end);

	sleep(10);

	fprintf(stderr,"%d: End locking on %d\n",getpid(),fd);
	if( lockf(fd,F_LOCK,end-start) < 0 )
	{	perror("lockf2");
		exit(1);
	}

	close(fd);

	exit(0);
}
