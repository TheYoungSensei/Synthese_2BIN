import re
import subprocess

print("«Il est ", re.split("\:",subprocess.getoutput("echo %time%"))[0],"h", re.split("\:",subprocess.getoutput("echo %time%"))[1], "»", sep="")
