package src.AlgoritmoGenetico;

import java.util.Random;

import src.Rede.Rede;


public class AlgoritmoGenetico {
    private static final int TAMANHO_POPULACAO = 20;
    private static final int oculta = 9; // numero de neuronios da camada oculta
    private static final int saida = 9;  // numero de neuronios da camada de saida
    private Rede rede = new Rede(oculta, saida);
    private Cromossomo[] populacao;


    public AlgoritmoGenetico() {
        Random aleatorio = new Random();
        this.populacao = new Cromossomo[TAMANHO_POPULACAO];

        for (int i = 0; i < this.populacao.length; i++) {
            populacao[i] = Cromossomo.random(oculta, saida);
        }

        Cromossomo[] populacaoIntermediaria = new Cromossomo[TAMANHO_POPULACAO];

//        30 Gerações
//        for (int g = 1; g <= 30; g++) { // inicio da geracao
//            System.out.println("\n\nGeração: " + g);

        for (Cromossomo cromossomo : populacao) {
//            rede.setPesosNaRede();
//            Pra cada cromossomo:
            aptidao(cromossomo);
        }
//        } // Fim da geração

        // Seta o primeiro cromossomo, vindo do elitismo da atual
        populacaoIntermediaria[0] = elitismo(populacao);

        crossOver(populacao, populacaoIntermediaria);
        populacao = populacaoIntermediaria;

        if (aleatorio.nextInt(2) == 0) {
            mutacao(populacao);
        }
    }

    public static void mutacao(Cromossomo[] _populacao) {
    }

    public void aptidao(Cromossomo _cromossomo) {
//        FINGINDO QUE FEZ UM CALCULO:
        _cromossomo.aptidao = 0.3d;
    }

    // VERIFICAR NOVAMENTE
    public Cromossomo elitismo(Cromossomo[] _populacao) {
        int pesosSize = _populacao[0].getTamanhoPesos();
        Cromossomo aux = new Cromossomo();
        int linha = 0;
        double menorValor = _populacao[0].getPesos(TAMANHO_POPULACAO-1);

        for (int i = 1; i < TAMANHO_POPULACAO; i++) {
            if (_populacao[i].getPesos(TAMANHO_POPULACAO-1) < menorValor) {
                menorValor = _populacao[i].getPesos(TAMANHO_POPULACAO-1);
                linha = i;
            }
        }

        for (int j = 0; j < pesosSize; j++) {
            aux.setPeso(j, _populacao[linha].getPesos(j));
        }


        return aux; // remover depois de definir a funcao
    }

    public void crossOver(Cromossomo[] _populacao, Cromossomo[] _populacaoIntermediaria) {
    }

    public void torneio() {
    }

    public static void main(String[] args) {
        new AlgoritmoGenetico();
    }
}
