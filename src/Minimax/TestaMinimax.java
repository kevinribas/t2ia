package src.Minimax;

import src.Rede.Sucessor;

import java.util.Random;

/**
 * Write a description of class src.Jogo.Tabuleiro here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TestaMinimax {
    private char[][] velha;
    private Dificuldade dificuldade = Dificuldade.DIFICIL;

    /**
     * Cria e inicializa o tabuleiro do jogo do velha.
     * Todas as posições inicial livres, ou seja, com #
     */
    public TestaMinimax(int tabuleiroVelha[][]) {    //-1: celula livre  1: X   0: O
        velha = new char[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (tabuleiroVelha[i][j] == -1) velha[i][j] = '#';
                else if (tabuleiroVelha[i][j] == 1) velha[i][j] = 'X';
                else velha[i][j] = 'O';
    }

    public void setDificuldade(Dificuldade dificuldade) {
        this.dificuldade = dificuldade;
    }

    /**
     * Faz a jogada do computador, usando o algoritmo Minimax
     */
    public Sucessor joga() {
        Minimax mini = new Minimax(velha);
        Random r = new Random();

        switch (dificuldade) {
            case FACIL:
                if (r.nextInt(5) == 0) { // 20% de chance
                    return mini.getMelhorAB();
                }
                return mini.getMelhorAleatorio();
            case MEDIO:
                if (r.nextInt(2) == 0) { // 50% de chance
                    return mini.getMelhorAB();
                }
                return mini.getMelhorAleatorio();
            default:
                return mini.getMelhorAB(); // chama versão Alfa Beta Pruning
        }
    }

    /**
     * Gera um String do estado atual do tabuleiro
     */
    public String toString() {
        String saida = " ----- Jogo da Velha -----\n";
        for (int i = 0; i < 3; i++) saida = saida + "\t" + i;
        saida = saida + "\n";
        for (int i = 0; i < 3; i++) {
            saida = saida + i + "\t";
            for (int j = 0; j < 3; j++) saida = saida + velha[i][j] + "\t";
            saida = saida + "\n";
        }
        return saida;
    }


}