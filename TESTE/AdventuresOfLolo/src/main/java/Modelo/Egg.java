package Modelo;

import java.io.Serializable;

import Controler.Tela;

public class Egg extends ElementoEstatico implements Serializable{
   
   public Egg() {
      super("egg.png");
      this.bDestrutivel = false;
      this.bMovel = true;
      this.bEhOvo = true;
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
