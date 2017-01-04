(for $x in doc("library.xml")//song
let $c := (doc("duree.xml")//song[@track_ID=$x/track_ID])
order by $c ascending
return <song>{$x/artist}{$x/name}</song>)[last()]