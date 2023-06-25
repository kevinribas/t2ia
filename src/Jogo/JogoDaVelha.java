package src.Jogo;
/**
 * Write a description of class src.Jogo.JogoDaVelha here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.Scanner;
public class JogoDaVelha
{    
    public static void main(String args[]){
        Tabuleiro tabu = new Tabuleiro();
        int r=10;
        System.out.println("\f\n>>> Usuário jogando... ");
        System.out.println(tabu);
        jogaUsuario(tabu);
        for(int turno=1; turno<=4; turno++){
            System.out.println("\n>>> Computador jogando...");
            
            r = tabu.jogaComputador();
           // System.out.println(tabu);
           
            if(r==1) break;
            
            System.out.println("\n>>> Usuário jogando... ");
            System.out.println(tabu);
            
            jogaUsuario(tabu);
            r = tabu.vencedor();
            if(r==-1) break;
            
        }
        System.out.println(tabu);
        if(r==1) System.out.println("Computador ganhou");
        else if(r==-1) System.out.println("Usuário ganhou");
             else System.out.println("Deu véia");
    }
    
    private static void jogaUsuario(Tabuleiro tabu){
        Scanner in = new Scanner(System.in);
        int lin,col;
        do{
            System.out.print("Informe a linha do tabuleiro: ");
            lin = in.nextInt();
            System.out.print("Informe a coluna do tabuleiro: ");
            col = in.nextInt();
        }while(lin<0 ||lin>2||col<0||col>2||tabu.ocupada(lin,col));
        tabu.alteraPosicao(lin,col,'X');
        
    }
}
