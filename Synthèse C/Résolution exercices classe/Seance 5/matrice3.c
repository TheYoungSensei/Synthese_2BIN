#include <stdio.h>
#include <stdlib.h>

int main(){
        int  m;
        int n = 7;
        int car  = 'A';
        int i;
        int j;
        int input;
        int tableLigne[5][7];
        int tableColonne[5][7];
        int *ptr1;
        int *tabDyn;
        double entree;
        printf("Veuillez entrer le nombre de lignes que la mtrice devrait avoir selon vous : ");
        m = getchar() - 48;
        entree = getchar();
                if((tabDyn = (int*) malloc((m * n) * sizeof(int))) == NULL){
                perror("Allocation de tabDyn impossible");
                return 1;
        }
        for(ptr1 = tabDyn; ptr1 - tabDyn < m * n; ptr1++){
                *ptr1 = car;
                car++;
                if(car > 'Z'){
                        car = 'A';
                }
        }
        /*for(i = 0; i < m; i++){
                for(j = 0; j < n; j++){
                        tableLigne[i][j] = car;
                        car ++;
                        if(car > 'Z'){
                                car  = 'A';
                        }
                }

        }*/
        car = 'A';
        for(i = 0; i < n; i++){
                for(j = 0; j < m; j++){
                        tableColonne[j][i] = car;
                        car++;
                        if(car > 'Z'){
                                car = 'A';
                        }
                }
        }


        printf("Les tableaux sont  a  present remplis \n");
        printf("Voici le tableau en lignes : \n");
        i = 0;
        for(ptr1 = tabDyn; ptr1 - tabDyn < m * n; ptr1++){
                input = *ptr1;
                printf("%c", input);
                i++;
                if(i %  n == 0){
                        printf("\n");
                }
        }
        /*
        for(i = 0; i < m; i++){
                for(j = 0; j < n; j++){
                        input = tableLigne[i][j];
                        printf("%c", input);
                }
                printf("\n");
        }
        */
        printf("Voici le tableau en colonnes : \n");
        for(i = 0; i < m; i++){
                for(j = 0; j < n; j++){
                        input = tableColonne[i][j];
                        printf("%c", input);
                }
                printf("\n");
        }
        printf("Doublons : \n");
        for(i = 0; i < m; i++){
                for(j = 0; j < n; j++){
                        if(tableLigne[i][j] == tableColonne[i][j]){
                                printf("%c en ligne %d en colonne %d \n", tableLigne[i][j], i, j);
                        }
                }
        }
        return 0;
}

