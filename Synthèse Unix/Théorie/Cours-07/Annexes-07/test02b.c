#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>

#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/msg.h>

int main(int argc, char *argv[])
{       int qid;

        qid = msgget(0x10002000,0644|IPC_CREAT);
        if( qid < 0 )
        {       perror("msgget");
                exit(-1);
        }

        fprintf(stderr,"Queue %d\n",qid);
}

