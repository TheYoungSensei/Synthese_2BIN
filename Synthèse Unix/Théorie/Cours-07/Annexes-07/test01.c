#include    <sys/types.h>
#include    <unistd.h>
#include    <stdio.h>
#include    <stdlib.h>

int main(int argc, char *argv[])
{   int status, nproc, procmax, pid = 0;
    int fd1[2], fd2[2];
    int token = 0x1001;

    setbuf(stderr,NULL);

    if( argc != 2 )
    {   fprintf(stderr,"Usage: %s n\n",argv[0]);
        exit(1);
    }

    procmax = atoi(argv[1]);
    nproc   = 0;

    while( pid == 0 && nproc++ < procmax )
    {   if( pipe(fd1) < 0 )
        {   perror("pipe1");
            exit(1);
        }

        if( nproc == 1 && pipe(fd2) < 0 )
        {   perror("pipe2");
            exit(1);
        }

        if( (pid = fork()) < 0 )
        {   perror("fork");
            exit(1);
        }

        if( pid )   // Parent
        {   close(fd1[0]);  // Parent close fd1 reader
            write(fd1[1],&token,sizeof(int));
            fprintf(stderr,"Parent %d - child %d - written 0x%08x\n",getpid(),pid,token);
            if( nproc == 1 )
            {   close(fd2[1]);
                read(fd2[0],&token,sizeof(int));
                fprintf(stderr,"Main parent %d - read 0x%08x\n",getpid(),token);
            }
            wait(&status);
        }
        else
        {   if( nproc == 1 )
                close(fd2[0]);  // Child close fd2 reader
            read(fd1[0],&token,sizeof(int));
            token++;
            if( nproc == procmax )
                write(fd2[1],&token,sizeof(int));
            else
                write(fd1[1],&token,sizeof(int));
        }
    }


    exit(0);
}
