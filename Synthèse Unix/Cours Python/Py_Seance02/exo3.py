mois = ["Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Décembre"]
jours = [31,28,31,30,31,30,31,31,30,31,30,31]
annee = []
for i in range(len(mois)):
    annee.append(mois[i])
    annee.append(jours[i])
for x in range(0, len(annee),2):
    print("Le mois de " + annee[x] + " comporte " + str(annee[x+1]) + " jours")
