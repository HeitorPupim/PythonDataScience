package Modelo;

import java.io.Serializable;

import Controler.Tela;

public abstract class ElementoEstatico extends Elemento implements Serializable{

   protected ElementoEstatico(String sNomeImagePNG){
      super(sNomeImagePNG);
      this.bTransponivel = false;
   }

   public void checaColisao(Elemento e){
      if (e instanceof Link)
         return;
      if (this.isMovel()){
         // elemento e outras coisas:
         
         if (!e.isTransponivel() || e.isColetavel()){
            //Bloco transponivel ou item:
            this.voltaAUltimaPosicao();
         } else if (e.isMortal()){
            // se for um inimigo mortal:
            e.voltaAUltimaPosicao();
         }
      }else {
         // Boloco imovel x qualquer coisa:
         e.voltaAUltimaPosicao();
      }
   }
   
   public boolean movimenta(char UltimaDirecaoHeroi){
      if (UltimaDirecaoHeroi == 'R') {
         return this.moveLeft();
      } else if (UltimaDirecaoHeroi == 'L') {
         return this.moveRight();
      } else if (UltimaDirecaoHeroi == 'U') {
         return this.moveDown();
      } else if (UltimaDirecaoHeroi == 'D') {
         return this.moveUp();
      }
     return false;
   }

   @Override
   public boolean moveUp() {
      if (this.checaPosicao(this.pPosicao.getLinha() - 1, this.pPosicao.getColuna())) {
         return super.moveUp();
      } else {
         return false;
      }
   }

   @Override
   public boolean moveDown() {
      if (this.checaPosicao(this.pPosicao.getLinha() + 1, this.pPosicao.getColuna())) {
         return super.moveDown();
      } else {
         return false;
      }
   }

   @Override
   public boolean moveRight() {
      if (this.checaPosicao(this.pPosicao.getLinha(), this.pPosicao.getColuna() + 1)) {
         return super.moveRight();
      } else {
         return false;
      }
   }

   @Override
   public boolean moveLeft() {
      if (this.checaPosicao(this.pPosicao.getLinha(), this.pPosicao.getColuna() - 1)) {
         return super.moveLeft();
      } else {
         return false;
      }
   }
}
