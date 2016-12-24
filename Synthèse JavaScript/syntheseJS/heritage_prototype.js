// definition de Lanister qui herite de Person :
Lanister.prototype=new Person("Lanister","");
// redirection du constructeur par defaut (sinon celui de Person est utilise) :
Lanister.prototype.constructor=function(surname) {
	this.surname=surname;
}