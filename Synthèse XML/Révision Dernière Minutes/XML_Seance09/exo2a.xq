(for $song in (doc("library.xml")//song)
let $c := (doc("duree.xml")//song[@track_ID = $song/track_ID]/duree)
order by $c ascending
return <song>{$song/name}</song>)[last()]