import subprocess
import re

date = subprocess.getoutput("echo %time%")
iter = re.findall("[0-9]+", date)
print("«Il est", iter[0], "h", iter[1],"»")