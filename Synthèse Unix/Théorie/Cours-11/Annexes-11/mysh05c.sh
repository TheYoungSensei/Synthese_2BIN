#! /bin/bash

./prodd $*
ret=$?
echo Return value is $ret

case $ret in
	0)
		echo Number is even
		;;
	1)
		echo Number is odd
		;;
	*)
		echo Invalid number
		;;
esac

exit 0
