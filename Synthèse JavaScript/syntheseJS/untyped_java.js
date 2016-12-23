Map<String, Object> p1 = new HashMap<String, Object>() {{
	put("name", "Foo");
	put("age", 28);
}};
String json= genson.serialize(p1);
// {"age":28,"name":"Foo"}
Map<String, Object> p2 = genson.deserialize(json, Map.class);