#include <stdio.h>

int main(){
	double c;
	int nombrePermis[10] = {0,1,2,3,4,5,6,7,8,9};
	printf("Veuilliez entrer un chiffre ou deux chiffres \n");
	while((c = getchar()) != 10) {
		c -= 48;
		if(estCompris(c) == 0){
			printf("Les informations entrées ne sont pas correctes");
			break;
		}
		double entree = getchar();
		double d;
		if(entree == 10){
			d = entree;
		} else if (estCompris(entree - 48) == 0) {
			break;
		} else {
			d = entree - 48;
			entree = getchar();
			if(entree != 10){
				printf("Trop grand nombre entré, faites attention la prochaine fois ^^ \n");
				while((entree = getchar()) != 10);
				printf("Le nombre dont la factorielle est calculée est : %.0f", c);
				printf("%.0f \n", d);
			}

		}
		double facto = 1;
		int x;
		double nombre;
		if(d != 10){
			nombre = (10 * c) + d;
		} else {
			nombre = c;
		}
		for(x = nombre; x > 0; x--){
			facto *= x;
		}
		printf("La Factorielle est : %.0f \n", facto);
		printf("Veuilliez entrer un chiffre \n");
	}
}
int  estCompris(double nombre){
	int nombrePermis[10] = {0,1,2,3,4,5,6,7,8,9};
	int x;
	for(x = 0; x < 10; x++){
		if(nombre == nombrePermis[x]){
			return 1;
		}
	}
	return 0;
}
	

