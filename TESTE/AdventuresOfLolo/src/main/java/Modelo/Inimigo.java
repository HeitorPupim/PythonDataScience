package Modelo;
import Controler.Tela;
import java.io.Serializable;

public abstract class Inimigo extends ElementoAnimado implements Serializable{
   Lolo imagemLolo;
   int contadorRandom = 0;
   int timerMovimento;
   long framesContados = 10; //velocidade de movimento

   public Inimigo (String sNomeImagePNG){
      super(sNomeImagePNG);
      this.bMortal = true;
      this.timerMovimento = 10;
   }

   @Override
   public void checaColisao(Elemento e) {
      if(e instanceof Link)
          return;
      if (e != Tela.getTela().getLolo()) {
          this.voltaAUltimaPosicao();
      }
  }

   @Override
   public boolean checaPosicao(int linha, int coluna){
      for (Elemento e : Tela.getTela().getArrayElementos()){
         if (e != this){
            if (e.pPosicao.getLinha() == linha) {
               if (e.pPosicao.getColuna() == coluna) {
                   return e == Tela.getTela().getLolo();
               }
            }
         }
      }
      return true;
   }

   @Override
   public void autoDesenho(){
      super.autoDesenho();
      
      //movimenta();
      
      if(!Tela.getTela().getLoading()){
         if(framesContados >= timerMovimento){
               movimenta();
               framesContados = 5;
         } else{
               framesContados++;
         }
      }
   }
   public abstract boolean movimenta();
}