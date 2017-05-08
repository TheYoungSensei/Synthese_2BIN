for $acte1 in (doc("hamlet.xml")//ACT)
for $acte2 in (doc("hamlet.xml")//ACT[count(distinct-values(.//SPEAKER)) = count(distinct-values($acte1//SPEAKER))][TITLE < $acte1/TITLE])
return <actes><acte1>
          {$acte1/TITLE/text()}
       </acte1>
        <acte2>
          {$acte2/TITLE/text()}
        </acte2>
        <nb>{count(distinct-values($acte1//SPEAKER))}</nb>
        </actes>
