<speakers>{for $speaker in distinct-values(doc("hamlet.xml")//SPEAKER)
let $nombreActe := count(doc("hamlet.xml")//ACT[.//SPEAKER=$speaker])
order by $nombreActe descending
return <speaker><nom>{$speaker}</nom><nbActe>{$nombreActe}</nbActe></speaker>}</speakers>