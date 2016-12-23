Person someone= new Person("Eugen", 28, new Address(157, "Paris"));
String json= genson.serialize(someone);
===>
{"address":{"building":157,"city":"Paris"},"age":28,"name":"Eugen"}