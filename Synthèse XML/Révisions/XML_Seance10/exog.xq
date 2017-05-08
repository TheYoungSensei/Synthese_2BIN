<speakers>{
  for $speaker in distinct-values(doc("hamlet.xml")//SPEAKER)
  where every $i in doc("hamlet.xml")//ACT[.//SPEECH[SPEAKER = 'GUILDENSTERN']] satisfies $i//SPEECH[SPEAKER = $speaker]
  where $speaker != 'GUILDENSTERN'
  return <speaker>{$speaker}</speaker>
}</speakers>