var batailleNavale = function(){
	var lettres = [ '', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K'];
	var grille = [ [], [], [], [], [], [], [], [], [], [], [] ];
	var grilleAdv = [ [], [], [], [], [], [], [], [], [], [], [] ]; 
	for(var i = 0; i < 11; i++){
		for(var j = 0; j < 11; j++){
			if(i === 0){
				if(j === 0){
					grille[i][j] = "  ";
					grilleAdv[i][j] = "  ";
				} else {
					if(j === 10){
						grille[i][j] = j;
						grilleAdv[i][j] = j;
					} else {
						grille[i][j] = j + " ";
						grilleAdv[i][j] = j + " ";
					}
				}
			} else if (j === 0) {
				grille[i][j] = "| " + lettres[i] + " |";
				grilleAdv[i][j] = "| " + lettres[i] + " |";
			} else {
				grille[i][j] = '|   |';
				grilleAdv[i][j] = '|   |';
			}
		}
	}
	function placerUnBateau (taille, coordA, coordB, sens){
		if(!checkPlacementBateau(taille, coordA, coordB, sens))
			return "Mauvaise Position";
		if(sens.toLowerCase() === 'h'){
			for(var i = coordA; i < (coordA + taille); i++){
				grille[i][coordB] = "| 0 |";
			}
			return "Bateau en position";
		} else if (sens.toLowerCase() === 'v'){
			for(var i = coordB; i < (coordB + taille); i++){
				grille[coordA][i] = "| 0 |";
			}
			return "Bateau en position";
		} else {
			return "Bizarement le sens que vous avez entré n'est pas correct comment justifiez vous cela ? :P";
		}
	}
	function checkPlacementBateau(taille, coordA, coordB, sens){
		checkCroisement();
		if(sens.toLowerCase() === 'h'){
			for(var i = coordA; i < (coordA + taille); i++){
				if(i > 9)
					return false;
				if(grille[i][coordB] === "| 0 |")
					return false;
			}
		} else if (sens.toLowerCase() === 'v'){
			for(var i = coordB; i < (coordB + taille); i++){
				if(i > 9)
					return false;
				if(grille[coordA][i] === "| 0 |")
					return false;
			}
		}
		return true;
		
	}
	function checkCroisement(taille, coordA, coordB, sens){
		
	}
	function checkSortieGrille(coordA, coordB){
		if(coordA > grille.length)
			return true;
		if(coordB > grille[coordA].length)
			return true;
		return false;
	}
	function tirer(joueur, coordA, coordB){
		if(checkSortieGrille(coordA,coordB) === true)
			return "Vous avez tiré trop loin, dommage ^^";
		var modif = joueur.getGrille();
		if(modif[coordA][coordB] === "| 0 |"){
			modif[coordA][coordB] = "| X |";
			grilleAdv[coordA][coordB] = "| X |";
			joueur.setGrille(modif);
			return "touché";
		} else {
			modif[coordA][coordB] = "| W |";
			grilleAdv[coordA][coordB] = "| W |";
			joueur.setGrille(modif);
			return "A l'eau";
		}
	}
	function setGrille(newGrille) { grille = newGrille; }
	function setGrilleAdv(newGrilleAdv) { grilleAdv = newGrilleAdv; }
	var self = {
			getGrille : function() { return grille; },
			getGrilleAdv : function() { return grilleAdv; },
			setGrille : setGrille,
			setGrilleAdv : setGrilleAdv,
			tirer : tirer,
			placerUnBateau : placerUnBateau
	}
	return self;
}

function printGrilles(grille, grilleAdv) {
	for(var i = 0; i < grille[0].length; i++){
		var print = "";
		for(var j = 0; j < grille.length; j++){
			print += grille[j][i];
		}
		print += "          ";
		for(var j = 0; j < grilleAdv.length; j++){
			print += grilleAdv[j][i];
		}
		console.log(print);
	}
	
}

function test() {
	var part = batailleNavale();
	printGrille(part.getGrille());
}

function placerBateaux(partie){
	var bateauDispo = [ 4, 3, 2, 1];
	var tailleBateaux = [2, 3, 4, 5];
	while(checkBateau(bateauDispo)){
		console.log("Quel est le bateau que vous souhaitez placer ? :");
		var taille = parseInt(console.readln());
		console.log("Quelles sont les coordonnées où vous souhaitez placer votre bateau ? : ");
		var coord = console.readln();
		coordA = parseInt(getCoordA(coord));
		coordB = parseInt(getCoordB(coord));
		console.log("Dans quel sens souhaitez vous placer votre bateau ? (H/V)");
		var sens = console.readln();
		var reussite = partie.placerUnBateau(taille, coordA, coordB, sens);
		console.log(reussite);
		printGrilles(partie.getGrille(), partie.getGrilleAdv());
		if(reussite === "Bateau en position")
			bateauDispo[taille - 2] -= 1;
	}
}

function placerBateauxAleatoire(partie){
	var bateauDispo = [ 4, 3, 2, 1];
	var tailleBateaux = [2, 3, 4, 5];
	for(var i = 0; i < tailleBateaux.length; i++){
		while(bateauDispo[i] > 0){
			if(partie.placerUnBateau(tailleBateaux[i], unEntierAuHasardEntre(1, 10), unEntierAuHasardEntre(1, 10), sensAleatoire()) === "Bateau en position")
				bateauDispo[i] -= 1;
		}
	}
}

function getCoordA(coord){
	return coord.charCodeAt(0) - 96;
}

function getCoordB(coord){
	if(coord.length === 3){
		return ((coord.charCodeAt(1) - 48) * 10) + (coord.charCodeAt(2) - 48);
	} else {
		return coord.charCodeAt(1) - 48;
	}
}

function checkBateau(bateauDispo){
	var tot = 0;
	for(var i = 0; i < bateauDispo.length; i++){
		tot += bateauDispo[i];
	}
	return tot > 0;
}

function sensAleatoire(){
	if(unEntierAuHasardEntre(0,1) === 1){
		return 'h';
	} else {
		return 'v';
	}
}

function unEntierAuHasardEntre (valeurMinimale, valeurMaximale){
    var nombreReel;
    var resultat;
 
    nombreReel = Math.random();
    resultat = (nombreReel * (valeurMaximale - valeurMinimale + 1))
       					+ valeurMinimale;
    return parseInt(resultat);
}

function checkSiFinis(joueur){
	for(var i = 0; i < 10; i++){
		for(var j = 0 ; j < 10; j++){
			if(joueur.getGrille()[i][j] === "| 0 |")
				return false;
		}
	}
	return true;
}

function jeu(){
	var joueur1 = batailleNavale();
	placerBateauxAleatoire(joueur1);
	printGrilles(joueur1.getGrille(), joueur1.getGrilleAdv());
	var joueur2 = batailleNavale();
	placerBateauxAleatoire(joueur2);
	console.log("Les Bateaux de l'adversaire sont en position");
	while(!checkSiFinis(joueur1)){
		console.log("Joueur 1, veuillez entrer vos coordonnées de tir :  ");
		var coord = console.readln();
		console.log(joueur1.tirer(joueur2, getCoordA(coord), getCoordB(coord)));
		if(!checkSiFinis(joueur2)){
			var coordA = unEntierAuHasardEntre(1,10);
			var coordB = unEntierAuHasardEntre(1,10);
			console.log("L'ordinateur tire en : " + String.fromCharCode(coordA + 96) + coordB);
			console.log(joueur2.tirer(joueur1, coordA, coordB));
		}
		printGrilles(joueur1.getGrille(), joueur1.getGrilleAdv());
	}
	
	if(checkSiFinis(joueur1)){
		console.log("Joueur 1 domine les mers");
	} else {
		console.log("Joueur 2 domine les mers");
	}
	
	
}