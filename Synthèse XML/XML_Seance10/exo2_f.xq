for $a1 in (doc("hamlet.xml")//ACT)
let $c := count(distinct-values($a1/descendant::SPEAKER))
let $a2 := (doc("hamlet.xml")//ACT[count(distinct-values(descendant::SPEAKER)) = $c][TITLE > $a1/TITLE])
where count(distinct-values($a2/descendant::SPEAKER)) > 0
return <actes>
          <acte1>{$a1/TITLE}</acte1>
          <acte2>{$a2/TITLE}</acte2>
          <nb>{$c}</nb>
       </actes>