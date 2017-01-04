<songlist>{for $x in doc("library.xml")//song
let $c := (doc("duree.xml"))//song[@track_ID=$x/track_ID]
return <song>
          {$x/track_ID}
          {$x/name}
          {$x/artist}
          {$x/album}
          {$x/genre}
          {$c/duree}
       </song>}
</songlist>