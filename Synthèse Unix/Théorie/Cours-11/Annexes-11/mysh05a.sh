#! /bin/bash

if [ $# -ne 1 ]; then
	echo Usage: $0 file
	exit 1
fi

if [ -f $1 ]; then
	echo md5sum is $(md5sum $1)
elif [ -d $1 ]; then
	echo $1 is a directory
else
	echo Unable to md5sum $1
fi

exit
