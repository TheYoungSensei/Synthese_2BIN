<playlist>{for $list in doc("library.xml")//list
let $duree := sum(doc("duree.xml")//song[@track_ID=$list/track_ID])
where $duree >600
return <list>{$list/@name}<duree>{$duree}</duree></list>}</playlist>