#La fonction maListe
def maListe(m, n):
    '''
        :param m: valeur dont les éléments doivent être multiples
        :param n: valeur max des éléments
        :return: la liste créée
    '''
    liste = [0]
    for x in range(2, n + 1):
        if(x % m == 0):
            liste.append(x)
    return liste

#Script principal
mult = int(input("Valeur dont les élements doivent être multiples : "))
max = int(input("Valeur max : "))
print(maListe(mult, max))