package src.Console;

import src.AlgoritmoGenetico.Cromossomo;
import src.Rede.JogoRede;

public class LOG {
    // ==================================== LOGs JOGO
    public static boolean feedbackJogo = false;

    public static void printaFeedback(String s) {
        if (feedbackJogo) {
            System.out.println(s);
        }
    }

    // ==================================== LOGs Genetico
    public static double aptidaoMinima = Integer.MIN_VALUE;
    public static double aptidaoMinimaPrintJogo = 100;

    public static void printaAptidao(double aptidao, JogoRede jg) {
        if (aptidao > aptidaoMinima) {
            System.out.println("Aptidão Cromossomo -> " + aptidao);
            if (jg != null && aptidao > aptidaoMinimaPrintJogo) {
                jg.printaJogo();
            }
        }
    }

    public static boolean feedbackElitismo = true;

    public static void printaElitismo(String s) {
        if (feedbackElitismo) {
            System.out.println(s);
        }
    }

    public static boolean feedbackMutacao = true;

    public static void printaMutacao(String s) {
        if (feedbackMutacao) {
            System.out.println(s);
        }
    }
}
