package Modelo;

import Controler.Tela;
import java.io.Serializable;
import java.util.ArrayList;

public class Lolo extends ElementoAnimado implements Serializable {
   
   private int itensColetados = 0;
   private int bauColetado = 0;

   public Lolo(String sNomeImagePNG) {
      super(sNomeImagePNG);
   }

   //Colisao Lolo vs Todas as coisas:
    @Override
   public void checaColisao(Elemento e){
      if (e.elemViraOvo()) {
         e.viraOvo();
      }
   
      if (e.isColetavel()){
         Tela.getTela().tocaEfeito("get_item.wav");
         Tela.getTela().removeElemento(e);
         this.itensColetados++;

      } else if(e.isBau()){
         if(itensColetados == Tela.getTela().getNItensColetaveis()){
           Tela.getTela().tocaEfeito("get_item.wav");
         Tela.getTela().removeElemento(e);
         this.bauColetado++; 
         }

      }else if (e.isMortal()){
         //Lolo morre:
         this.itensColetados = 0;
         Tela.getTela().tiraVida();
         if (Tela.getTela().getVidas() > 0){
            Tela.getTela().reiniciaFase();
         }
      } else if (e.isMovel()){
         ElementoEstatico elemEstatico = (ElementoEstatico) e;
         //Se o ovo não puder se mover, ele some.
         if (!elemEstatico.movimenta(this.apontaUltimaPos())){
             if (elemEstatico.isOvo()){
                 Tela.getTela().removeElemento(elemEstatico);
             }else this.voltaAUltimaPosicao();
         }
         /*
         if ((!elemEstatico.movimenta(this.apontaUltimaPos())) && elemEstatico.isOvo()){
            Tela.getTela().removeElemento(elemEstatico);
         }*/
      }else if  (!e.isTransponivel()){ //bloco imovel.
         this.voltaAUltimaPosicao();
      }
   }

   public int getItensColetados(){
      return this.itensColetados;
   }

   public int getBauColetado(){
      return this.bauColetado;
   }

   //Tenta destruir um bloco na direcao do olhar do heroi
   public void destroiElemento(ArrayList<Elemento> e){
      if (this.direcaoOlhar == 'U') {
            destroiAux(e, this.pPosicao.getColuna(), this.pPosicao.getLinha() - 1);
      }  else if (this.direcaoOlhar == 'D') {
            destroiAux(e, this.pPosicao.getColuna(), this.pPosicao.getLinha() + 1);
      }  else if (this.direcaoOlhar == 'L') {
            destroiAux(e, this.pPosicao.getColuna() - 1, this.pPosicao.getLinha());
      }  else if (this.direcaoOlhar == 'R') {
            destroiAux(e, this.pPosicao.getColuna() + 1, this.pPosicao.getLinha());
      }
  }
  
   private void destroiAux(ArrayList<Elemento> e, int xPos, int yPos){
      for(int i = 0; i < e.size(); i++){
         if(e.get(i).pPosicao.getColuna() == xPos){
            if(e.get(i).pPosicao.getLinha() == yPos){
               if(e.get(i).isDestrutivel()){
                  Tela.getTela().tocaEfeito("break_block.wav");
                  e.remove(i);
               }
            }
         }
      }
   }


}
