package Modele;

public class Modele {
    
    int ligne;
    int colone;

    int nbJoueur, currentPlayer;

    // false => remplie
    // true => vide
    boolean [][] plateau;

    // todo: don't accept l && c < 1
    Modele(int ligne, int colone, int nbJoueur, int firstJoueur) {

        this.colone = colone;
        this.ligne = ligne;
        this.nbJoueur = nbJoueur;
        this.currentPlayer = firstJoueur;

        plateau = new boolean[ligne][colone];

        assert(firstJoueur < nbJoueur - 1);
    }


    void nextPlayer() {
        this.currentPlayer = (this.currentPlayer + 1) % nbJoueur;
    }

    boolean isGameOver() {
        return plateau[0][0] == true;
    }


    void joue(int l, int c) {

        for (int i = l; i < ligne; i++) {
            for (int j = c; j < colone; j++) {
                plateau[i][j] = true;
            }
        }


        nextPlayer();
    }
    
}