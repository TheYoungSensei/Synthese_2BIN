:

  filtered="";
  filtered=$filtered"58.17.52.14|";
  filtered=$filtered"61.176.193.225|"
  filtered=$filtered"221.132.34.170|"
  filtered=$filtered"64.71.33.53"

        ./ip-detected.sh | egrep -v $filtered | \
	tail -20 | tac | tee pipe | awk '{print $2;}' | \
	xargs -I IP -n 1 whois -h whois.cymru.com " -v -f -t IP" | \
	grep -v whois.cymru.com | paste pipe -

exit
