
BINARIES=test01 test01_nocrlf test02 test04_flush test04_noflush \
	test05_close test05_noclose test06 test07 test08 \
	test09_bsd test09_sysv test10

all:	$(BINARIES)

clean:
	$(RM) $(BINARIES)

test01:	test01.c
	cc -o $@ test01.c

test01_nocrlf:	test01.c
	cc -o $@ -DNOCRLF test01.c

test02:	test02.c
	cc -o $@ test02.c

test04_noflush:	test04.c
	cc -o $@ test04.c

test04_flush:	test04.c
	cc -o $@ -DFLUSH test04.c

test05_noclose:	test05.c
	cc -o $@ test05.c

test05_close:	test05.c
	cc -o $@ -DCLOSE test05.c

test06:	test06.c
	cc -o $@ test06.c

test07:	test07.c
	cc -o $@ test07.c

test08:	test08.c
	cc -o $@ -g test08.c

test09_bsd:	test09.c
	cc -o $@ test09.c

test09_sysv:	test09.c
	cc -o $@ -D_XOPEN_SOURCE=1 test09.c
