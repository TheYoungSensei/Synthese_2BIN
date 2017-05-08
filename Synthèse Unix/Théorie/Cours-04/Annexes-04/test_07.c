#include    <stdio.h>
#include    <stddef.h>
#include    <fcntl.h>
#include    <sys/types.h>
#include    <sys/stat.h>

int main(int argc, char *argv[])
{   if ( fork() == 0 )
    {   int ret;
		close(1);
        if( (ret = open("test07.out",O_CREAT|O_WRONLY,0644)) < 0 )
		{	perror("open");
			exit(0);
		}
        execl("/bin/date","date",(char*)NULL);
    }
	int status;
    wait(&status);
    exit(0);
}

// ex:ts=4
