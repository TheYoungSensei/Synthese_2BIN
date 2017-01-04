<result>{
  (for $speaker in distinct-values(doc("hamlet.xml")//SPEAKER)
  let $nombreLigne := sum((for $sp in doc("hamlet.xml")//SPEECH[SPEAKER=$speaker] return count($sp/LINE)))
  return <nombre_de_ligne><role>{$speaker}</role><nombre>{$nombreLigne}</nombre></nombre_de_ligne>)[nombre>100]
}</result>