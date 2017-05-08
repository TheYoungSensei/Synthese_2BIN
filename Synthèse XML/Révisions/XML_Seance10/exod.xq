for $acte in (doc("hamlet.xml")//ACT)
return <ACTE>
  {$acte/TITLE}
  <SPEAKERS>{for $sp in distinct-values($acte//SPEAKER) return <SPEAKER>{$sp}</SPEAKER>}</SPEAKERS>
</ACTE>