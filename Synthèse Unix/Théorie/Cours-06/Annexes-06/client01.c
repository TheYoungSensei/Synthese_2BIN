#include    <netdb.h>
#include    <stdio.h>
#include    <string.h>
#include    <stdlib.h>

#include    <sys/socket.h>
#include    <sys/types.h>

#include    <netinet/in.h>

#define       BUFSIZE  80
static char ibuf[BUFSIZE];

extern void err_handler(int);

int main(argc, argv)
int argc;
char *argv[];
{   int sck, port;
    struct sockaddr_in addr;
	struct hostent *host;

	signal(SIGPIPE,err_handler);

    if( argc != 3 )
    {   fprintf(stderr,"Usage: %s ip port\n",argv[0]);
        exit(1);
    }

    if( (sck = socket(AF_INET,SOCK_STREAM,0)) < 0 )
    {   perror("server - socket");
        exit(1);
    }

	host = gethostbyname(argv[1]);
    port = atoi(argv[2]);

	if( host == NULL )
	{	fprintf(stderr,"Unknown host\n");
		exit(1);
	}
	
    bzero((char*)&addr,sizeof(struct sockaddr_in));
    addr.sin_family      = AF_INET;
	bcopy(host->h_addr,(char*)&addr.sin_addr.s_addr,host->h_length);
    addr.sin_port        = htons(port);

    if( connect(sck, (struct sockaddr *)&addr, sizeof(addr)) < 0 )
    {   perror("client - connect");
        exit(1);
    }
	FILE *fd = fdopen(sck,"w");
	setbuf(fd,NULL);

    if( isatty(0) )
	    fprintf(stderr,"Connected\n> ");

    while(  fgets(ibuf,BUFSIZE,stdin) != NULL )
    {   int l = strlen(ibuf);

        /* Null command */
        if( l <= 1 )
            goto Continue;

        /* Comment line */
        if( ibuf[0] == '#' )
            goto Continue;

        /* Replace the leading '\n' by '\0' */
        if( ibuf[l-1] == '\n' )
                    ibuf[l-1] = '\0';
        else
            fpurge(stdin);

#define ARG_SEP " \t"
        char *pw, *ps = ibuf;
        int cnt = 0;
        while( (pw = strtok(ps,ARG_SEP)) != NULL )
        {   fprintf(fd,"%s ",pw);
            cnt++;
            ps = NULL;
        }
        if( cnt > 0 )
            fprintf(fd," - %d words transmitted\n",cnt);

Continue:
        if( isatty(0) )
            fprintf(stderr,"> ");
    }
 exit(0);
}

void err_handler(int unused)
{   fprintf(stderr,"Connection broken !\n");
    return;
}

