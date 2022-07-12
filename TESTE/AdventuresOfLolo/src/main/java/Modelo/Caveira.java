package Modelo;

import java.io.Serializable;
import Controler.Tela;

public class Caveira extends ElementoEstatico implements Serializable{
   
   public Caveira(){
      super("skull.png");
      this.bDestrutivel = false;
      this.bMovel = true;
      this.bViraOvo = true;
   }

   @Override
   public void checaColisao(Elemento e){
      if(e instanceof Link)
         return;
      if(e != Tela.getTela().getLolo()){
         e.voltaAUltimaPosicao();
      }
   }
}
