package src.AlgoritmoGenetico;

import src.Minimax.Dificuldade;
import src.Minimax.TestaMinimax;
import src.Rede.Rede;
import src.Rede.Sucessor;

public class JogoRede {
    private final Rede rede;
    public final double[] tabuleiro;
    public final int[][] tabuleiroVelha;

    public JogoRede(int oculta, int saida, Cromossomo c) {
        this.rede = new Rede(oculta, saida);
        tabuleiroVelha = new int[][]{{-1, -1, -1},            //-1: celula livre  1: X   0: O
                {-1, -1, -1}, {-1, -1, -1}};

        // tabuleiro de teste - conversao de matriz para vetor
        tabuleiro = new double[tabuleiroVelha.length * tabuleiroVelha.length];

        int k = 0;
        for (int[] casas : tabuleiroVelha) {
            for (int j = 0; j < tabuleiroVelha.length; j++) {
                tabuleiro[k] = casas[j];
                k++;
            }
        }

        rede.setPesosNaRede(tabuleiro.length, c.pesos);
    }

    public void jogar() {
        boolean aindaTemJogo = true;
        int k;

        while (aindaTemJogo) {
//            -----------------------------------------JOGA REDE
//                        System.out.println("\n\n>>>RODADA: " + n);
//            Exibe um exemplo de propagação : saida dos neurônios da camada de saída
            double[] saidaRede = rede.propagacao(tabuleiro);
//            System.out.println("Rede Neural - Camada de Saida: Valor de Y");
//            for (int i = 0; i < saidaRede.length; i++) {
//                System.out.println("src.Rede.Neuronio " + i + " : " + saidaRede[i]);
//            }

            //Define posicao a jogar de acordo com rede
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
//            System.out.println("src.Rede.Neuronio de maior valor: " + indMaior + " - " + saidaRede[indMaior]);
//            System.out.println(">>> src.Rede.src.Rede escolheu - Linha: " + linha + " Coluna: " + coluna);

            if (tabuleiroVelha[linha][coluna] != -1) {
//                System.out.println("REDE ERROU?" + "linha: " + linha + " coluna: " + coluna);
//                printaJogo();
//                break;
//                    DO NOTHING
//                System.out.println("Posicao ocupada");
            } else {
                tabuleiroVelha[linha][coluna] = 1;

//                System.out.println("\nsrc.Jogo.Tabuleiro apos jogada: ");
//                for (int[] casas : tabuleiroVelha) {
//                    for (int j = 0; j < tabuleiroVelha.length; j++) {
//                        System.out.print(casas[j] + "\t");
//                    }
//                    System.out.println();
//                }
            }

            if (venceu(0) || empatou() || venceu(1)) {
                aindaTemJogo = false;
            }

            if (aindaTemJogo) {
                //-----------------------------------------JOGA MINIMAX
                TestaMinimax mini = new TestaMinimax(tabuleiroVelha);
                mini.setDificuldade(Dificuldade.DIFICIL);

                Sucessor melhor = mini.joga();

//            System.out.println(">>> MINIMAX escolheu - Linha: " + melhor.getLinha() + " Coluna: " + melhor.getColuna());

                if (tabuleiroVelha[melhor.getLinha()][melhor.getColuna()] != -1) {
//                System.out.println("Posicao ocupada");
//                    System.out.println("Minimax ERROU?");
//                    printaJogo();
                } else {
                    tabuleiroVelha[melhor.getLinha()][melhor.getColuna()] = 0;

//                System.out.println("\nTabuleiro apos jogada: ");
//                for (int[] casas : tabuleiroVelha) {
//                    for (int j = 0; j < tabuleiroVelha.length; j++) {
//                        System.out.print(casas[j] + "\t");
//                    }
//                    System.out.println();
//                }
                }
                if (venceu(0) || empatou() || venceu(1)) {
                    aindaTemJogo = false;
                }
            }


            //tabuleiro de teste - conversao de matriz para vetor
            k = 0;
            for (int[] casas : tabuleiroVelha) {
                for (int j = 0; j < tabuleiroVelha.length; j++) {
                    tabuleiro[k] = casas[j];
                    k++;
                }
            }
        }
        //tabuleiro de teste - conversao de matriz para vetor
        k = 0;
        for (int[] casas : tabuleiroVelha) {
            for (int j = 0; j < tabuleiroVelha.length; j++) {
                tabuleiro[k] = casas[j];
                k++;
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

        return quantoMaisMelhor
                ? qtdAcertos * 10
                : (4 - qtdAcertos) * 10;
    }

    public void printaJogo() {
        System.out.println("======TABULEIRO======");
        System.out.println("Tabuleiro apos jogada: ");
        for (int[] casas : tabuleiroVelha) {
            for (int j = 0; j < tabuleiroVelha.length; j++) {
                System.out.print(casas[j] + "\t");
            }
            System.out.println();
        }
    }
}
