def supprimerElement(li, el):
    i = 0
    while i < len(li):
        if li[i] == el:
            li[i:i+1] = []
            return True
        i += 1
    return False

li = list(range(1,25))
while True:
    print(li)
    a = int(input("Entrer element : "))
    print(supprimerElement(li,a))