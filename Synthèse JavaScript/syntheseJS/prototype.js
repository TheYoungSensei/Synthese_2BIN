function Person(name,surname) {
	this.name=name; this.surname=surname;
}
Person.prototype.setAddress=function(a) { this.address=a;}
Person.prototype.setAge=function(a) { this.age=a;}
Person.prototype.getName=function() {return this.name;};
Person.prototype.getSurname=function() {return
this.surname;};
Person.prototype.getAge=function() {return this.age;};
Person.prototype.getAddress=function() {return
this.address;};
};
var john=new Person("John", "Snow");