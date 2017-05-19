#! /bin/bash

cnt=1

while [ $cnt -le 16 ]; do
	cntf=$(echo $cnt | awk '{printf "%03d",$1}')
	echo $cntf
 	touch File-$cntf.tst
	cnt=$(expr $cnt + 1)
done

exit
