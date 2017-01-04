for $noeudCourant in doc("recettes.xml")//recette[position()=1]//following-sibling::ingredient[position()=3]
return element ingredient {$noeudCourant/text()}