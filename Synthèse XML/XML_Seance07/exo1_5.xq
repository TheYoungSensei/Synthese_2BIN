for $monIngredient in doc("recettes.xml")//recette[position()=1]//ingredient[position()<= 2] 
return element ingredient {$monIngredient/text()}