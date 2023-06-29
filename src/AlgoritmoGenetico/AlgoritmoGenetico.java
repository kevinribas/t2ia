package src.AlgoritmoGenetico;

import src.Arquivos.GerenciadorArquivos;
import src.Console.LOG;
import src.Jogo.Jogador;
import src.Minimax.Dificuldade;
import src.Rede.JogoRede;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Random;

public class AlgoritmoGenetico {
    private static int TAMANHO_POPULACAO = 20;
    private static int oculta = 9; // numero de neuronios da camada oculta
    private static int saida = 9;  // numero de neuronios da camada de saida

    private Cromossomo[] populacao;
    private final Random aleatorio = new Random();

    private Cromossomo melhorCromossomo = null;

    public AlgoritmoGenetico(int tamanhoPopulacao, int neuroniosCamadaOculta, int neuroniosCamadaSaida) {
        TAMANHO_POPULACAO = tamanhoPopulacao;
        oculta = neuroniosCamadaOculta;
        saida = neuroniosCamadaSaida;
        this.populacao = new Cromossomo[TAMANHO_POPULACAO];
    }

    public void executar(int geracoes, Dificuldade dificuldade) {
        for (int i = 0; i < this.populacao.length; i++) {
            populacao[i] = Cromossomo.random(oculta, saida);
//          printCromossomo(populacao[i]);
        }

        for (int g = 1; g <= geracoes; g++) {
            Cromossomo[] populacaoIntermediaria = new Cromossomo[TAMANHO_POPULACAO];
            System.out.println("\nGeração " + g);

//            Utilidade para a mutação
            double somatorioAptidao = 0;

            for (Cromossomo cromossomo : populacao) {
                JogoRede jg = new JogoRede(oculta, saida, cromossomo, dificuldade);
//              Jogar
                jg.jogar();

//              Computar aptidão
                cromossomo.aptidao = aptidao(jg);
                LOG.printaAptidao(cromossomo.aptidao, jg);

                somatorioAptidao += cromossomo.aptidao;

//                Seleciona para salvar e jogar depois
                if (melhorCromossomo == null || melhorCromossomo.aptidao < cromossomo.aptidao) {
                    melhorCromossomo = new Cromossomo(cromossomo); // Construtor que copia pesos (e gera outra referencia)
                }
            }

            populacaoIntermediaria[0] = elitismo(populacao);

            crossOver(populacao, populacaoIntermediaria);
            populacao = populacaoIntermediaria;

//            boolean forcarMutacao = (somatorioAptidao / populacao.length) < 100;
            if (aleatorio.nextInt(2) == 0) { // 50% de chance de mutação || quando a média de aptidão for menor q 0
                mutacao(populacao);
            }
        }
    }

    public static void mutacao(Cromossomo[] _populacao) {
        LOG.printaMutacao("Mutação acionada");
        Random aleatorio = new Random();

        int quantidadeCromossomos = 2;

        while (quantidadeCromossomos > 0) {
            int indexCromossomoAleatorio = aleatorio.nextInt(TAMANHO_POPULACAO - 1) + 1; // -1 +1 pra não pegar o primeiro cromossomo
            int quantidadeDeGenesQueSeraoMutados = 40; // dos 180 genes
            while (quantidadeDeGenesQueSeraoMutados > 0) {
                int indexAleatorioPeso = aleatorio.nextInt(_populacao[0].pesos.length);

                _populacao[indexCromossomoAleatorio].pesos[indexAleatorioPeso] = aleatorio.nextDouble();
                quantidadeDeGenesQueSeraoMutados--;
            }
            quantidadeCromossomos--;
        }
    }

    public double aptidao(JogoRede jogoRede) {
        double pontuacao = 0d;

        if (jogoRede.venceu(1)) { // Rede ganhou
            pontuacao += 100;
            pontuacao += jogoRede.numeroDeRounds(false);
        } else if (jogoRede.empatou()) {
            pontuacao += 30;
            pontuacao += jogoRede.numeroDeRounds(true);
        } else if (jogoRede.venceu(0)) { // Minimax ganhou
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
        LOG.printaElitismo("ELITISMO: Cromossomo escolhido teve aptidão " + cromoPopu.aptidao);
        return cromoPopu;
    }

    public void crossOver(Cromossomo[] _populacao, Cromossomo[] _populacaoIntermediaria) {
        for (int i = 1; i < _populacaoIntermediaria.length; i++) { // Começa em 1 por conta do elitismo
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

        int val1 = random.nextInt(_populacao.length);
        int val2 = random.nextInt(_populacao.length);

        if (_populacao[val1].aptidao > _populacao[val2].aptidao) {
            return val1;
        }

        return val2;
    }

    public static void main(String[] args) {
        AlgoritmoGenetico ag = new AlgoritmoGenetico(20, 9, 9);
        ag.executar(1000, Dificuldade.DIFICIL);

//        Salva o melhor cromossomo
        System.out.println("O MELHOR CROMOSSOMO TEVE APTIDAO: " + ag.melhorCromossomo.aptidao);

        LocalDateTime localDate = LocalDateTime.now();
        String filename = localDate.getYear() + "-" + localDate.getMonthValue() + "-" + localDate.getDayOfMonth() + " " + localDate.getHour() + "-" + localDate.getMinute() + "-" + localDate.getSecond();
        GerenciadorArquivos.geraArquivo(filename, Arrays.stream(ag.melhorCromossomo.pesos).mapToObj(Double::toString).toArray(String[]::new));
    }
}
