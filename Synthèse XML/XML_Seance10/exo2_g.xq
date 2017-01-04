<speakers>{for $speaker in distinct-values(doc("hamlet.xml")//SPEAKER)
where every $i in (doc("hamlet.xml")//ACT[.//SPEAKER='GUILDENSTERN']) satisfies $i[.//SPEAKER=$speaker] and $speaker != 'GUILDENSTERN'
return <speaker>{$speaker}</speaker>}</speakers>