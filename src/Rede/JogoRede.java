package src.Rede;

import src.AlgoritmoGenetico.Cromossomo;
import src.Console.LOG;
import src.Jogo.Jogador;
import src.Minimax.Dificuldade;
import src.Minimax.TestaMinimax;

import java.util.Scanner;

public class JogoRede {
    private final Rede rede;
    public final double[] tabuleiro;
    private final int[][] tabuleiroVelha;
    private Jogador jogador = Jogador.Minimax;
    private Dificuldade dificuldade;

    public JogoRede(int oculta, int saida, Cromossomo c, Dificuldade dificuldade) {
        this.dificuldade = dificuldade;
        this.rede = new Rede(oculta, saida);
        tabuleiroVelha = new int[][]{{-1, -1, -1},            //-1: celula livre  1: X   0: O
                {-1, -1, -1}, {-1, -1, -1}};

        tabuleiro = new double[tabuleiroVelha.length * tabuleiroVelha.length];
        mapearTabuleiro();

        rede.setPesosNaRede(tabuleiro.length, c.pesos);
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    private void mapearTabuleiro() {
        //tabuleiro de teste - conversao de matriz para vetor
        int k = 0;
        for (int[] casas : tabuleiroVelha) {
            for (int j = 0; j < tabuleiroVelha.length; j++) {
                tabuleiro[k] = casas[j];
                k++;
            }
        }
    }

    private boolean redeJoga() {
        mapearTabuleiro();
        //-----------------------------------------JOGA REDE
        // Exibe um exemplo de propagação : saida dos neurônios da camada de saída
        double[] saidaRede = rede.propagacao(tabuleiro);

        // Define posicao a jogar de acordo com rede
        int indMaior = 0;
        double saidaMaior = saidaRede[0];
        for (int i = 1; i < saidaRede.length; i++) {
            if (saidaRede[i] > saidaMaior) {
                saidaMaior = saidaRede[i];
                indMaior = i;
            }
        }

        int linha = indMaior / 3;
        int coluna = indMaior % 3;

        if (tabuleiroVelha[linha][coluna] != -1) {
            LOG.printaFeedback("Posicao ocupada [" + linha + "," + coluna + "]");
//            Não conseguiu jogar
            return false;
        } else {
            tabuleiroVelha[linha][coluna] = 1;
            // Conseguiu jogar
            return true;
        }
    }

    private boolean minimaxJoga() {
//-----------------------------------------JOGA MINIMAX
        TestaMinimax mini = new TestaMinimax(tabuleiroVelha);
        mini.setDificuldade(dificuldade);

        Sucessor melhor = mini.joga();

        // System.out.println(">>> MINIMAX escolheu - Linha: " + melhor.getLinha() + " Coluna: " + melhor.getColuna());

        if (tabuleiroVelha[melhor.getLinha()][melhor.getColuna()] != -1) {
            LOG.printaFeedback("Posicao ocupada [" + melhor.getLinha() + "," + melhor.getColuna() + "]");
            // Não conseguiu jogar
            return false;
        } else {
            tabuleiroVelha[melhor.getLinha()][melhor.getColuna()] = 0;
            // Conseguiu Jogar
            return true;
        }
    }

    private void usuarioJoga() {
        Scanner in = new Scanner(System.in);
        int lin, col;
        do {
            printaJogo();
            System.out.print("Informe a linha do tabuleiro: ");
            lin = in.nextInt();
            System.out.print("Informe a coluna do tabuleiro: ");
            col = in.nextInt();

            if (tabuleiroVelha[lin][col] != -1) {
                LOG.printaFeedback("Posição ocupada! [" + lin + "," + col + "]");
            }
        } while (lin < 0 || lin > 2 || col < 0 || col > 2 || tabuleiroVelha[lin][col] != -1);
        tabuleiroVelha[lin][col] = 0;
    }

    public void jogar() {
        boolean aindaTemJogo = true;

        while (aindaTemJogo) {
            aindaTemJogo = redeJoga(); // Rede: 1

            if (venceu(0) || empatou() || venceu(1)) {
                aindaTemJogo = false;
            }

            if (aindaTemJogo) { // ADVERSARIO DA REDE: 0
                if (jogador == Jogador.Usuario) {
                    usuarioJoga();
                } else {
                    aindaTemJogo = minimaxJoga();
                }

                if (venceu(0) || empatou() || venceu(1)) {
                    aindaTemJogo = false;
                }
            }
        }
    }

    public boolean venceu(int player) {
        for (int i = 0; i < 3; i++) {
            if (tabuleiroVelha[i][0] == player && tabuleiroVelha[i][1] == player && tabuleiroVelha[i][2] == player) {
                return true;
            }
        }

        for (int j = 0; j < 3; j++) {
            if (tabuleiroVelha[0][j] == player && tabuleiroVelha[1][j] == player && tabuleiroVelha[2][j] == player) {
                return true;
            }
        }

        if (tabuleiroVelha[0][0] == player && tabuleiroVelha[1][1] == player && tabuleiroVelha[2][2] == player) {
            return true;
        }

        if (tabuleiroVelha[0][2] == player && tabuleiroVelha[1][1] == player && tabuleiroVelha[2][0] == player) {
            return true;
        }

        return false;
    }

    public boolean empatou() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tabuleiroVelha[i][j] == -1) {
                    return false;
                }
            }
        }

        return true;
    }

    public int numeroDeRounds(boolean quantoMaisMelhor) {
        int qtdAcertos = 0;
        for (int[] linha : tabuleiroVelha) {
            for (int coluna : linha) {
                if (coluna == 1) {
                    qtdAcertos++;
                }
            }
        }

        return quantoMaisMelhor ? qtdAcertos * 10 : (5 - qtdAcertos) * 10;
    }

    public void printaJogo() {
        System.out.println("======TABULEIRO======");
        for (int[] casas : tabuleiroVelha) {
            for (int j = 0; j < tabuleiroVelha.length; j++) {
                System.out.print(casas[j] + "\t");
            }
            System.out.println();
        }
    }
}
