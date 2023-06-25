
/**
 * Write a description of class Tabuleiro here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tabuleiro
{
    private char[][] velha;
    
    /**
     * Cria e inicializa o tabuleiro do jogo do velha.
     * Todas as posições inicial livres, ou seja, com #
     */
    public Tabuleiro(){
        velha = new char[3][3];
        for(int i=0; i<3;i++) 
            for(int j=0; j<3;j++)
                velha[i][j]='#';
    }
    
    /**
     * Gera um String do estado atual do tabuleiro
     */
    public String toString(){
        String saida=" ----- Jogo da Velha -----\n";
        for(int i=0; i<3; i++) saida = saida + "\t"+i;
        saida = saida + "\n";
        for(int i=0; i<3; i++){
            saida = saida + i +"\t";
            for(int j=0; j<3;j++) saida = saida + velha[i][j]+"\t";
            saida = saida + "\n";
        }
        return saida;
    }
    
    /**
     * Altera um posição i,j do tabuleiro com o caracter informado
     */
    public boolean alteraPosicao(int i, int j, char caracter){
        if(velha[i][j]!='#') return false;
        velha[i][j] = caracter;
        return true;
    }
    
    /**
     * Verifica se a posição i,j está ocupada
     */
    public boolean ocupada(int i, int j){
        if(velha[i][j]=='#') return false;
        return true;
    }
    
    /**
     * Faz a jogada do computador, usando o algoritmo Minimax
     */
    public int jogaComputador(){
        Minimax mini = new Minimax(velha);
        //Sucessor melhor = mini.getMelhor(); //chama versão clássica
        Sucessor melhor = mini.getMelhorAB(); //chama versão Alfa Beta Pruning
        velha = melhor.getEstado();
        return melhor.getValor();
    }
    
    /**
     * Indica se há vencedor
     */
    public int vencedor(){
        Minimax mini = new Minimax(velha);
        int profundidade = mini.livres(velha);
        return mini.utilidade(velha,profundidade);
    }
}
