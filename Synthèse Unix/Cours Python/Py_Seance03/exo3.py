import re

while True:
    chaine = input("Veuilliez introduire un nombre : ")
    if chaine:
        if re.search("^\d*$", chaine): #\d à la place de [0-9]
            print(chaine)
        else :
            print("Pas un nombre")
    else:
        break