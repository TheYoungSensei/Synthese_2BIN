import time;
import random;

def tri(li):
    for i in range(1, len(li) - 1):
        min = i
        for j in range(i + 1, len(li)):
            if li[j] < li[min]:
                min = j
        if(min != i):
            temp = li[i]
            li[i] = li[min]
            li[min] = temp
    return li

def triPython(li):
    return li.sort()

## Script de test

# on produit un tableau de un million de notes aléatoires
notes = []
for i in range(10000):
    notes.append(random.choice(range(21)))

# on compare notre algorithme de présence à l'implémentation in de Python
# - avec un entier présent:
t1 = time.clock()
tri(notes)
t2 = time.clock()
print('notre réponse en',"{:.6f}".format(t2 - t1),'sec.')
t1 = time.clock()
triPython(notes)
t2 = time.clock()
print('réponse de Python en',"{:.6f}".format(t2 - t1),'sec.')
# - avec un nombre absent:
t1 = time.clock()
tri(notes)
t2 = time.clock()
print('notre réponse en',"{:.6f}".format(t2 - t1),'sec.')
t1 = time.clock()
triPython(notes)
t2 = time.clock()
print('réponse de Python en',"{:.6f}".format(t2 - t1),'sec.')