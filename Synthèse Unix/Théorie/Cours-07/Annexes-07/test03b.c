#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/msg.h>

#define MAX_LENGTH  512

struct Message
{   long type;
    char text[MAX_LENGTH];
} msg;

int main(int argc, char *argv[])
{   int qid, len, ret;

    qid = msgget(0x10002000,0644|IPC_CREAT);
    if( qid < 0 )
    {       perror("msgget");
            exit(-1);
    }

    msg.type = 1;
    strcpy(msg.text,"Hello world !");

    len = strlen(msg.text);
    
    ret = msgsnd(qid,&msg,len,0);
    if( ret < 0 )
    {   perror("msgsnd");
        exit(-1);
    }
    
    exit(0);
}
// ex:ts=4
