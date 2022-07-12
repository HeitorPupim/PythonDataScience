package Controler;


public class Main {

   public static void main(String[] args) {
      /*Coloca a criacao de "tTela" numa fila, em caso de demora para
      responder o programa espera a anterior finalizar o processo*/
      java.awt.EventQueue.invokeLater(new Runnable() {
         public void run() {
               Tela tTela = Tela.getTela();
               tTela.setVisible(true);
               tTela.createBufferStrategy(2);
               tTela.go();
         }
      });
   }
}
