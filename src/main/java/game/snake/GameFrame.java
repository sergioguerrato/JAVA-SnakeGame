package game.snake;

import javax.swing.JFrame;

/**
 *
 * @author Sergio Guerrato
 */
public class GameFrame extends JFrame {
    
    /** Cria a classe construtora do Game Frame **/
    GameFrame() {
        
        Panel panel = new Panel();  // Cria o painel onde será jogado
        
        this.add(panel);    // Insere o painel no Game Frame
        this.setTitle("Snake"); // Define o Título
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    // Define o métodos de encerramento do programa EXIT_ON CLOSE
        this.setResizable(false);   // Trava o redimencionamento do frame
        this.pack();    // Define os tamanhos do frame
        this.setVisible(true);  // Permite ver o frame
        this.setLocationRelativeTo(null); // Centraliza o jogo na tela
    }
}
