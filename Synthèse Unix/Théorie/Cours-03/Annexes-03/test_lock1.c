#include	<stdio.h>
#include	<fcntl.h>
#include	<stdlib.h>

int main(int argc, char *argv[])
{	
	fprintf(stderr,"%d: Trying to acquire lock\n",getpid());
	while( creat("lockfile1",0) < 0 )
									sleep(1);
	fprintf(stderr,"%d: Start locking\n",getpid());
	sleep(10);

	unlink("lockfile1");
	fprintf(stderr,"%d: End locking\n",getpid());

	exit(0);
}
