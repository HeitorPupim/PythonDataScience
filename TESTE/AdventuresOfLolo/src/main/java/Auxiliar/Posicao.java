package Auxiliar;

import java.io.Serializable;

public class Posicao implements Serializable{
   private int	linha;
   private int coluna;

   private int linhaAnterior;
   private int colunaAnterior;

   //Construtor:
   public Posicao(int linha, int coluna){
      this.setPosicao(linha,coluna);
      if(this.linhaAnterior == -1 || this.colunaAnterior == -1){
          this.linhaAnterior = this.linha;
          this.colunaAnterior = this.coluna;
      }
   }
   //Coloca posicao
   public boolean setPosicao(int linha, int coluna){
      if(linha < 0 || linha >= Auxiliar.Consts.RES)
         return false;
      
      
      linhaAnterior = this.linha;
      this.linha = linha;

      if(coluna < 0 || coluna >= Auxiliar.Consts.RES)
         return false;
      
      colunaAnterior = this.coluna;
      this.coluna = coluna;
      return true;   
   }
   
   public int getLinha(){
      return linha;
   }

   public int getLinhaAnterior() {
      return linhaAnterior;
   }

   public boolean volta(){
      return this.setPosicao(linhaAnterior,colunaAnterior);
   }

   public int getColuna(){
      return coluna;
   }

   public int getColunaAnterior() {
      return colunaAnterior;
   }

   public boolean estaNaMesmaPosicao(Posicao posicao){
      return (linha == posicao.getLinha() && coluna == posicao.getColuna());
   }

   public boolean copia(Posicao posicao){
      return this.setPosicao(posicao.getLinha(),posicao.getColuna());
   }

   //Faz mover o personagem/elemento.
   public boolean moveUp(){
      return this.setPosicao(this.getLinha()-1, this.getColuna());
   }
   public boolean moveDown(){
      return this.setPosicao(this.getLinha()+1, this.getColuna());
   }
   public boolean moveRight(){
      return this.setPosicao(this.getLinha(), this.getColuna()+1);
   }
   public boolean moveLeft(){
      return this.setPosicao(this.getLinha(), this.getColuna()-1);        
   }
}


