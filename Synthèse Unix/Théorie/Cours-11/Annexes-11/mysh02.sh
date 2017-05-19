#! /bin/bash

for file in *.c ; do
	echo -n "$file "
	base=$(basename $file .c)
	echo $base
	cp -p $file $base.backup
	md5sum $file $base.backup
done

exit
