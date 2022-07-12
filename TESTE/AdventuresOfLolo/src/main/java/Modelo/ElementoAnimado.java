package Modelo;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.ImageIcon;

public abstract class ElementoAnimado extends Elemento {
   
   protected ImageIcon[] iImages;

   //Precisa ter as imagens de direção!
   public ElementoAnimado(String sNomeImagePNG) {
      super(sNomeImagePNG);
      this.bAnimado = true;

      //Entra em loop e pega as imagens de olhar do elemento.
      // *** falta colocar as imagens de direção, por enquanto coloca a msm.

      try{
         iImages = new ImageIcon[4];
         char direcao = 'M';
         for (int i = 0; i < 4; i++) {
            if (i == 0){
               direcao = 'U';
            } else if(i == 1){
               direcao = 'D';
            } else if(i == 2){
            direcao = 'L';
            } else if(i == 3){
               direcao = 'R';
            }
            //Substitui a letra no nome da imagem para a imagem de direção coreta..
            iImages[i] = new ImageIcon(new java.io.File(".").getCanonicalPath() + Consts.PATH + sNomeImagePNG.replace(".", direcao + "."));
            Image img = iImages[i].getImage();
            BufferedImage bi = new BufferedImage(Consts.CELL_SIDE, Consts.CELL_SIDE, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            g.drawImage(img, 0, 0, Consts.CELL_SIDE, Consts.CELL_SIDE, null);
            iImages[i] = new ImageIcon(bi);
         }
      } catch (IOException ex){
         System.out.println(ex.getMessage());
      }
   }


   @Override
   public void autoDesenho(){
      if (this.getDirecaoOlhar() == 'U'){ 
         Desenho.desenhar(this.iImages[0], pPosicao.getColuna(), pPosicao.getLinha());
      } else if(this.getDirecaoOlhar() == 'D'){
         Desenho.desenhar(this.iImages[1], pPosicao.getColuna(), pPosicao.getLinha());
      } else if(this.getDirecaoOlhar() == 'L'){
         Desenho.desenhar(this.iImages[2], pPosicao.getColuna(), pPosicao.getLinha());
      } else if(this.getDirecaoOlhar() == 'R'){
         Desenho.desenhar(this.iImages[3], pPosicao.getColuna(), pPosicao.getLinha());
      }   
   }
}
