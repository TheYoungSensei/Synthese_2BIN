$.ajax({
	url: 'getInfo',
	type: 'POST',
	data: 'id=5',
	success: function(reponse) {
		// traitement de la reponse
	},
	error: function(e) {
		// en cas d'erreur
		console.log(e.message);
	}
});