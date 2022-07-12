package Modelo;

import java.io.Serializable;

import Controler.Tela;

public class BlocoVerde extends ElementoEstatico implements Serializable{

   public BlocoVerde(){
      super("blocoMovel.png");
      this.bMovel = true;
      this.bTransponivel = false;
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