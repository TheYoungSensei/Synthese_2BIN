<speakers>{for $sp in distinct-values(doc("hamlet.xml")//SPEAKER)
let $acte := count(doc("hamlet.xml")//ACT[.//SPEECH[SPEAKER=$sp]])
order by $acte descending
return <speaker>
  <nom>{$sp}</nom>
  <nbActe>{$acte}</nbActe>
</speaker>
}</speakers>