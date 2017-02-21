def transposer(m):
    '''
        :param m: la matrice à transposer
        :return: la liste de lignes
    '''
    liste = [];
    for x in m:
        liste.append(x[:])

    return liste

def afficher(li):
    '''
        affiche la ligne passée en paramètre
    '''
    for x in li:
        print(list(x))

li = range(1,11)
m = transposer([li,li,li,li,li])
afficher(m)