var puissance4 = function(){
	var  grille=['','','','','','','']; //lignes
	for(var i = 0; i < 7; i++){
		grille[i] = ['|   |','|   |','|   |','|   |','|   |','|   |']; //colonnes
	}
	function mouvement(colonne, couleur){
		if(grille[colonne - 1][0] === '|   |'){
			for(var i = grille[colonne - 1].length; i >= 0; i--){
				if(grille[colonne - 1][i] ===  '|   |'){
					grille[colonne - 1][i] = "| " + couleur + " |";
					return i;
				}
			}
		} else {
			return "remplis";
		}
	}
	function suiteColonne(){
		for(var i = 0; i < grille.length; i++){
			for(var j = 0; j < grille[i].length; j++){
				if(grille[i][j] === '| X |'){
					if(j < grille[i].length - 1 && grille[i][j + 1] === '| X |'){
						if(j < grille[i].length - 2 && grille[i][j + 2] === '| X |'){
							if(j < grille[i].length - 3 && grille[i][j + 3] === '| X |'){
								return "jaune";
							}
						}
					}
				}
				if(grille[i][j] === '| O |'){
					if(j < grille[i].length - 1 && grille[i][j + 1] === '| O |'){
						if(j < grille[i].length - 2 && grille[i][j + 2] === '| O |'){
							if(j < grille[i].length - 3 && grille[i][j + 3] === '| O |'){
								return "rouge";
							}
						}
					}
				}
			}
		}
		return "rien";
	}
	function suiteLigne(){
		for(var i = 0; i < grille.length; i++){
			for(var j = 0; j < grille[i].length; j++){
				if(grille[i][j] === '| X |'){
					if(i < grille.length - 1 && grille[i + 1][j] === '| X |'){
						if(i < grille.length - 2 && grille[i + 2][j] === '| X |'){
							if(i < grille.length - 3 && grille[i + 3][j] === '| X |'){
								return "jaune";
							}
						}
					}
				}
				if(grille[i][j] === '| O |'){
					if(i < grille.length - 1 && grille[i + 1][j] === '| O |'){
						if(i < grille.length - 2 && grille[i + 2][j] === '| O |'){
							if(i < grille.length - 3 && grille[i + 3][j] === '| O |'){
								return "rouge";
							}
						}
					}
				}
			}
		}
		return "rien";
	}
	function suiteDiagonale(){
		for(var i = 0; i < grille.length; i++){
			for(var j = 0; j < grille[i].length; j++){
				if(grille[i][j] === '| X |'){
					if((i < grille.length - 1 && j < grille[i].length - 1) && grille[i + 1][j + 1] === '| X |'){
						if((i < grille.length - 2 && j < grille[i].length - 2) && grille[i + 2][j + 2] === '| X |'){
							if((i < grille.length - 3 && j < grille[i].length - 3) && grille[i + 3][j + 3] === '| X |'){
								return "jaune";
							}
						}
					}
					if((i < grille.length - 1 && j > 2) && grille[i + 1][j - 1] === '| X |'){
						if((i < grille.length - 2 && j > 1) && grille[i + 2][j - 2] === '| X |'){
							if((i < grille.length - 3 && j > 0) && grille[i + 3][j - 3] === '| X |'){
								return "jaune";
							}
						}
					}
				}
				if(grille[i][j] === '| O |'){
					if((i < grille.length - 1 && j < grille[i].length - 1) && grille[i + 1][j + 1] === '| O |'){
						if((i < grille.length - 2 && j < grille[i].length - 2) && grille[i + 2][j + 2] === '| O |'){
							if((i < grille.length - 3 && j < grille[i].length - 3) && grille[i + 3][j + 3] === '| O |'){
								return "rouge";
							}
						}
					}
					if((i < grille.length - 1 && j > 2) && grille[i + 1][j - 1] === '| O |'){
						if((i < grille.length - 2 && j > 1) && grille[i + 2][j - 2] === '| O |'){
							if((i < grille.length - 3 && j > 0) && grille[i + 3][j - 3] === '| O |'){
								return "rouge";
							}
						}
					}
				}
			}
		}
		return "rien";
	}
	function gagnant(){
		if(self.suiteColonne() === "jaune" || self.suiteLigne() === "jaune" || self.suiteDiagonale() === "jaune"){
			return "jaune";
		} else if(self.suiteColonne() === "rouge" || self.suiteLigne() === "rouge" || self.suiteDiagonale() === "rouge"){
			return "rouge";
		} else {
			return "rien";
		}
	}
	function placeDisponible(){
		for(var i = 0; i < grille.length; i++){
			for(var j = 1; j < grille[i].length; j++){
				if(grille[i][j] === '|   |'){
					return true;
				}
			}
		}
		return false;
	}
	function resetGrille(){
		var  newGrille=['','','','','','','']; //lignes
		for(var i = 0; i < 7; i++){
			newGrille[i] = ['|   |','|   |','|   |','|   |','|   |','|   |']; //colonnes
		}
		grille = newGrille;
	}
	var self = {
			getGrille:function() { return grille; },
			mouvement : mouvement,
			suiteColonne : suiteColonne,
			suiteLigne : suiteLigne,
			suiteDiagonale : suiteDiagonale,
			gagnant : gagnant,
			placeDisponible : placeDisponible,
			resetGrille : resetGrille
	}
	return self;
}

function printGrille(grille) {
	for(var i = 0; i < grille[0].length; i++){
		var print = "";
		for(var j = 0; j < grille.length; j++){
			print += grille[j][i];
		}
		if(print !== undefined){
			console.log(print);
		}
	}
}


function jeu(){
	console.log("Bienvenue dans ce puissance 4, création de votre partie ^^");
	var partie = puissance4();
	var boutons = document.getElementsByClassName();
	while(partie.gagnant() === "rien"){
		console.log(printGrille(partie.getGrille()));
		console.log("C'est au tour des jaunes (symbole correspondant : X (Veuilliez entrer le numéro de la colonne dans laquelle vous souhaitez mettre un pion)");
		boutons.onclick();
		partie.mouvement(parseInt(cJaune), 'X');
		if(partie.gagnant() !== "jaune"){
			console.log(printGrille(partie.getGrille()));
			console.log("C'est au tour des rouges (symbole correspondant : O (Veuilliez entrer le numéro de la colonne dans laquelle vous souhaitez mettre un pion)");
			var cRouge = console.readln();
			partie.mouvement(parseInt(cRouge), 'O');
		}
		if(!partie.placeDisponible()){
			console.log("Aucun des joueurs n'est parvenus jusqu'à la victoire")
			break;
		}
	}
	console.log(printGrille(partie.getGrille()));
	console.log("Les " + partie.gagnant() + "s sont les gagnants de cette manche");
}

var partie = puissance4();
var joueurPrec = "jaune";
var depl;

function joue(colonne){
	var notif = document.getElementById("notif");
	if(partie.gagnant() === "rien" && partie.placeDisponible() === true) {
		var divs = document.getElementsByTagName('div');
		if(joueurPrec === "jaune"){
			depl = partie.mouvement(colonne, 'O');
			if(depl !== "remplis"){
				divs[depl * 7 + colonne - 1].style.backgroundColor = "red";
				joueurPrec = "rouge";
				notif.innerHTML = "C'est à présent au tour des jaunes de jouer";
			}
		} else if (joueurPrec === "rouge") {
			depl = partie.mouvement(colonne, 'X');
			if(depl !== "remplis"){
				divs[depl * 7 + colonne - 1].style.backgroundColor = "yellow";
				joueurPrec = "jaune";
				notif.innerHTML = "C'est à présent au tour des rouges de jouer";
			}
		}
		
	}
	if(partie.placeDisponible() === false){
		notif.innerHTML = "Plus de Place Disponible";
	} else if(partie.gagnant() === "jaune") {
		notif.innerHTML = "Les jaunes sortent vainqueur de cette manche";
	} else if(partie.gagnant() === "rouge") {
		notif.innerHTML = "Les rouges sortent vainqueur de cette manche";
	}
}

function reset() {
	partie.resetGrille();
	var divs = document.getElementsByTagName('div');
	for(var i = 0; i < divs.length; i++){
		divs[i].style.backgroundColor = "white";
	}
}
