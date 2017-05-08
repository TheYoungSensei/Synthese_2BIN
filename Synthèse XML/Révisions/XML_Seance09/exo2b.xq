for $song in (doc("library.xml")//song)
let $duree := (doc("duree.xml")//song[@track_ID = $song/track_ID]/duree)
return <song>
  {$song/track_ID}
  {$song/name}
  {$song/artist}
  {$song/album}
  {$song/genre}
  {$duree}
</song>