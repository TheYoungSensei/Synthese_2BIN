########################################
# Makefile biblio : v0.2
#   Bdh
#   25 nov : 
#######################################

CFLAGS=-g

biblio : biblio.o livre.o
	cc $(CFLAGS) -o biblio biblio.o livre.o

biblio.o : biblio.c livre.h
	cc $(CFLAGS) -c biblio.c

livre.o : livre.c livre.h
	cc $(CFLAGS) -c livre.c

clean :
	rm *.o
	rm biblio

