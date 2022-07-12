package Auxiliar;

import java.awt.Graphics;
import java.io.Serializable;
import javax.swing.ImageIcon;
import Controler.Tela;

public class Desenho implements Serializable{
   static Tela cenario;
   
   public static void setCenario(Tela cenarioParaDesenhar) {
      cenario = cenarioParaDesenhar;
   }

   public static Tela getTelaDoJogo() {
      return cenario;
  }

   private static Graphics getGraphicsDaTela() {
      return cenario.getGraphicsBuffer();
   }  

   public static void desenhar(ImageIcon iImage, int iColuna, int iLinha) {
      iImage.paintIcon(cenario,getGraphicsDaTela(),iColuna * Consts.CELL_SIDE, iLinha * Consts.CELL_SIDE);
   }

}
