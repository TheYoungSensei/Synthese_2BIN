function Person(name,surname) {
  var age,address;
  function setAddress(a) { address=a;}
  function setAge(a) { age=a;}
  this.getName=function() {return name;};
  this.getSurname=function() {return surname;};
  this.getAge=function() {return age;};
  this.getAddress=function() {return address;};
  this.setAge=setAge;
  this.setAddress=setAddress;
};
var john=new Person("John", "Snow");