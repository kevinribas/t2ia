package src.AlgoritmoGenetico;

import java.util.Random;

public class AlgoritmoGenetico {
    private static int TAMANHO_POPULACAO = 20;
    private static int oculta = 9; // numero de neuronios da camada oculta
    private static int saida = 9;  // numero de neuronios da camada de saida

    private Cromossomo[] populacao;
    private final Random aleatorio = new Random();

    public AlgoritmoGenetico(int tamanhoPopulacao, int neuroniosCamadaOculta, int neuroniosCamadaSaida) {
        TAMANHO_POPULACAO = tamanhoPopulacao;
        oculta = neuroniosCamadaOculta;
        saida = neuroniosCamadaSaida;
        this.populacao = new Cromossomo[TAMANHO_POPULACAO];
    }

    public void executar(int geracoes) {
        for (int i = 0; i < this.populacao.length; i++) {
            populacao[i] = Cromossomo.random(oculta, saida);
//          printCromossomo(populacao[i]);
        }

        for (int g = 1; g <= geracoes; g++) {
            Cromossomo[] populacaoIntermediaria = new Cromossomo[TAMANHO_POPULACAO];
            System.out.println("\nGeração " + g);


            for (Cromossomo cromossomo : populacao) {
                JogoRede jg = new JogoRede(oculta, saida, cromossomo);
//              Jogar
                jg.jogar();

//              Computar aptidão
                cromossomo.aptidao = aptidao(jg);
                if (cromossomo.aptidao >= 70) {
                    jg.printaJogo();
                    System.out.println("Aptidão Cromossomo -> " + cromossomo.aptidao);
                }
            }

            populacaoIntermediaria[0] = elitismo(populacao);

            crossOver(populacao, populacaoIntermediaria);
            populacao = populacaoIntermediaria;

            if (aleatorio.nextInt(2) == 0) {
                mutacao(populacao);
            }
        }
    }

    public static void mutacao(Cromossomo[] _populacao) {
        Random aleatorio = new Random();

        int indexCromossomoAleatorio = aleatorio.nextInt(TAMANHO_POPULACAO - 1) + 1;
        int quantidadeDeGenesQueSeraoMutados = 20; // dos 180 genes
        while (quantidadeDeGenesQueSeraoMutados > 0) {
            int indexAleatorioPeso = aleatorio.nextInt(_populacao[0].pesos.length);

            _populacao[indexCromossomoAleatorio].pesos[indexAleatorioPeso] = aleatorio.nextDouble();
            quantidadeDeGenesQueSeraoMutados--;
        }

    }

    public double aptidao(JogoRede jogoRede) {
        double pontuacao = 0d;

        if (jogoRede.venceu(1)) {
            pontuacao += 100;
            pontuacao += jogoRede.numeroDeRounds(false);
        } else if (jogoRede.empatou()) {
            pontuacao += 30;
            pontuacao += jogoRede.numeroDeRounds(true);
        } else if (jogoRede.venceu(0)) {
            pontuacao += -100;
            pontuacao += jogoRede.numeroDeRounds(true);
        } else {
            pontuacao += -500;
        }

        return pontuacao;
    }

    // pronto
    public Cromossomo elitismo(Cromossomo[] _populacao) {
        Cromossomo cromoPopu = _populacao[0];
        for (Cromossomo cromossomo : _populacao) {
            if (cromossomo.aptidao > cromoPopu.aptidao) {
                cromoPopu = cromossomo;
            }
        }
        return cromoPopu;
    }

    public void crossOver(Cromossomo[] _populacao, Cromossomo[] _populacaoIntermediaria) {
        for (int i = 0; i < TAMANHO_POPULACAO; i++) {
            _populacaoIntermediaria[i] = new Cromossomo(oculta, saida);

            int individuo1 = torneio(_populacao);
            int individuo2 = torneio(_populacao);

            for (int j = 0; j < _populacaoIntermediaria[i].pesos.length; j++) {
                _populacaoIntermediaria[i].pesos[j] = (_populacao[individuo1].pesos[j] + _populacao[individuo2].pesos[j]) / 2;
//                System.out.println(_populacaoIntermediaria[i].pesos[j]);
            }
        }
    }

    // pronto
    public int torneio(Cromossomo[] _populacao) {
        Random random = new Random();

        int val1 = random.nextInt(TAMANHO_POPULACAO);
        int val2 = random.nextInt(TAMANHO_POPULACAO);

        if (_populacao[val1].aptidao > _populacao[val2].aptidao) {
            return val1;
        }

        return val2;
    }

    public static void main(String[] args) {
        AlgoritmoGenetico ag = new AlgoritmoGenetico(20, 9, 9);
        ag.executar(3000);
    }
}
