package src.AlgoritmoGenetico;

import src.Rede.Rede;


public class AlgoritmoGenetico {
    private static final int TAMANHO_POPULACAO = 20;
    private static final int oculta = 9; // numero de neuronios da camada oculta
    private static final int saida = 9;  // numero de neuronios da camada de saida
    private Rede rede = new Rede(oculta, saida);
    private Cromossomo[] populacao;


    public AlgoritmoGenetico() {
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
    }

    public void mutacao() {
    }

    public void aptidao(Cromossomo c) {
//        FINGINDO QUE FEZ UM CALCULO:
        c.aptidao = 0.3d;
    }

    public Cromossomo[] elitismo() {
        return null;
    }

    public void crossOver() {
    }

    public void torneio() {
    }

    public static void main(String[] args) {
        new AlgoritmoGenetico();
    }
}
