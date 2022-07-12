package Controler;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import Modelo.*;
import Auxiliar.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.logging.*;
import javax.sound.sampled.*;
import javax.swing.JFileChooser;

public class Tela extends javax.swing.JFrame implements MouseListener, KeyListener {

    private static Tela instancia;

    private Lolo lLolo;
    private ArrayList<Elemento> lElementos;
    private ControleDeJogo cj = new ControleDeJogo();
    private Graphics g2;
    private boolean esperandoTecla = true;
    private boolean loading = false;
    private long lastPress = 0;
    private int vidas = 5;
    private Music soundtrack;
    private int nItensColetaveis;

    //Cria uma tela com leitores de teclao, mouse e elementos..
    public Tela() {
        Desenho.setCenario(this);
        initComponents();
        
        this.addMouseListener(this); /*mouse*/
        this.addKeyListener(this);   /*teclado*/

        /*Configurações de resolução da janela.*/
        this.setSize(Consts.RES * Consts.CELL_SIDE + getInsets().left + getInsets().right,
                Consts.RES * Consts.CELL_SIDE + getInsets().top + getInsets().bottom);

        lElementos = new ArrayList<Elemento>(100);
        
        this.proximaFase();
        this.setTitle("Adventures of Lolo");
        Saver.criaAutoSave();
    }   

/* ---------------------------------------------------------------- */
// Getters/Setters:

    public void addElemento(Elemento esseElemento){
        lElementos.add(esseElemento);
    }

    public void removeElemento(Elemento esseElemento){
        lElementos.remove(esseElemento);
    }

    public void resetaArray(){
        lElementos.clear();
    }

    public Graphics getGraphicsBuffer(){
        return g2;
    }

    /*Padrão de design Singleton implementado:
    Na primeira vez que getTela é chamado (Nota-se que é um método estático),
    irá criar uma instancia de Tela, após isso, retornará essa mesma instancia
    sem nunca poder criar outras (O construtor de Tela é private)*/
    public static Tela getTela() {
        if (instancia == null) {
            instancia = new Tela();
        }
        return instancia;
    }

    public static boolean isInstanciada(){
        return instancia != null;
    }

    /*Este método é executado a cada Consts.FRAME_INTERVAL milissegundos*/  
    public void paint(Graphics gOld) {
        Graphics g = this.getBufferStrategy().getDrawGraphics();

        //cria um contexto gráfico do tamanho da tela:

        g2 =g.create(   getInsets().left, 
                        getInsets().top,
                        getWidth() - getInsets().right, 
                        getHeight() - getInsets().top);


    
        //Desenha imagem de fundo grande ao invés de esenhar várias pequenas:
        try {
            Image newImage = Toolkit.getDefaultToolkit().getImage(new java.io.File(".").getCanonicalPath() + Consts.PATH + Fases.backgroundImg);
            g2.drawImage(newImage, 0, 0, null);
        } catch (IOException ex) {
            Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
        }

        /*Só processa elementos se o array não estiver vazio e estiver em uma fase*/
        if (!this.lElementos.isEmpty() || !loading) {
            this.cj.processaTudo(lElementos);
            this.cj.desenhaTudo(lElementos);
        }
        
        /*Esvazia os espacos de memoria ocupados pelos Buffers*/
        g.dispose();
        g2.dispose();
        if (!getBufferStrategy().contentsLost()) {
            getBufferStrategy().show();
        }
    }


    public void go(){
        TimerTask redesenhar = new TimerTask(){
            public void run(){
                repaint(); // faz o repaint da tela
            }
        };

        Timer timer = new Timer();
        timer.schedule(redesenhar, 0, Consts.FRAME_INTERVAL);
    }

    public void keyPressed(KeyEvent ke){
        // delay de movimento -> em ms
        if(System.currentTimeMillis() - lastPress > Consts.FRAME_INTERVAL + 20){
            if (ke.getKeyCode() == KeyEvent.VK_UP){
                lLolo.moveUp();
            } else if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
                lLolo.moveDown();
            } else if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
                lLolo.moveLeft();
            } else if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
                lLolo.moveRight();
            }
            else if (ke.getKeyCode() == KeyEvent.VK_P) {
                Fases.proximaFase(lElementos);
                lLolo = (Lolo) lElementos.get(0);
            } else if (ke.getKeyCode() == KeyEvent.VK_R) {
                this.tiraVida();
                if (vidas <= 0) {
                    Tela.getTela().gameOver();
                } else{
                    this.reiniciaFase();
                }
            } else if (ke.getKeyCode() == KeyEvent.VK_L) {
                carregaJogo();
            } else if (ke.getKeyCode() == KeyEvent.VK_S) {
                salvaJogo();
            }
            lastPress = System.currentTimeMillis();
        }
        /*Evento da barra de espaco separado pra que nao tenha delay entre andar 
        e poder destruir um bloco*/
        if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
            if(esperandoTecla){
                this.criaMusica();
                this.reiniciaFase();
                setEsperandoTecla(false);
            }
            lLolo.destroiElemento(lElementos);
        }
    }

    public void carregaJogo(){
        if(!this.loading){
            this.resetaArray();
            Saver.loadGame();
            lLolo = (Lolo) lElementos.get(0);
            if (soundtrack != null) {
                criaMusica();
            }
        }
    }

    public void salvaJogo(){
        if(!this.loading){
            System.out.println("Salvando Jogo...");
            Saver.salvaJogo();
        }
    }
    
    public void proximaFase(){
        Fases.proximaFase(lElementos);
        lLolo = (Lolo) lElementos.get(0);
    }
    
    public void reiniciaFase(){
        Fases.resetaFase(lElementos);
        lLolo = (Lolo) lElementos.get(0);
    }
    
    public void gameOver(){
        Fases.gameOver(lElementos);
        lLolo = (Lolo) lElementos.get(0);
        this.vidas = 3;
    }
    
    public boolean getLoading(){
        return loading;
    }

    public void setNItensColetaveis(int i) {
        nItensColetaveis = i;
    }
  
    public int getNItensColetaveis() {
        return nItensColetaveis;
    }
    
    public void criaMusica() {
        try {
            String currentPath = new java.io.File(".").getCanonicalPath();
            String soundtrackPath = currentPath + Consts.PATH_SOUND + "soundtrack.wav";
            soundtrack = new Music(soundtrackPath);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void playMusica(){
        soundtrack.play();
    }
    
    public void pausaMusica(){
        soundtrack.pause();
    }
    
    public void setLoading(boolean loading){
        this.loading = loading;
    }
    
    public void setEsperandoTecla(boolean esperandoTecla){
        this.esperandoTecla = esperandoTecla;
    }
    
    public ArrayList<Elemento> getArrayElementos(){
        return lElementos;
    }
    
    public Lolo getLolo(){
        return (Lolo) lElementos.get(0);
    }
    
    public int getVidas(){
        return vidas;
    }
    
    public void setVidas(int novaVidas){
        vidas = novaVidas;
    }
    
    public int tiraVida(){
        vidas--;
        tocaEfeito("lose_life.wav");
        System.out.println("Vida perdida!");
        if(vidas == 0){
            System.out.println("Voce morreu...");
        } else {
            System.out.println("Vidas atuais: " + Tela.getTela().getVidas());
        }
        return vidas;
    }
    
    public void tocaEfeito(String nomeArquivo){
        try {
            SoundEffect collision = new SoundEffect(nomeArquivo);
            collision.play();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
        }
    }
    
    public void substituiElemento(Elemento elem){
        int x = elem.pPosicao.getColuna();
        int y = elem.pPosicao.getLinha();
        
        lElementos.remove(elem);
        
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        File arqElemento = null;
         
        this.loading = true;
        int valorRetornado = chooser.showOpenDialog(null);
        if(valorRetornado == JFileChooser.APPROVE_OPTION) {
            arqElemento = chooser.getSelectedFile();
        }
        
        if (!arqElemento.exists()) {
            System.out.println("Falha em carregar arquivo");
        }
        try{
            // Efetua a leitura no arquivo
            FileInputStream fileStream = new FileInputStream(arqElemento);
            ObjectInputStream inputStream = new ObjectInputStream(fileStream);
            
            Elemento novoElemento = (Elemento) inputStream.readObject();
            novoElemento.setPosicao(y, x);
            if(novoElemento instanceof Link){
                ((Link) novoElemento).resetaImagem();
            }
            lElementos.add(novoElemento);
            
            fileStream.close();
            inputStream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.loading = false;
    }

/* -------------------NÃO MUDAR DAQUI PRA BAIXO ------------------------------------ */
    public void mousePressed(MouseEvent e) {
        int mX = 0;
        int mY = 0;
        
        if(e.getButton() == MouseEvent.BUTTON3){
           
            for(Elemento eTemp : lElementos){
                if(eTemp.pPosicao.getColuna() == mX){
                    if(eTemp.pPosicao.getLinha() == mY){
                        substituiElemento(eTemp);
                        break;
                    }
                }
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("POO2021");
        setAutoRequestFocus(false);
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 561, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}

