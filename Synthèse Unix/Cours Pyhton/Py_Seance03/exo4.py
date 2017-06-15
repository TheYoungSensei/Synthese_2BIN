import subprocess

date = subprocess.getoutput("echo %time%")
print("Il est ", date[0:2], "h", date[3:5])
