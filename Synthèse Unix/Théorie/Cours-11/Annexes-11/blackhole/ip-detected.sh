:

  gzip -dcf messages* | grep SRC= | awk '{print $10;}' | \
	sed -e s/SRC=// | sort -t. -k 1,1n -k 2,2n -k 3,3n -k4,4n | \
	uniq -c | sort -n

exit
