#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>

#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/msg.h>

int main(int argc, char *argv[])
{   int qid;

    qid = msgget(IPC_PRIVATE,0644);
    if( qid < 0 )
    {   perror("msgget");
        exit(-1);
    }

    fprintf(stderr,"Queue Id %d\n",qid);
}
// ex:ts=4
