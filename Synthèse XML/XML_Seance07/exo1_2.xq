let $elem := doc("recettes.xml")//recette[position()=1]//ingredient[position()=3]
return element ingredient {$elem/text()}