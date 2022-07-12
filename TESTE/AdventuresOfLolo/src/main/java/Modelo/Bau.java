package Modelo;

import java.io.Serializable;

import Controler.Tela;

public class Bau extends ElementoEstatico implements Serializable {
   
   public Bau() {
      super("bau.png");
      this.bTransponivel = false;
      this.bBau = true;
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
