package src.AlgoritmoGenetico;

import java.util.Random;

public class Cromossomo {
    private static final Random gera = new Random();
    public double[] pesos;

    public double aptidao;

    public Cromossomo(Cromossomo c) {
        this.pesos = new double[c.pesos.length];
        System.arraycopy(c.pesos, 0, this.pesos, 0, c.pesos.length);
        this.aptidao = c.aptidao;
    }

    public Cromossomo(int oculta, int saida) {
        int pesosOculta = oculta + 1; //numero de pesos por neuronio da camada oculta
        int pesosSaida = saida + 1;  //numero de pesos por neuronio da camada de saida
        int totalPesos = pesosOculta * oculta + pesosSaida * saida;
        this.pesos = new double[totalPesos];
    }

    public static Cromossomo random(int oculta, int saida) {
        Cromossomo c = new Cromossomo(oculta, saida);

        for (int i = 0; i < c.pesos.length; i++) {
            c.pesos[i] = gera.nextDouble();
            if (gera.nextBoolean()) c.pesos[i] = c.pesos[i] * -1;
        }
        return c;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("------------------------");
        sb.append("\nPesos do cromossomo:");
        int tamanho = this.pesos.length;
        for (int i = 0; i < tamanho; i++) {
            sb.append("\n[").append(i).append("]: ").append(this.pesos[i]);
        }
        sb.append("\nAptidao do cromossomo -> ").append(this.aptidao);
        sb.append("\n------------------------");
        return sb.toString();
    }
}
