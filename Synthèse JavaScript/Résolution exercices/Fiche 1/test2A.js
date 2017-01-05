function test(){
	var a = createPerson('a');
	var b = createPerson('b');
	var c = createPerson('c');
	a.setRightPer(b);
	b.setRightPer(c);
	c.setRightPer(a);
	a.setLeftPer(c);
	b.setLeftPer(b);
	c.setLeftPer(a);
	a.traitement(1,c);
}