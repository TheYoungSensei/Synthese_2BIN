# !/usr/bin/python.

from math import sqrt

while True :
    a = int(input("a : "))
    b = int(input("b : "))
    c = int(input("c : "))

    if(a == 0) :
        print("Fin programme")
        break

    delta = (b**2) - (4 * a * c)

    if(delta < 0) :
        print("Le delta est négatif et il n'y a donc pas de solution")
    elif(delta == 0) :
        print("Delta null, suite du calcul")
        print("Voici la racine : ", (-b/(2 * a)))
    else :
        print("Delta positif, suite du calcul")
        print("Premiere racine : ", (-b + sqrt(delta)) / (2 * a))
        print("Deuxieme racine : ", (-b - sqrt(delta)) / (2 * a))


