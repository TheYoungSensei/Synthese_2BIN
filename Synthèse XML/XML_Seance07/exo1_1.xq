
for $i in doc("recettes.xml")//recette
return element titre {$i/entete/titre/text()}