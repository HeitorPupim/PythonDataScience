package Controler;

import Auxiliar.Fases;
import Modelo.Elemento;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Saver {
   
   public static void criaAutoSave(){
      int intervalo = 30; //em segundos

      //Faz o autosave:
      Timer timer = new Timer();
      timer.scheduleAtFixedRate(new TimerTask() {
         public void run(){
            Tela.getTela().salvaJogo();
         }
      },10000, intervalo*1000);
   }

   public static void salvaJogo(){
      File save = new File("save.zip");
      if (save.exists()){
         try{
            save.createNewFile();
         }catch (IOException ex){
            ex.printStackTrace();
         }
      }
      try{
         FileOutputStream fos = new FileOutputStream(save);
         GZIPOutputStream gzos = new GZIPOutputStream(fos);
         ObjectOutputStream oos = new ObjectOutputStream(gzos);

         //Escreve o arraylist de objetos do estado do jogo:
         oos.writeObject(Fases.fase);
         oos.writeObject(Fases.backgroundImg);
         oos.writeObject(Fases.transicoes);
         oos.writeObject(Tela.getTela().getVidas());

         for (Elemento el: Tela.getTela().getArrayElementos()){
            oos.writeObject(el);
         }

         oos.close();
         gzos.close();
         fos.close();
      } catch (Exception e){
         e.printStackTrace();
      }
   }

   //Carrega o game a partir de um zip:
   public static void loadGame(){
      File save = new File ("save.zip");
      if (!save.exists()){
         System.out.println("Falha ao carregar arquivo de save.");
      }

      try {
         FileInputStream fis = new FileInputStream(save);  
         GZIPInputStream gzis = new GZIPInputStream(fis);
         ObjectInputStream ois = new ObjectInputStream(gzis);
         
         /* Efetua a leitura no ArrayList de objetos e dos estados do jogo*/
         Fases.fase = (int) ois.readObject();
         Fases.backgroundImg = (String) ois.readObject();
         Fases.transicoes = (ArrayList<String>) ois.readObject();
         Tela.getTela().setVidas((int) ois.readObject());
      
         try{
            while(true){
               Tela.getTela().addElemento((Elemento) ois.readObject());
            }
        } catch(EOFException eof){}
      
      }catch (Exception e) {
         e.printStackTrace();
      }
   }
}
