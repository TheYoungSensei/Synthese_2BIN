var createPerson=function(name,surname) {
	var age,address;
	function setAddress(a) { address=a;}
	function setAge(a) { age=a;}
	var self={
		getName:function() {return name;},
		getSurname:function() {return surname;},
		getAge:function() {return age;},
		getAddress:function() {return address;},
		setAge:setAge,
		setAddress:setAddress
	}
	return self;
};