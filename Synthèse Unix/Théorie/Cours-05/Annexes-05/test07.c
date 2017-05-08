#include	<stdio.h>
#include	<stdlib.h>

int main(int argc, char *argv[])
{	FILE *fd;
	int i;

	if( (fd = popen("/bin/more -c","w")) == NULL )
	{	perror("popen");
		exit(1);
	}

	for( i = 0 ; i < 80 ; i++ )
	{	fprintf(fd,"%5d\n",i);
		fflush(fd);
	}

	pclose(fd);

	exit(0);
}

// ex:ts=4
