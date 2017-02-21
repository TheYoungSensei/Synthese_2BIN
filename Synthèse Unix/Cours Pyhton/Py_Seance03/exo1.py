import re;

compteurVide = 0
for i in range(0,10):
    chaineCarac = input("Veuilliez entrer une chaîne de caractère : ")
    if chaineCarac:
        print(chaineCarac)
    else :
        compteurVide += 1
print("Nombre de chaînes vide : ", compteurVide)