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
	union
	{	char text[MAX_LENGTH];
		int  value;
	} data;
} msg;

int main(int argc, char *argv[])
{   int qid, len, ret;

    if( argc != 2 )
    {	fprintf(stderr,"Usage: %s queue_id\n",argv[0]);
		exit(1);
    }
    qid = atoi(argv[1]);

	/* Send text message */
    msg.type = 2;
    strcpy(msg.data.text,"Hello world !");

    len = strlen(msg.data.text);
    
    ret = msgsnd(qid,&msg,len,0);
    if( ret < 0 )
    {   perror("msgsnd");
        exit(-1);
    }
	fprintf(stderr,"Written %d bytes to queue %d\n",len,qid);

	/* Send binary message */
    msg.type = 1;
	msg.data.value = 0x80000010;

    len = sizeof(msg.data.value);
    
    ret = msgsnd(qid,&msg,len,0);
    if( ret < 0 )
    {   perror("msgsnd");
        exit(-1);
    }
	fprintf(stderr,"Written %d bytes to queue %d\n",len,qid);

    exit(0);
}
// ex:ts=4
