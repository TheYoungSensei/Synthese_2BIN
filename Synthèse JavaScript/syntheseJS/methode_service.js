service(HttpServletRequest i,HttpServletResponse o)
//HttpServletRequest : contient tout ce qui concerne la requete (son chemin, l'adresse de l'emetteur, les cookies, les donnees transmises, etc...)
//HttpServletResponse : possede les methodes pour preparer la reponse a renvoyer au front-end.
//En general on ne redefinit pas directement service : 
doGet(HttpServletRequest i,HttpServletResponse o)
doPost(HttpServletRequest i,HttpServletResponse o)
doPut(HttpServletRequest i,HttpServletResponse o)
doDelete(HttpServletRequest i,HttpServletResponse o)
