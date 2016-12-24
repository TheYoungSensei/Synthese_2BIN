Object userID = null;
try {
	Map<String, Object> decodedPayload = new JWTVerifier(JWTSecret).verify(ltoken);
	userID = decodedPayload.get("id");
	if (!remoteHost.equals(decodedPayload.get("ip"))) userID=null;
} catch (Exception exception) {
// ignore
}
if (userID!=null) { // ici on a pu recuperer un utilisateur valide
...
} else { // ici pas : envoi d'une erreur ou redirection vers page d'authentification ou autre...
...
}