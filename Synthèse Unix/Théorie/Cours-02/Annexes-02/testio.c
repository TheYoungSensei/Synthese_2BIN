#include <sys/types.h>
#include <sys/uio.h>
#include <unistd.h>

int main(int argc, char *argv[])
{	char *out = "Hello standard output !\n";
 	char *err = "Hello standard error  !\n";

	int len_out = strlen(out);
	int len_err = strlen(err);

	write(1,out,len_out);
	write(2,err,len_err);

	exit(1);
}
