package game.snake;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

/**
 *
 * @author Sergio Guerrato
 */
public class Panel extends JPanel implements ActionListener{
    
    /** Declarações **/
    static final int SCREEN_WIDTH = 600;    // Define a largura da tela
    static final int SCREEN_HEIGHT = 600;   // Define a altura da tela
    static final int UNIT_SIZE = 25;    // Define o tamanho de pixels de 1 unidade do jogo
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;   // Calcula o número de unidades
    static final int DELAY = 75; // Define um delay (quanto maior, mais lento o jogo)
    final int x[] = new int [GAME_UNITS];   // Fixa o tamanho de X em unidades
    final int y[] = new int [GAME_UNITS];   // Fixa o tamanho de Y em unidades
    int bodyParts = 6;  // Define o tamanho inicial do corpo da cobra
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';   // Define a direção inicial como sendo Direita
    boolean running = false;    // Se true iniciará o jogo
    Timer timer;
    Random random;
    
    Panel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));  // Dimenciona o painel
        this.setBackground(Color.black);    // Define a cor de fundo
        this.setFocusable(true);    // Ativa o recebimento de dados input, como teclado e mouse    
        this.addKeyListener(new MyKeyAdapter());    // Adiciona o Key Listener
        startGame(); // Chama o método de start
    }
    
    public void startGame() {
        newApple(); // Chama o método de criar Maçãs
        running = true; // Define a flag de inicialização
        timer = new Timer(DELAY,this);  // Cria o timer com o delay predefinido
        timer.start();  // Inicia o timer
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);    // Chama o componente de colorir do Graphics
        draw(g);    // Chama o método draw
    }
    
    public void draw(Graphics g) {
        
        if(running) {
            /* **Cria uma grade de fundo para o jogo (opicional)**
            for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
                g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,SCREEN_HEIGHT);
                g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH,i*UNIT_SIZE);
            }
            */
            
            /** Define cor, formato, tamanho e posição da maçã **/
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE); 
        
            /** Cria as partes da cobra **/
            for(int i=0; i < bodyParts; i++) {
                if(i==0) {  // Define a cor, posição e tamanho da cabeça
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                else {  // Define cor, posição e tamanho do corpo
                    g.setColor(new Color(45,180,0));
                    //g.setColor(new Color(random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            
            /** Define as propriedades do texto do placar**/
            g.setColor(Color.red);  // Define a cor  
            g.setFont( new Font("Monospaced", Font.BOLD, 40));    // Define nome, estilo e tamanho da fonte
            FontMetrics metrics = getFontMetrics(g.getFont());  // Importa as informações do FontMetrics
            g.drawString("Apples:"+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Apples:"+applesEaten))/2, g.getFont().getSize());  // Escreve
        }
        else {
            gameOver(g); // Chama o método de Game Over
        }
    }
    
    public void newApple() {
        /** Define as coordenadas da nova maçã **/
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    
    public void move() {
        /** Instancia todas as partes do corpo da cobra **/
        for(int i= bodyParts;i>0;i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        
        /** Define para cada direção, a função que será executada **/
        switch(direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }
    
    public void checkApple() {
        /** Verifica se a coordenada da cabeça atingiu a coordenada da maça **/
        if((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;    // No caso de atingir, aumenta o corpo em 1
            applesEaten++;  // Aumenta o placar em 1  
            newApple();     // Gera nova maçã
        }
    }
    
    public void checkCollisions() {
        /** Verifica se a coordenada da cabeça atingiu alguma coordenada do corpo **/
        for(int i=bodyParts; i > 0; i--) {
            if((x[0] == x[i]) && (y[0] == y[i])){
                running = false; // Se atingir, finaliza o jogo
            }
        }
        
        /** Verifica se a cabeça atingiu a borda da esquerda **/
        if(x[0] < 0) {
            running = false;
        }
        
        /** Verifica se a cabeça atingiu a borda da direita **/
        if(x[0] > SCREEN_WIDTH) {
            running = false;
        }
        
        /** Verifica se a cabeça atingiu a borda de cima **/
        if(y[0] < 0) {
            running = false;
        }
        
        /** Verifica se a cabeça atingiu a borda de baixo **/
        if(x[0] > SCREEN_HEIGHT) {
            running = false;
        }
        
        /** Se alguma condição fez com que o jogo fosse finalizado, para o timer **/
        if(!running) {
            timer.stop();
        }
    }
    public void gameOver(Graphics g) {
        
        /** Define as propriedades do texto do placar final **/
        g.setColor(Color.red);  // Define a cor 
        g.setFont( new Font("Monospaced", Font.BOLD, 40));  // Define nome, estilo e tamanho da fonte
        FontMetrics metrics1 = getFontMetrics(g.getFont()); // Importa as informações do FontMetrics
        g.drawString("Apples:"+applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Apples:"+applesEaten))/2, g.getFont().getSize()); // Escreve
        
        /** Define as propriedades do texto do Game Over **/
        g.setColor(Color.red);  // Define a cor 
        g.setFont( new Font("Monospaced", Font.BOLD, 75));  // Define nome, estilo e tamanho da fonte
        FontMetrics metrics2 = getFontMetrics(g.getFont()); // Importa as informações do FontMetrics
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);   // Escreve
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        /** Sobreescreve o método de actionPerformed, acionando os métodos necessários para o jogo funcionar **/
        if(running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint(); // Solicita ao método actionPerformed para se redesenhar
    }
    
    public class MyKeyAdapter extends KeyAdapter{
        /** 
         * Sobrescreve o método do KeyAdapter mapeando as setas do teclado para cada função correspondente 
         * Impedindo a cabeça de ir para a direção oposta da que já estava indo
         **/
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                    
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                    
                case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                    
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
