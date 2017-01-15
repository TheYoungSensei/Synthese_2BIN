<result>{
for $speaker in distinct-values(doc("hamlet.xml")//SPEAKER)
let $count := sum(let $sp := (doc("hamlet.xml")//SPEECH[SPEAKER = $speaker]) return count($sp/LINE))
order by $count descending
return <nombre_de_ligne>
  <role>{$speaker}</role>
  <nombre>{$count}</nombre>
</nombre_de_ligne>
}</result>