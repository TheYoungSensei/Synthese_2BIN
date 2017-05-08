#include    <stdio.h>
#include    <stddef.h>
#include    <fcntl.h>
#include    <sys/types.h>
#include    <sys/stat.h>

int main(int argc, char *argv[])
{   if ( fork() == 0 )
    {   int ret;
		execl("/bin/sh","sh","-c","ls /etc | wc > test_08.out",(char*)NULL);
    }
    wait();
    exit(0);
}

// ex:ts=4
