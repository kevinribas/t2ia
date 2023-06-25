package src.AlgoritmoGenetico;

import java.util.Random;

public class Cromossomo {
    private static final Random gera = new Random();
    public double[] pesos;

    public double aptidao;

    public static Cromossomo random(int oculta, int saida) {
        int pesosOculta = oculta + 1; //numero de pesos por neuronio da camada oculta
        int pesosSaida = saida + 1;  //numero de pesos por neuronio da camada de saida
        int totalPesos = pesosOculta * oculta + pesosSaida * saida;

        Cromossomo c = new Cromossomo();
        c.pesos = new double[totalPesos];

        for (int i = 0; i < c.pesos.length; i++) {
            c.pesos[i] = gera.nextDouble();
            if (gera.nextBoolean()) c.pesos[i] = c.pesos[i] * -1;
        }
        return c;
    }

}
