package Modelo;

import java.io.Serializable;

import Controler.Tela;

public class Minhoca extends ElementoEstatico implements Serializable{
   // é imóvel e se transforma em ovo quando toca.
   public Minhoca() {
      super("cat.png");
      this.bDestrutivel = false;
      this.bMovel = true;
      this.bViraOvo = true;
   }

   //implementar a mudança de imagem se houver colisao com o Lolo.
   @Override
   public void checaColisao(Elemento e){
      if(e instanceof Link)
         return;
      if(e != Tela.getTela().getLolo()){
         e.voltaAUltimaPosicao();
      }
   }
}
