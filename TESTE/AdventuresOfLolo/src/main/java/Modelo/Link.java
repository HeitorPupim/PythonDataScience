package Modelo;

import java.io.Serializable;
import Controler.Tela;

public class Link extends Inimigo implements Serializable{
   //Inimigo hardcore que persegue o Lolo mais rapido.
   // Para incluir, tem que rodar a 
    
    int tipoMov = 0;

   public Link(){
      super("link_.png");
      this.timerMovimento = 9;
   }


   public void resetaImagem(){
      this.imagemLolo = Tela.getTela().getLolo();
   }

   @Override
   public boolean movimenta(){
      if(tipoMov == 0){
         tipoMov = 1;
         if (imagemLolo.pPosicao.getColuna() > pPosicao.getColuna()) {
             return moveRight();
         } else if(imagemLolo.pPosicao.getColuna() < pPosicao.getColuna()){
             return moveLeft();
         } else if(imagemLolo.pPosicao.getLinha() > pPosicao.getLinha()){
             return moveDown();
         } else if(imagemLolo.pPosicao.getLinha() < pPosicao.getLinha()){
             return moveUp();
         }
     } else {
         tipoMov = 0;
         if(imagemLolo.pPosicao.getLinha() > pPosicao.getLinha()){
             return moveDown();
         } else if(imagemLolo.pPosicao.getLinha() < pPosicao.getLinha()){
             return moveUp();
         } else if (imagemLolo.pPosicao.getColuna() > pPosicao.getColuna()) {
             return moveRight();
         } else if (imagemLolo.pPosicao.getColuna() < pPosicao.getColuna()) {
             return moveLeft();
         }
     }
     return false;
   }
   
}
