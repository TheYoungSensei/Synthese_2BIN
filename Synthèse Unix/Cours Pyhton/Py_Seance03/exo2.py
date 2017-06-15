import re
while True:
    chaine = input("Veuilliez entrer une chaine de caractère : ")
    if chaine:
        if re.search("[a-zA-Z]{10,}", chaine):
            print(chaine)
    else:
         break
