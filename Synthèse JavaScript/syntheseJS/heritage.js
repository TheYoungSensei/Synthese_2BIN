var createEtudiant=function(nom,prenom) {
	var self=createPerson(nom,prenom);
	var noma;
	self.setNoma=function(n) {noma=n;}
	self.getNoma=function() {return noma;}
	return self;
}