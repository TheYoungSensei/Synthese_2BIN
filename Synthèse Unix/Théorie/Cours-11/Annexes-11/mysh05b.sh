#! /bin/bash

if [ ! -w / ]; then
	echo You must be root to run this program !
	exit 1
fi

echo Access granted as root !

exit 0
