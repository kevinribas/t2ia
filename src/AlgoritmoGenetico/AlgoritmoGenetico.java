package src.AlgoritmoGenetico;

import java.util.Random;

import src.Rede.Rede;


public class AlgoritmoGenetico {
    private static final int TAMANHO_POPULACAO = 20;
    private static final int oculta = 9; // numero de neuronios da camada oculta
    private static final int saida = 9;  // numero de neuronios da camada de saida
    private Rede rede = new Rede(oculta, saida);
    private Cromossomo[] populacao;
    
    private double[] tabuleiro;
    private int[][] tabuleiroVelha;


    public AlgoritmoGenetico() {
        tabuleiroInit();
        Random aleatorio = new Random();
        this.populacao = new Cromossomo[TAMANHO_POPULACAO];

        for (int i = 0; i < this.populacao.length; i++) {
            populacao[i] = Cromossomo.random(oculta, saida);
            printCromossomo(populacao[i]);
        }
        // funciona até aqui, verificar problema abaixo!!!

        Cromossomo[] populacaoIntermediaria = new Cromossomo[TAMANHO_POPULACAO];

        for (Cromossomo cromossomo : populacao) {
            rede.setPesosNaRede(tabuleiro.length, cromossomo.pesos);
            cromossomo.aptidao = aptidao(tabuleiroVelha);
            System.out.println("Cromossomo -> " + cromossomo.aptidao);
        }

        populacaoIntermediaria[0] = elitismo(populacao);

        crossOver(populacao, populacaoIntermediaria);
        populacao = populacaoIntermediaria;

        if (aleatorio.nextInt(2) == 0) {
            mutacao(populacao);
        }
    }

    public void printCromossomo (Cromossomo _cromossomo) {
        System.out.println("------------------------");
        System.out.println("Cromossomo: ");
        int tamanho = _cromossomo.pesos.length;
        for (int i = 0; i < tamanho; i++) {
            System.out.println("Pos -> " + i + " Peso -> " + _cromossomo.pesos[i]);
        }
        System.out.println("Aptidao do cromossomo -> " + _cromossomo.aptidao);
        System.out.println("------------------------");
    }

    public void tabuleiroInit () {
        tabuleiroVelha = new int[][]{{-1, -1, -1},            //-1: celula livre  1: X   0: O
                {-1, -1, -1},
                {-1, -1, -1}};

        // System.out.println("\f\nsrc.Jogo.Tabuleiro inicial: ");
        // for (int i = 0; i < tabuleiroVelha.length; i++) {
        //     for (int j = 0; j < tabuleiroVelha.length; j++) {
        //         System.out.print(tabuleiroVelha[i][j] + " \t");
        //     }
        //     System.out.println();
        // }

        //tabuleiro de teste - conversao de matriz para vetor      
        tabuleiro = new double[tabuleiroVelha.length * tabuleiroVelha.length];
        int k = 0;
        for (int i = 0; i < tabuleiroVelha.length; i++) {
            for (int j = 0; j < tabuleiroVelha.length; j++) {
                tabuleiro[k] = tabuleiroVelha[i][j];
                k++;
            }
        }
    }

    public static void mutacao(Cromossomo[] _populacao) {
        Random random = new Random();
        int quantidade = random.nextInt(3) + 1;

        // testar removendo o while no furuto
        while (quantidade > 0) {
            int linha = random.nextInt(_populacao.length) + 1;
            int coluna = random.nextInt(TAMANHO_POPULACAO);

            if (_populacao[linha].pesos[coluna] == 0) _populacao[linha].pesos[coluna] = 1;
            else _populacao[linha].pesos[coluna] = 0;
            // System.out.println("Mutacao no cromossomo: " + linha + " na coluna: " + coluna);
            quantidade--;
        }
    }

    public double aptidao(int[][] tabuleiro) {
        double pontuacao = (double) 0;
    
        if (venceu(tabuleiro, 1)) {
            pontuacao+= (double) 100;
        } else if (empatou(tabuleiro)) {
            pontuacao+= (double) 30;
        } else {
            pontuacao+= (double) -100;
        }

        pontuacao+= numeroDeRounds(tabuleiro);
        return pontuacao;
    }

    private int numeroDeRounds(int[][] tabuleiro) {
        int qntRodadas = 0;
        for (int i = 0; i < tabuleiro.length; i++) {
            for (int j = 0; j < tabuleiro.length; j++) {
                if (tabuleiro[i][j] == 1) {
                    qntRodadas++;
                }
            }
        }

        if (qntRodadas == 1) {
            return 10;
        } else if (qntRodadas == 2) {
            return 20;
        } else if (qntRodadas == 3) {
            return 30;
        } else {
            return 40;
        }
    }

    private boolean empatou(int[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == -1) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean venceu(int[][] board, int player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true;
            }
        }
        for (int j = 0; j < 3; j++) {
            if (board[0][j] == player && board[1][j] == player && board[2][j] == player) {
                return true;
            }
        }

        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true;
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true;
        }

        return false;
    }

    // pronto
    public Cromossomo elitismo(Cromossomo[] _populacao) {
        Cromossomo cromoPopu = populacao[0];
        for (Cromossomo cromossomo : populacao) {
            if (cromossomo.aptidao < cromoPopu.aptidao) {
                cromoPopu = cromossomo;
            }
        }
        return cromoPopu;
    }

    public void crossOver(Cromossomo[] _populacao, Cromossomo[] _populacaoIntermediaria) {
        for (int i=0; i < TAMANHO_POPULACAO; i++) {
            int individuo1 = torneio(_populacao);
            int individuo2 = torneio(_populacao);

            for (int j = 0; j < 10; j++) {
                _populacaoIntermediaria[i].pesos[j] = _populacao[individuo1].pesos[j];
                _populacaoIntermediaria[i + 1].pesos[j] = _populacao[individuo2].pesos[j];
            }
            for (int j = 10; j < 20; j++) {
                _populacaoIntermediaria[i].pesos[j] = _populacao[individuo2].pesos[j];
                _populacaoIntermediaria[i + 1].pesos[j] = _populacao[individuo1].pesos[j];
            }
        }
    }

    // pronto
    public int torneio(Cromossomo[] _populacao) {
        Random random = new Random();

        int val1 = random.nextInt(TAMANHO_POPULACAO);
        int val2 = random.nextInt(TAMANHO_POPULACAO);

        if (_populacao[val1].aptidao > _populacao[val2].aptidao) {
            return val2;
        }

        return val1;
    }

    public static void main(String[] args) {
        new AlgoritmoGenetico();
    }
}
