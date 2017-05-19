:

  (
	./ip-top20.sh | tee ip-top20.out
        echo
	diff ip-top20.bak ip-top20.out
	cp -p ip-top20.out ip-top20.bak
  ) | mail -s "Black hole report" rssi@uclouvain.be

exit
