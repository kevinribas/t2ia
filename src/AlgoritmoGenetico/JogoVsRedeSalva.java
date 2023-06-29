package src.AlgoritmoGenetico;

import src.Arquivos.GerenciadorArquivos;
import src.Console.LOG;
import src.Jogo.Jogador;
import src.Rede.JogoRede;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class JogoVsRedeSalva {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Cromossomo c = new Cromossomo(9, 9);

        String[] s = GerenciadorArquivos.listarArquivosNaPasta("pesos");
        for (int i = 0; i < s.length; i++) {
            System.out.println("[" + i + "] - " + s[i]);
        }
        int index = -1;
        while (index < 0 || index > s.length) {
            System.out.print("Digite o arquivo: ");
            index = scanner.nextInt();
            System.out.println();
        }

        System.out.println("Carregando \"" + s[index] + "\"...");

        String[] pesos = GerenciadorArquivos.leArquivo(s[index]);

        c.pesos = Arrays.stream(pesos).mapToDouble(Double::parseDouble).toArray();

        LOG.feedbackJogo = true;
        JogoRede jg = new JogoRede(9, 9, c, null);
        jg.setJogador(Jogador.Usuario);
        jg.jogar();
        jg.printaJogo();
    }
}
