#include	<stdio.h>
#include	<stdlib.h>
#include	<strings.h>
#include	<unistd.h>

#define	BUFSIZE	40
static char ibuf[BUFSIZE];

int main(int argc, char *argv[])
{
	if( isatty(0) )
		fprintf(stderr,"> ");

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
		{	fprintf(stderr,"<%s> ",pw);
			cnt++;
			ps = NULL;
		}
		if( cnt > 0 )
		 	fprintf(stderr," - %d words read\n",cnt);
		
Continue:
		if( isatty(0) )
			fprintf(stderr,"> ");
	}

	exit(0);
}
