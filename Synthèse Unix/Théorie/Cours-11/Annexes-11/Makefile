#

all:	prenv prargs prodd

prenv:	prenv.c
	$(CC) -o prenv prenv.c

prargs:	prargs.c
	$(CC) -o prargs prargs.c

prodd:	prodd.c prodd.h
	$(CC) -o prodd prodd.c

clean:
	rm -f prenv prargs prenv.backup prargs.backup prodd
