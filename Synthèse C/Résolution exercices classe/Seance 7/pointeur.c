#include <stdio.h>
#include <stdlib.h>

int main(int argc, char** argv){
      int adresseMin;
        int adresseMax;

        srand(time(NULL));
        m = (int) rand() %9 + 2;
        n = (int) rand() %9 + 2;
        printf("%d\n%d\n", m, n);
        if((tabDyn = (int*) malloc ((m * n) * sizeof(int))) == NULL) {
                perror("Error Malloc");
                return 1;
        }
        for(ptr1 = tabDyn; ptr1 - tabDyn < m * n; ptr1++) {
                *ptr1 = (int) (rand() / (RAND_MAX + 1.0)*9);
        }
        compteur = 0;
        nbMin = 9;
        nbMax = 0;
        nbAvg = 0;
        for(ptr1 = tabDyn; ptr1 - tabDyn < m * n; ptr1++) {
                printf("%d " , *ptr1);
                compteur++;
                if(*ptr1 < nbMin) {
                        nbMin = *ptr1;
                        adresseMin = ((ptr1 - tabDyn) % n) + 1;
                }
                if(*ptr1 > nbMax) {
                        nbMax = *ptr1;
                        adresseMax = ((ptr1 - tabDyn) % n) + 1;
                }
                nbAvg += *ptr1;
                if(compteur % n == 0){
                        nbAvg = (int) nbAvg / n;
                        printf("   Min : %d   Indice Min : %d  Max : %d   IndiceMax : %d   Moyenne : %d \n", nbMin, adresseMin, nbMax, adresseMax, nbAvg);
                        nbMin = 9;
                        nbMax = 0;
                        nbAvg = 0;
                }
        }
        compteur = 0;
        printf("Colonnes : \n");
        for(ptr1 = tabDyn; ptr1 - tabDyn < n; ptr1++) {
                for(ptrTemp = ptr1; ptrTemp - tabDyn < m * n; ptrTemp += n) {
                        if(*ptrTemp < nbMin) {
                                nbMin = *ptrTemp;
                                adresseMin = (ptrTemp - tabDyn);
                        }
                        if(*ptrTemp > nbMax) {
                                nbMax = *ptrTemp;
                                adresseMax = (ptrTemp - tabDyn);
                        }
                        nbAvg += *ptrTemp;
                        printf("%d ", *ptrTemp);
                }
                if(compteur % n == 0){
                        nbAvg = (int) nbAvg / n;
                        printf("   Min : %d   Indice Min : %d  Max : %d   IndiceMax : %d   Moyenne : %d \n", nbMin, adresseMin, nbMax, adresseMax, nbAvg);
                        nbMin = 9;
                        nbMax = 0;
                        nbAvg = 0;
                }
        }

}
