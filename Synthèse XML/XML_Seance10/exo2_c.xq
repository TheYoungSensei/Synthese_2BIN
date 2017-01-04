<result>{for $marcellus in doc("hamlet.xml")//SPEECH[SPEAKER="MARCELLUS"]
let $hamlet := $marcellus/following-sibling::SPEECH[SPEAKER="HAMLET"][1]
return $hamlet/LINE}</result>