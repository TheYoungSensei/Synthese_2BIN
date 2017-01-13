#include <stdio.h>

int main() {
	//Un nombre premier est un nombre entier divisible par lui-même et par 1
	printf("Entrer le nombre maximal : (2 chiffres seulement) ");
	double c;
	while((c = getchar()) != 10){
		c -= 48;
		printf("%.0f", c);
		if((estCompris(c)) == 0) {
			printf("Les informations ne sont pas correctes");
			break;
		}
		double entree = getchar();
		double d;
		int nombre;
		if(entree == 10) {
			d = entree;
			nombre = c;
		} else if (estCompris(entree - 48) == 0) {
			printf("Les informations ne sont pas correctes \n");
			break;
		} else {
			d = entree - 48;
			entree = getchar();
			nombre = (10 * c) + d;
			if(entree != 10) {
				printf("Trop grand nombre entré, faites attention la prochaine fois ^^ \n");
				while((entree = getchar() != 10));
				printf("Le nombre maximal est : %.0f", c);
				printf("%.0f \n", d);
			}
		}
		int table[nombre];
		int x;
		int y;
		for(x = 0; x < nombre; x++){
			if(x == 0 || x == 1){
				table[x] = 1;
			} else {
				table[x] = 0;
			}
		}
		for(x = 2; x <  nombre; x++){
			for(y = 2; y  < nombre; y++){
				if(((y % x) == 0) && (x != y) ){
					table[y] = 1;
				}
			}
		}
		printf("Les nombres premiers sont : ");
		for(x = 0; x < nombre; x++) {
			if(table[x] == 0){
				printf("%d, ", x);
			}
			//printf("%d", table[x]);

		}
		printf("\n");
		printf("Entrer le nombre maximal : (2 chiffres seulement) ");
	}
}
	


int estCompris(double nombre) {
	int nombrePermis[10] = {0,1,2,3,4,5,6,7,8,9};
	int x;
	for(x = 0; x < 10; x++){
		if(nombre == nombrePermis[x]){
			return 1;
		}
	}
	return 0;
}
