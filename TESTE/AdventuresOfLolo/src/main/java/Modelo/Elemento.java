package Modelo;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import Auxiliar.Posicao;
import Controler.Tela;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import javax.swing.ImageIcon;


public abstract class Elemento implements Serializable {
   
   protected ImageIcon     iImage;
   public Posicao          pPosicao;
   protected char          direcaoOlhar; //Muda a direção q o boneco ta indo
   protected boolean       bAnimado;
   protected boolean       bTransponivel; /*Pode passar por cima?*/
   protected boolean       bMortal;       /*Se encostar, morre?*/
   protected boolean       bColetavel; //Coraçãozinho/ baú/etc.
   protected boolean       bDestrutivel; //Destroi elemento
   protected boolean       bMovel;
   protected boolean       bBau;
   protected boolean       bViraOvo;
   protected boolean       bEhOvo;
   protected boolean       bEhArvore;

   //Cria o elemento e pega a imagem do diretorio.
   protected Elemento(String sNomeImagePNG){
      this.pPosicao =      new Posicao(-1, -1);
      this.direcaoOlhar =  'D';
      this.bAnimado =      false;
      this.bTransponivel = true;
      this.bMortal =       false;
      this.bColetavel =    false;
      this.bDestrutivel =  false;
      this.bMovel =        false;
      this.bBau =          false;

   
      try{
         iImage = new ImageIcon(new java.io.File(".").getCanonicalPath() + Consts.PATH + sNomeImagePNG);
         Image img = iImage.getImage();
         BufferedImage bi = new BufferedImage(Consts.CELL_SIDE, Consts.CELL_SIDE, BufferedImage.TYPE_INT_ARGB);
         Graphics g = bi.createGraphics();
         g.drawImage(img, 0, 0, Consts.CELL_SIDE, Consts.CELL_SIDE, null);
         iImage = new ImageIcon(bi);

      }catch (IOException ex){
         System.out.println(ex.getMessage());
      }
   }

   public Posicao getPosicao(){
      return pPosicao;
   }

   public boolean isTransponivel(){
      return bTransponivel;
   }

   public boolean isMortal(){
      return bMortal;
   }
   public boolean isBau(){
      return bBau;
   }

   public boolean isColetavel(){
      return bColetavel;
   }
   
   public boolean isDestrutivel(){
      return bDestrutivel;
   }

   public boolean isArvore(){
      return bEhArvore;
   }
   
   public boolean elemViraOvo(){
      return bViraOvo;
   }

   public boolean isOvo(){
      return bEhOvo;
   }

   public boolean isMovel(){
      return bMovel;
   }

   public char getDirecaoOlhar(){
      return this.direcaoOlhar;
   }

   public void setDirecaoOlhar(char direcaoOlhar){
      this.direcaoOlhar = direcaoOlhar;
   }

   public boolean setPosicao(int linha, int coluna){
      return pPosicao.setPosicao(linha, coluna);
   }

   public boolean moveUp() {
      this.setDirecaoOlhar('U');
      return this.pPosicao.moveUp();
   }

   public boolean moveDown() {
      this.setDirecaoOlhar('D');
      return this.pPosicao.moveDown();
   }

   public boolean moveRight() {
      this.setDirecaoOlhar('R');
      return this.pPosicao.moveRight();
   }

   public boolean moveLeft() {
      this.setDirecaoOlhar('L');
      return this.pPosicao.moveLeft();
   }

   public void autoDesenho(){
      Desenho.desenhar(this.iImage, pPosicao.getColuna(), pPosicao.getLinha());        
   }

   public abstract void checaColisao(Elemento eTemp);

   //Retorna false se tiver um elemento na posicao recebida
   public boolean checaPosicao(int linha, int coluna) {
      for (Elemento eTemp : Tela.getTela().getArrayElementos()){
          if(eTemp != this){
            if (eTemp.pPosicao.getLinha() == linha) {
               if (eTemp.pPosicao.getColuna() == coluna) {
                  return false;
               }
            }
         }
      }
      return true;
   }

   public char apontaUltimaPos() {
      if (this.pPosicao.getLinhaAnterior() == this.pPosicao.getLinha()) {
         if (this.pPosicao.getColuna() < this.pPosicao.getColunaAnterior()) {
            return 'R';
         } 
         else if (this.pPosicao.getColuna() > this.pPosicao.getColunaAnterior()) {
            return 'L';
          }
      } else if (this.pPosicao.getColunaAnterior() == this.pPosicao.getColuna()) {
         if (this.pPosicao.getLinha() < this.pPosicao.getLinhaAnterior()) {
            return 'D';
         } 
         else if (this.pPosicao.getLinha() > this.pPosicao.getLinhaAnterior()) {
            return 'U';
         }
      }
      return 'M';
   }

   public boolean voltaAUltimaPosicao(){
      return this.pPosicao.volta();
   }


   public void viraOvo(Elemento this){
      Posicao posTemp = new Posicao(this.getPosicao().getLinha(), this.getPosicao().getColuna());
      Tela.getTela().removeElemento(this);

      Egg ovo = new Egg();
      ovo.setPosicao(posTemp.getLinha(), posTemp.getColuna());
      Tela.getTela().addElemento(ovo);
  }

}
