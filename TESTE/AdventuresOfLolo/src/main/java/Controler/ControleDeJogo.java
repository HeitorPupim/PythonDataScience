package Controler;
import Modelo.*;
import java.util.ArrayList;

public class ControleDeJogo {

   public void desenhaTudo(ArrayList<Elemento> e){
      for(int i = 0; i < e.size(); i++){
         Elemento eTemp1 = e.get(i);
         //Se estiver na mesma posição que Lolo:
         if(!eTemp1.isColetavel() && !eTemp1.isMortal() && !eTemp1.isMovel() &&
            !eTemp1.isDestrutivel() && !eTemp1.getPosicao().estaNaMesmaPosicao(e.get(0).getPosicao())){
               eTemp1.autoDesenho();
               e.get(0).autoDesenho();
         }else e.get(i).autoDesenho();
      }
   }

   public void processaTudo(ArrayList<Elemento> e){
      Elemento eTemp;
      Elemento eTemp2;

      //Operação de matriz:
      for(int i = 0; i < e.size(); i++){
         eTemp = e.get(i);
         for(int j = i+1; j < e.size(); j++){
            eTemp2 = e.get(j);
            if((j != i) && eTemp.getPosicao().estaNaMesmaPosicao(eTemp2.getPosicao())){
               if(i == 0){ //Para o caso de Lolo, verifica com todos os objetos se existe colisao
                  
                  Lolo loloTemp = (Lolo) eTemp;
                  loloTemp.checaColisao(eTemp2);
                  
                  //Caso o lolo colete todos os coraçõs, abre o baú:

                  if((loloTemp.getItensColetados() >= Tela.getTela().getNItensColetaveis() ) &&loloTemp.getBauColetado() == 1 ){
                        Tela.getTela().proximaFase();
                        return; 
                  }
                  //Quando as vidas acabarem:
                  if(Tela.getTela().getVidas() <= 0){
                     Tela.getTela().gameOver();
                     return;
                  }
                   
               }else if (eTemp.isMortal()){
                  eTemp = (Inimigo) eTemp;
                  eTemp.checaColisao(eTemp2);
               }else if(!eTemp.isTransponivel()){//Blocos e Elementos:
                  eTemp = (ElementoEstatico) eTemp;
                  eTemp.checaColisao(eTemp2);
               }
            
            }
         }
      }


   }

}
