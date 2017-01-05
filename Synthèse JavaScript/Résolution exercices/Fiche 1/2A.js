var createPerson = function(name){
	var rightPer,leftPer;
	function setRightPer(newRightPer) {
		rightPer = newRightPer;
	}
	function setLeftPer(newLeftPer) {
		leftPer = newLeftPer;
	}
	function traitement(chiffre, origine, max) {
		chiffre++;
		if(chiffre > max){
			console.log("GAME OVER");
			return 0;
		} else if (chiffre % 5 == 0 && chiffre % 7 == 0) {
			console.log("Joueur : " + name + ", vous devez dire : Ding Ding Bottle");
			origine.traitement(chiffre, self, max);
			return;
		} else if(chiffre % 5 == 0){
			console.log("Joueur : " + name + ", vous devez dire : Ding Ding");
			if(origine === leftPer){
				rightPer.traitement(chiffre,self, max);
				return;
			} else {
				leftPer.traitement(chiffre,self, max);
				return;
			}
		} else if (((chiffre % 7) == 0) || (((chiffre - 7) % 10) == 0)) {
			console.log("Joueur : " + name + ", vous devez dire : Bottle");
			origine.traitement(chiffre,self, max);
			return;
		} else {
			console.log("Joueur : " + name + ", vous devez dire : " + chiffre);
			if(origine === leftPer){
				rightPer.traitement(chiffre,self, max);
				return;
			} else {
				leftPer.traitement(chiffre,self, max);
				return;
			}
		}
	}
	var self = {
			getName : function() { return name; },
			getRightPer : function() { return rightPer.getName(); },
			getLeftPer : function() { return leftPer.getName(); },
			setRightPer:setRightPer,
			setLeftPer:setLeftPer,
			traitement:traitement
	}
	return self;
}

function test(){
	var a = createPerson('a');
	var b = createPerson('b');
	var c = createPerson('c');
	a.setRightPer(b);
	b.setRightPer(c);
	c.setRightPer(a);
	a.setLeftPer(c);
	c.setLeftPer(b);
	b.setLeftPer(a);
	console.log("Quel numero max desirez vous ? ^^");
	var max = 100;
	a.traitement(0,c, max);
}