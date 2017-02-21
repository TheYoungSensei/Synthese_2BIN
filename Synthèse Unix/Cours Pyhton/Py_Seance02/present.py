#!/usr/bin/python3
###################################################################
# present.py
# compare les performances de deux implémentation d'une fonction
# al
# Lundi 13 fév 2017
###################################################################

import random 
import time

## Définitions de fonctions

def present(m, li):
    '''
        :param m: l'élément dont il faut vérifier la présence
        :param li: la liste à tester
        :return: /
    '''
    compteur = 0;
    while(compteur < len(li)):
        if m == li[compteur]:
            print("L'élement : ", m, " est présent dans la liste")
            return
        compteur += 1
    print("L'élément : ", m, " n'est pas présent dans la liste")
    return

def presentPython(m, li):
    '''
          :param m: l'élément dont il faut vérifier la présence
          :param li: la liste à tester
          :return: /
    '''
    if m in li:
        print("L'élement : ", m, " est présent dans la liste")
    else:
        print("L'élément : ", m, " n'est pas présent dans la liste")


## Script de test

# on produit un tableau de un million de notes aléatoires
notes = [] 
for i in range(1000000): 
    notes.append(random.choice(range(21)))

# on compare notre algorithme de présence à l'implémentation in de Python
# - avec un entier présent:
t1 = time.clock() 
present(20,notes) 
t2 = time.clock() 
print('notre réponse en',"{:.6f}".format(t2 - t1),'sec.') 
t1 = time.clock() 
presentPython(20,notes) 
t2 = time.clock() 
print('réponse de Python en',"{:.6f}".format(t2 - t1),'sec.') 
# - avec un nombre absent: 
t1 = time.clock() 
present(-1,notes) 
t2 = time.clock() 
print('notre réponse en',"{:.6f}".format(t2 - t1),'sec.') 
t1 = time.clock() 
presentPython(-1,notes) 
t2 = time.clock() 
print('réponse de Python en',"{:.6f}".format(t2 - t1),'sec.')
