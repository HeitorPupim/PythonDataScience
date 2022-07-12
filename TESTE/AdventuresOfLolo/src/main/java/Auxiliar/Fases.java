package Auxiliar;

import Modelo.BlocoVerde;
import Modelo.Bau;
import Controler.Tela;
import Modelo.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class Fases {
   public static int fase = 0;
   public static String backgroundImg;
   public static ArrayList<String> transicoes;

   public static void proximaFase(ArrayList<Elemento> lElementos){
      if (fase == 0){
         Fases.criaTransicoes();
         
      }
      lElementos.clear();
      Fases.transicaoDeFase(lElementos, transicoes.get(fase));
      fase++;
   }

   public static void resetaFase(ArrayList<Elemento> lElementos){
      lElementos.clear();
      Fases.iniciaFase(fase, lElementos);
   }

   public static void gameOver(ArrayList<Elemento> lElementos){
      lElementos.clear();
      backgroundImg = "gameOver.png";

      Lolo dummyLolo = new Lolo("");
      lElementos.add(dummyLolo);

      Tela.getTela().setEsperandoTecla(true);
      Tela.getTela().pausaMusica();

      //Cria um timer para quea a primeira fase seja exibida com um delay em ms:

      Timer timer = new Timer();
      timer.schedule(new TimerTask(){
         public void run(){
            fase = 0;
            Fases.proximaFase(lElementos);
         }
      }, 5000);
   }
   
   public static void iniciaFase(int numFase, ArrayList<Elemento> faseAtual){
      if (numFase == 1){
         faseAtual.addAll(primeiraFase());
      }
      
      if (numFase == 2){
         faseAtual.addAll(segundaFase());
      }
      
      if (numFase == 3){
         faseAtual.addAll(terceiraFase());
      } 
   }

   private static void criaTransicoes(){
      transicoes = new ArrayList<>();
      transicoes.add("startMenu.png");
      transicoes.add("transicao1.png");
      transicoes.add("transicao2.png");
      transicoes.add("youWin.png");
   }

   //Cria uma fase vazia e sem musica so com o background de transicao
   private static void transicaoDeFase(ArrayList<Elemento> lElementos, String telaTransicao){
      backgroundImg = telaTransicao;
      int delay;
      
      Lolo dummyLolo = new Lolo("");
      lElementos.add(dummyLolo);
      
      if(fase > 0){
         Tela.getTela().pausaMusica();
         Tela.getTela().setLoading(true);
         delay = 3000;
         
         if(fase == 4){
            delay += 5000;
         }

         /*Cria um timer para que a proxima fase seja exibida em "delay" ms*/
         Timer timer = new Timer();
         timer.schedule(new TimerTask(){
            public void run(){
               if(fase == 4){
                     System.exit(0);
               }else{
                     lElementos.clear();
                     Fases.iniciaFase(fase, lElementos);
                     Tela.getTela().setLoading(false);
                     Tela.getTela().reiniciaFase();
                     Tela.getTela().playMusica();
               }
            }
         }, delay);
      }
   }

   //Cada fase é composta de um array com elementos que é copiado para trocar
   private static ArrayList<Elemento> primeiraFase(){
      backgroundImg = "background1.png";
      
      ArrayList<Elemento> fase1 = new ArrayList<>();

      Tela.getTela().setNItensColetaveis(6);

      //Lolo:
      Lolo lolo = new Lolo("lolo.png");
      lolo.setPosicao(7, 5);
      fase1.add(lolo);

      //MINHOCA:
      
      Minhoca m1 = new Minhoca();
      m1.setPosicao(1, 1);
      fase1.add(m1);

      Minhoca m2 = new Minhoca();
      m2.setPosicao(6, 4);
      fase1.add(m2);

      Minhoca m3 = new Minhoca();
      m3.setPosicao(6, 8);
      fase1.add(m3);


      //BAU:
      Bau b1 = new Bau();
      b1.setPosicao(2, 5);
      fase1.add(b1); 



      //CORACAO:
      Coracao coracao1 = new Coracao();
      coracao1.setPosicao(1, 0);
      fase1.add(coracao1); 

      Coracao coracao2 = new Coracao();
      coracao2.setPosicao(10, 1);
      fase1.add(coracao2); 

      Coracao coracao3 = new Coracao();
      coracao3.setPosicao(10, 7);
      fase1.add(coracao3); 

      Coracao coracao4 = new Coracao();
      coracao4.setPosicao(10, 8);
      fase1.add(coracao4); 

      Coracao coracao5 = new Coracao();
      coracao5.setPosicao(0, 7);
      fase1.add(coracao5); 

      Coracao coracao6 = new Coracao();
      coracao6.setPosicao(0, 10);
      fase1.add(coracao6); 



      // ARVORES:
      ElementoEstatico arvore1 = EEFactory.criaElementoEstatico("arvore");
      arvore1.setPosicao(0, 0);
      fase1.add(arvore1);

      ElementoEstatico arvore2 = EEFactory.criaElementoEstatico("arvore");
      arvore2.setPosicao(2, 0);
      fase1.add(arvore2);

      ElementoEstatico arvore3 = EEFactory.criaElementoEstatico("arvore");
      arvore3.setPosicao(3, 0);
      fase1.add(arvore3);

      ElementoEstatico arvore4 = EEFactory.criaElementoEstatico("arvore");
      arvore4.setPosicao(6, 0);
      fase1.add(arvore4);

      ElementoEstatico arvore5 = EEFactory.criaElementoEstatico("arvore");
      arvore5.setPosicao(7, 0);
      fase1.add(arvore5);

      ElementoEstatico arvore6 = EEFactory.criaElementoEstatico("arvore");
      arvore6.setPosicao(8, 0);
      fase1.add(arvore6);

      ElementoEstatico arvore7 = EEFactory.criaElementoEstatico("arvore");
      arvore7.setPosicao(9, 0);
      fase1.add(arvore7);

      ElementoEstatico arvore8 = EEFactory.criaElementoEstatico("arvore");
      arvore8.setPosicao(10, 0);
      fase1.add(arvore8);

      ElementoEstatico arvore9 = EEFactory.criaElementoEstatico("arvore");
      arvore9.setPosicao(7, 1);
      fase1.add(arvore9);

      ElementoEstatico arvore10 = EEFactory.criaElementoEstatico("arvore");
      arvore10.setPosicao(8, 1);
      fase1.add(arvore10);

      ElementoEstatico arvore11 = EEFactory.criaElementoEstatico("arvore");
      arvore11.setPosicao(1, 2);
      fase1.add(arvore11);

      ElementoEstatico arvore12 = EEFactory.criaElementoEstatico("arvore");
      arvore12.setPosicao(2, 2);
      fase1.add(arvore12);

      ElementoEstatico arvore13 = EEFactory.criaElementoEstatico("arvore");
      arvore13.setPosicao(1, 3);
      fase1.add(arvore13);

      ElementoEstatico arvore14 = EEFactory.criaElementoEstatico("arvore");
      arvore14.setPosicao(2, 3);
      fase1.add(arvore14);

      ElementoEstatico arvore15 = EEFactory.criaElementoEstatico("arvore");
      arvore15.setPosicao(10, 3);
      fase1.add(arvore15);

      ElementoEstatico arvore16 = EEFactory.criaElementoEstatico("arvore");
      arvore16.setPosicao(9, 4);
      fase1.add(arvore16);

      ElementoEstatico arvore17 = EEFactory.criaElementoEstatico("arvore");
      arvore17.setPosicao(10, 4);
      fase1.add(arvore17);

      ElementoEstatico arvore18 = EEFactory.criaElementoEstatico("arvore");
      arvore18.setPosicao(9, 5);
      fase1.add(arvore18);

      ElementoEstatico arvore19 = EEFactory.criaElementoEstatico("arvore");
      arvore19.setPosicao(10, 5);
      fase1.add(arvore19);

      ElementoEstatico arvore20 = EEFactory.criaElementoEstatico("arvore");
      arvore20.setPosicao(8, 6);
      fase1.add(arvore20);

      ElementoEstatico arvore21 = EEFactory.criaElementoEstatico("arvore");
      arvore21.setPosicao(9, 6);
      fase1.add(arvore21);

      ElementoEstatico arvore22 = EEFactory.criaElementoEstatico("arvore");
      arvore22.setPosicao(10, 6);
      fase1.add(arvore22);

      ElementoEstatico arvore23 = EEFactory.criaElementoEstatico("arvore");
      arvore23.setPosicao(1, 7);
      fase1.add(arvore23);

      ElementoEstatico arvore24 = EEFactory.criaElementoEstatico("arvore");
      arvore24.setPosicao(2, 7);
      fase1.add(arvore24);

      ElementoEstatico arvore25 = EEFactory.criaElementoEstatico("arvore");
      arvore25.setPosicao(0, 8);
      fase1.add(arvore25);

      ElementoEstatico arvore26 = EEFactory.criaElementoEstatico("arvore");
      arvore26.setPosicao(1, 8);
      fase1.add(arvore26);

      ElementoEstatico arvore27 = EEFactory.criaElementoEstatico("arvore");
      arvore27.setPosicao(2, 8);
      fase1.add(arvore27);

      ElementoEstatico arvore28 = EEFactory.criaElementoEstatico("arvore");
      arvore28.setPosicao(3, 8);
      fase1.add(arvore28);

      ElementoEstatico arvore29 = EEFactory.criaElementoEstatico("arvore");
      arvore29.setPosicao(0, 9);
      fase1.add(arvore29);

      ElementoEstatico arvore30 = EEFactory.criaElementoEstatico("arvore");
      arvore30.setPosicao(1, 9);
      fase1.add(arvore30);

      ElementoEstatico arvore31 = EEFactory.criaElementoEstatico("arvore");
      arvore31.setPosicao(2, 9);
      fase1.add(arvore31);

      ElementoEstatico arvore32 = EEFactory.criaElementoEstatico("arvore");
      arvore32.setPosicao(9, 9);
      fase1.add(arvore32);

      ElementoEstatico arvore33 = EEFactory.criaElementoEstatico("arvore");
      arvore33.setPosicao(10, 9);
      fase1.add(arvore33);

      ElementoEstatico arvore34 = EEFactory.criaElementoEstatico("arvore");
      arvore34.setPosicao(8, 10);
      fase1.add(arvore34);

      ElementoEstatico arvore35 = EEFactory.criaElementoEstatico("arvore");
      arvore35.setPosicao(9, 10);
      fase1.add(arvore35);

      ElementoEstatico arvore36 = EEFactory.criaElementoEstatico("arvore");
      arvore36.setPosicao(10, 10);
      fase1.add(arvore36);

   
      return fase1;
   }


   private static ArrayList<Elemento> segundaFase(){
      backgroundImg = "background1.png";
      
      ArrayList<Elemento> fase2 = new ArrayList<>();

      Tela.getTela().setNItensColetaveis(6);


      //Lolo:
      Lolo lolo = new Lolo("lolo.png");
      lolo.setPosicao(10, 10);
      fase2.add(lolo);
      
      //BAU:
      Bau b1 = new Bau();
      b1.setPosicao(0, 0);
      fase2.add(b1); 


      //CORACAO:
      Coracao coracao1 = new Coracao();
      coracao1.setPosicao(0, 2);
      fase2.add(coracao1); 

      Coracao coracao2 = new Coracao();
      coracao2.setPosicao(0, 6);
      fase2.add(coracao2); 

      Coracao coracao3 = new Coracao();
      coracao3.setPosicao(5, 0);
      fase2.add(coracao3); 

      Coracao coracao4 = new Coracao();
      coracao4.setPosicao(7, 10);
      fase2.add(coracao4); 

      Coracao coracao5 = new Coracao();
      coracao5.setPosicao(8, 6);
      fase2.add(coracao5); 

      Coracao coracao6 = new Coracao();
      coracao6.setPosicao(9, 0);
      fase2.add(coracao6); 
      
      //Minhoca:
      Minhoca m1 = new Minhoca();
      m1.setPosicao(6, 4);
      fase2.add(m1);
      
      //CAVEIRA:
      Caveira c1 = new Caveira();
      c1.setPosicao(2, 4);
      fase2.add(c1);

      Caveira c2 = new Caveira();
      c2.setPosicao(5, 7);
      fase2.add(c2);





      // ARVORES:
      ElementoEstatico arvore0 = EEFactory.criaElementoEstatico("arvore");
      arvore0.setPosicao(0, 1);
      fase2.add(arvore0);

      ElementoEstatico arvore1 = EEFactory.criaElementoEstatico("arvore");
      arvore1.setPosicao(0, 3);
      fase2.add(arvore1);

      ElementoEstatico arvore2 = EEFactory.criaElementoEstatico("arvore");
      arvore2.setPosicao(0, 4);
      fase2.add(arvore2);

      ElementoEstatico arvore3 = EEFactory.criaElementoEstatico("arvore");
      arvore3.setPosicao(0, 5);
      fase2.add(arvore3);

      ElementoEstatico arvore4 = EEFactory.criaElementoEstatico("arvore");
      arvore4.setPosicao(0, 7);
      fase2.add(arvore4);

      ElementoEstatico arvore5 = EEFactory.criaElementoEstatico("arvore");
      arvore5.setPosicao(1, 3);
      fase2.add(arvore5);

      ElementoEstatico arvore6 = EEFactory.criaElementoEstatico("arvore");
      arvore6.setPosicao(1, 5);
      fase2.add(arvore6);

      ElementoEstatico arvore7 = EEFactory.criaElementoEstatico("arvore");
      arvore7.setPosicao(1, 7);
      fase2.add(arvore7);

      ElementoEstatico arvore8 = EEFactory.criaElementoEstatico("arvore");
      arvore8.setPosicao(1, 9);
      fase2.add(arvore8);

      ElementoEstatico arvore9 = EEFactory.criaElementoEstatico("arvore");
      arvore9.setPosicao(2, 1);
      fase2.add(arvore9);

      ElementoEstatico arvore10 = EEFactory.criaElementoEstatico("arvore");
      arvore10.setPosicao(2, 7);
      fase2.add(arvore10);

      ElementoEstatico arvore11 = EEFactory.criaElementoEstatico("arvore");
      arvore11.setPosicao(2, 9);
      fase2.add(arvore11);

      ElementoEstatico arvore12 = EEFactory.criaElementoEstatico("arvore");
      arvore12.setPosicao(3, 9);
      fase2.add(arvore12);

      ElementoEstatico arvore13 = EEFactory.criaElementoEstatico("arvore");
      arvore13.setPosicao(3, 1);
      fase2.add(arvore13);

      ElementoEstatico arvore14 = EEFactory.criaElementoEstatico("arvore");
      arvore14.setPosicao(3, 3);
      fase2.add(arvore14);

      ElementoEstatico arvore15 = EEFactory.criaElementoEstatico("arvore");
      arvore15.setPosicao(3,5);
      fase2.add(arvore15);

      ElementoEstatico arvore16 = EEFactory.criaElementoEstatico("arvore");
      arvore16.setPosicao(3, 7);
      fase2.add(arvore16);

      ElementoEstatico arvore17 = EEFactory.criaElementoEstatico("arvore");
      arvore17.setPosicao(4, 0);
      fase2.add(arvore17);

      ElementoEstatico arvore18 = EEFactory.criaElementoEstatico("arvore");
      arvore18.setPosicao(4, 1);
      fase2.add(arvore18);

      ElementoEstatico arvore19 = EEFactory.criaElementoEstatico("arvore");
      arvore19.setPosicao(4, 5);
      fase2.add(arvore19);

      ElementoEstatico arvore20 = EEFactory.criaElementoEstatico("arvore");
      arvore20.setPosicao(4, 9);
      fase2.add(arvore20);

      ElementoEstatico arvore21 = EEFactory.criaElementoEstatico("arvore");
      arvore21.setPosicao(5, 2);
      fase2.add(arvore21);

      ElementoEstatico arvore22 = EEFactory.criaElementoEstatico("arvore");
      arvore22.setPosicao(5, 3);
      fase2.add(arvore22);

      ElementoEstatico arvore23 = EEFactory.criaElementoEstatico("arvore");
      arvore23.setPosicao(5, 4);
      fase2.add(arvore23);

      ElementoEstatico arvore24 = EEFactory.criaElementoEstatico("arvore");
      arvore24.setPosicao(5, 6);
      fase2.add(arvore24);

      ElementoEstatico arvore25 = EEFactory.criaElementoEstatico("arvore");
      arvore25.setPosicao(5, 8);
      fase2.add(arvore25);

      ElementoEstatico arvore26 = EEFactory.criaElementoEstatico("arvore");
      arvore26.setPosicao(6, 0);
      fase2.add(arvore26);

      ElementoEstatico arvore27 = EEFactory.criaElementoEstatico("arvore");
      arvore27.setPosicao(6, 10);
      fase2.add(arvore27);

      ElementoEstatico arvore28 = EEFactory.criaElementoEstatico("arvore");
      arvore28.setPosicao(7, 2);
      fase2.add(arvore28);

      ElementoEstatico arvore29 = EEFactory.criaElementoEstatico("arvore");
      arvore29.setPosicao(7, 3);
      fase2.add(arvore29);

      ElementoEstatico arvore30 = EEFactory.criaElementoEstatico("arvore");
      arvore30.setPosicao(7, 4);
      fase2.add(arvore30);

      ElementoEstatico arvore31 = EEFactory.criaElementoEstatico("arvore");
      arvore31.setPosicao(7, 5);
      fase2.add(arvore31);

      ElementoEstatico arvore32 = EEFactory.criaElementoEstatico("arvore");
      arvore32.setPosicao(7, 6);
      fase2.add(arvore32);

      ElementoEstatico arvore33 = EEFactory.criaElementoEstatico("arvore");
      arvore33.setPosicao(7, 7);
      fase2.add(arvore33);

      ElementoEstatico arvore34 = EEFactory.criaElementoEstatico("arvore");
      arvore34.setPosicao(7, 8);
      fase2.add(arvore34);

      ElementoEstatico arvore35 = EEFactory.criaElementoEstatico("arvore");
      arvore35.setPosicao(7, 9);
      fase2.add(arvore35);

      ElementoEstatico arvore36 = EEFactory.criaElementoEstatico("arvore");
      arvore36.setPosicao(8, 7);
      fase2.add(arvore36);

      ElementoEstatico arvore37 = EEFactory.criaElementoEstatico("arvore");
      arvore37.setPosicao(9, 1);
      fase2.add(arvore37);
      
      ElementoEstatico arvore38 = EEFactory.criaElementoEstatico("arvore");
      arvore38.setPosicao(9, 3);
      fase2.add(arvore38);

      ElementoEstatico arvore39 = EEFactory.criaElementoEstatico("arvore");
      arvore39.setPosicao(9, 5);
      fase2.add(arvore39);

      ElementoEstatico arvore40 = EEFactory.criaElementoEstatico("arvore");
      arvore40.setPosicao(9, 6);
      fase2.add(arvore40);

      ElementoEstatico arvore41 = EEFactory.criaElementoEstatico("arvore");
      arvore41.setPosicao(9, 9);
      fase2.add(arvore41);

      ElementoEstatico arvore42 = EEFactory.criaElementoEstatico("arvore");
      arvore42.setPosicao(10, 0);
      fase2.add(arvore42);

      ElementoEstatico arvore43 = EEFactory.criaElementoEstatico("arvore");
      arvore43.setPosicao(10, 1);
      fase2.add(arvore43);

      ElementoEstatico arvore44 = EEFactory.criaElementoEstatico("arvore");
      arvore44.setPosicao(10, 3);
      fase2.add(arvore44);

      ElementoEstatico arvore45 = EEFactory.criaElementoEstatico("arvore");
      arvore45.setPosicao(10, 8);
      fase2.add(arvore45);

      ElementoEstatico arvore46 = EEFactory.criaElementoEstatico("arvore");
      arvore46.setPosicao(10, 9);
      fase2.add(arvore46);



      return fase2;
   }

   private static ArrayList<Elemento> terceiraFase(){
      backgroundImg = "background1.png";
      ArrayList<Elemento> fase3 = new ArrayList<>();
      Tela.getTela().setNItensColetaveis(6);


      //Lolo:
      Lolo lolo = new Lolo("lolo.png");
      lolo.setPosicao(10, 5);
      fase3.add(lolo);


      //BAU:
      Bau b1 = new Bau();
      b1.setPosicao(4, 1);
      fase3.add(b1); 

      //Tatu Cinza:

      TatuCinza t1 = new TatuCinza();
      t1.setPosicao(0, 0);
      fase3.add(t1);


      //CORACAO:
      Coracao coracao1 = new Coracao();
      coracao1.setPosicao(0, 7);
      fase3.add(coracao1); 

      Coracao coracao2 = new Coracao();
      coracao2.setPosicao(2, 3);
      fase3.add(coracao2); 

      Coracao coracao3 = new Coracao();
      coracao3.setPosicao(4, 0);
      fase3.add(coracao3); 
      
      Coracao coracao6 = new Coracao();
      coracao6.setPosicao(4, 2);
      fase3.add(coracao6); 

      Coracao coracao4 = new Coracao();
      coracao4.setPosicao(7, 1);
      fase3.add(coracao4); 

      Coracao coracao5 = new Coracao();
      coracao5.setPosicao(8, 9);
      fase3.add(coracao5); 

      // ARVORES:
      ElementoEstatico arvore1 = EEFactory.criaElementoEstatico("arvore");
      arvore1.setPosicao(0, 3);
      fase3.add(arvore1);

      ElementoEstatico arvore2 = EEFactory.criaElementoEstatico("arvore");
      arvore2.setPosicao(0, 4);
      fase3.add(arvore2);

      ElementoEstatico arvore3 = EEFactory.criaElementoEstatico("arvore");
      arvore3.setPosicao(0, 5);
      fase3.add(arvore3);

      ElementoEstatico arvore4 = EEFactory.criaElementoEstatico("arvore");
      arvore4.setPosicao(0, 8);
      fase3.add(arvore4);

      ElementoEstatico arvore5 = EEFactory.criaElementoEstatico("arvore");
      arvore5.setPosicao(1, 8);
      fase3.add(arvore5);

      ElementoEstatico arvore6 = EEFactory.criaElementoEstatico("arvore");
      arvore6.setPosicao(1, 3);
      fase3.add(arvore6);

      ElementoEstatico arvore7 = EEFactory.criaElementoEstatico("arvore");
      arvore7.setPosicao(1, 4);
      fase3.add(arvore7);

      ElementoEstatico arvore8 = EEFactory.criaElementoEstatico("arvore");
      arvore8.setPosicao(1, 5);
      fase3.add(arvore8);

      ElementoEstatico arvore0 = EEFactory.criaElementoEstatico("arvore");
      arvore0.setPosicao(1, 7);
      fase3.add(arvore0);

      ElementoEstatico arvore9 = EEFactory.criaElementoEstatico("arvore");
      arvore9.setPosicao(3, 0);
      fase3.add(arvore9);

      ElementoEstatico arvore10 = EEFactory.criaElementoEstatico("arvore");
      arvore10.setPosicao(3, 1);
      fase3.add(arvore10);

      ElementoEstatico arvore11 = EEFactory.criaElementoEstatico("arvore");
      arvore11.setPosicao(3, 2);
      fase3.add(arvore11);

      ElementoEstatico arvore12 = EEFactory.criaElementoEstatico("arvore");
      arvore12.setPosicao(3, 3);
      fase3.add(arvore12);

      ElementoEstatico arvore13 = EEFactory.criaElementoEstatico("arvore");
      arvore13.setPosicao(3, 4);
      fase3.add(arvore13);

      ElementoEstatico arvore14 = EEFactory.criaElementoEstatico("arvore");
      arvore14.setPosicao(3, 5);
      fase3.add(arvore14);

      ElementoEstatico arvore15 = EEFactory.criaElementoEstatico("arvore");
      arvore15.setPosicao(3, 6);
      fase3.add(arvore15);

      ElementoEstatico arvore16 = EEFactory.criaElementoEstatico("arvore");
      arvore16.setPosicao(3, 8);
      fase3.add(arvore16);

      ElementoEstatico arvore17 = EEFactory.criaElementoEstatico("arvore");
      arvore17.setPosicao(3, 9);
      fase3.add(arvore17);

      ElementoEstatico arvore18 = EEFactory.criaElementoEstatico("arvore");
      arvore18.setPosicao(4, 4);
      fase3.add(arvore18);

      ElementoEstatico arvore19 = EEFactory.criaElementoEstatico("arvore");
      arvore19.setPosicao(4, 5);
      fase3.add(arvore19);

      ElementoEstatico arvore20 = EEFactory.criaElementoEstatico("arvore");
      arvore20.setPosicao(8, 6);
      fase3.add(arvore20);

      ElementoEstatico arvore21 = EEFactory.criaElementoEstatico("arvore");
      arvore21.setPosicao(7, 8);
      fase3.add(arvore21);

      ElementoEstatico arvore22 = EEFactory.criaElementoEstatico("arvore");
      arvore22.setPosicao(7, 9);
      fase3.add(arvore22);

      ElementoEstatico arvore23 = EEFactory.criaElementoEstatico("arvore");
      arvore23.setPosicao(8, 5);
      fase3.add(arvore23);

      ElementoEstatico arvore24 = EEFactory.criaElementoEstatico("arvore");
      arvore24.setPosicao(8, 8);
      fase3.add(arvore24);

      ElementoEstatico arvore25 = EEFactory.criaElementoEstatico("arvore");
      arvore25.setPosicao(9, 4);
      fase3.add(arvore25);

      ElementoEstatico arvore26 = EEFactory.criaElementoEstatico("arvore");
      arvore26.setPosicao(9, 8);
      fase3.add(arvore26);

      ElementoEstatico arvore27 = EEFactory.criaElementoEstatico("arvore");
      arvore27.setPosicao(10, 8);
      fase3.add(arvore27);

      //AGUA:

      Agua a1 = new Agua();
      a1.setPosicao(5, 3);
      fase3.add(a1);

      Agua a2 = new Agua();
      a2.setPosicao(6, 3);
      fase3.add(a2);

      Agua a4 = new Agua();
      a4.setPosicao(7, 3);
      fase3.add(a4);

      Agua a5 = new Agua();
      a5.setPosicao(8, 3);
      fase3.add(a5);

      Agua a6 = new Agua();
      a6.setPosicao(9, 3);
      fase3.add(a6);

      Agua a7 = new Agua();
      a7.setPosicao(4, 3);
      fase3.add(a7);

      Agua a8 = new Agua();
      a8.setPosicao(6, 4);
      fase3.add(a8);

      Agua a9 = new Agua();
      a9.setPosicao(7, 4);
      fase3.add(a9);

      Agua a10 = new Agua();
      a10.setPosicao(8, 4);
      fase3.add(a10);

      Agua a11 = new Agua();
      a11.setPosicao(7, 5);
      fase3.add(a11);

      //BLOCO VERDE:

      BlocoVerde bl1 = new BlocoVerde();
      bl1.setPosicao(6, 0);
      fase3.add(bl1);

      BlocoVerde bl2 = new BlocoVerde();
      bl2.setPosicao(6, 1);
      fase3.add(bl2);

      BlocoVerde bl3 = new BlocoVerde();
      bl3.setPosicao(6, 2);
      fase3.add(bl3);

      BlocoVerde bl4 = new BlocoVerde();
      bl4.setPosicao(9, 0);
      fase3.add(bl4);

      BlocoVerde bl5 = new BlocoVerde();
      bl5.setPosicao(9, 1);
      fase3.add(bl5);

      BlocoVerde bl6 = new BlocoVerde();
      bl6.setPosicao(9, 2);
      fase3.add(bl6);

      BlocoVerde bl7 = new BlocoVerde();
      bl7.setPosicao(6, 9);
      fase3.add(bl7);

      return fase3;
   }




}
