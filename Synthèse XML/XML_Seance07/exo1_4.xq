for $recette in doc("recettes.xml")//recette
where every $i in $recette//ingredient satisfies $i != 'ananas'
return element titre {$recette//titre/text()}