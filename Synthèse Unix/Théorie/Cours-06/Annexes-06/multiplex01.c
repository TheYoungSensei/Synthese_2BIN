#include	<fcntl.h>
#include	<strings.h>
#include	<stdio.h>
#include	<stdlib.h>

#include	<sys/select.h>

#define BUFLEN 	80
static char buffer[BUFLEN];

int main(int argc, char *argv[])
{	int fd1, fd2;
	fd_set fdset;
        static struct timeval tv;
	
	fprintf(stderr,"Starting ... \n");

	if( (fd1 = open("/tmp/bar1",O_RDONLY|O_NONBLOCK)) < 0 )
	{	perror("open bar1");
		exit(1);
	}
	fprintf(stderr,"Bar 1 opened - fd = %d\n",fd1);

	if( (fd2 = open("/tmp/bar2",O_RDONLY|O_NONBLOCK)) < 0 )
	{	perror("open bar2");
		exit(1);
	}
	fprintf(stderr,"Bar 2 opened - fd = %d\n",fd2);

	FD_ZERO(&fdset);

	while( 1 )
	{	int ret;

		FD_SET(fd1,&fdset);
		FD_SET(fd2,&fdset);

		tv.tv_sec  = 2;
		tv.tv_usec = 500000;

		ret = select(8,&fdset,NULL,NULL,&tv);
		fprintf(stderr,"select: ret = %d\n",ret);
		if( ret > 0 )
		{
			if( FD_ISSET(fd1,&fdset) )
			{	int ret2;
				if( (ret2 = read(fd1,buffer,BUFLEN)) > 0 )
				 	fprintf(stderr,"1: %d - %s",ret2,buffer);
				else
					fprintf(stderr,"1: null\n");
			}
			else
					fprintf(stderr,"1: empty\n");

			if( FD_ISSET(fd2,&fdset) )
			{	int ret2;
				if( (ret2 = read(fd2,buffer,BUFLEN)) > 0 )
					fprintf(stderr,"2: %d - %s",ret2,buffer);
				else
					fprintf(stderr,"2: null\n");
			}
			else
					fprintf(stderr,"2: empty\n");

			sleep(1);
		}
	}

	exit(0);
}
