for $acte in doc("hamlet.xml")//ACT
return <ACTE>{$acte/TITLE}<SPEAKERS>{for $speaker in distinct-values($acte//SPEAKER) return <SPEAKER>{$speaker}</SPEAKER>}</SPEAKERS></ACTE>