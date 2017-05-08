#include    <stdio.h>
#include    <string.h>
#include    <stdlib.h>

#include    <sys/socket.h>
#include    <sys/types.h>

#include    <netinet/in.h>
#include	<arpa/inet.h>

#define         BUFLEN  80
static char buffer[BUFLEN];

int main(argc, argv)
int argc;
char *argv[];
{   int sck, port;
    struct sockaddr_in addr;

    if( argc != 2 )
    {   fprintf(stderr,"Usage: %s port\n",argv[0]);
        exit(1);
    }
    port = atoi(argv[1]);

    if( (sck = socket(AF_INET,SOCK_STREAM,0)) < 0 )
    {   perror("server - socket");
        exit(1);
    }

    bzero((char*)&addr,sizeof(struct sockaddr_in));
    addr.sin_family      = AF_INET;
    addr.sin_addr.s_addr = htonl(INADDR_ANY);
    addr.sin_port        = htons(port);

    if( bind(sck, (struct sockaddr *)&addr, sizeof(addr)) < 0 )
    {   perror("server - bind");
        exit(1);
    }

    fprintf(stderr,"Start listening for connections\n");
    listen(sck,1);

	int slt = 5;
    fprintf(stderr,"Sleeping for %d s ... ",slt);
    sleep(slt);
    fprintf(stderr,"\n");

    while( 1 )
    {   struct sockaddr_in addr2;
        u_int len2 = sizeof(addr2);
        int   sck2 = accept(sck,(struct sockaddr *)&addr2,&len2);
        //fprintf(stderr,"Connection from %s %d\n",inet_ntoa(addr2.sin_addr.s_addr), ntohs(addr2.sin_port));
        fprintf(stderr,"Connection from %s %d\n",inet_ntoa(addr2.sin_addr), ntohs(addr2.sin_port));
        FILE *netFd = fdopen(sck2,"r");
        while( fgets(buffer,BUFLEN,netFd) )
        {	fprintf(stderr,"%s",buffer);
			if( strncmp(buffer,"QUIT",4) == 0 )
			{	fprintf(stderr,"Closing connection\n");
				close(sck2);
				break;
			}
        }
    }

    exit(0);
}
