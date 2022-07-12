package Modelo;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

public class TatuCinza extends Inimigo implements Serializable{

   public TatuCinza(){
      super("pig.png");
   }

   @Override
   public boolean movimenta() {
       int randomNum = ThreadLocalRandom.current().nextInt(1, 5);
       switch (randomNum) {
           case 1:
               return moveUp();
           case 2:
               return moveDown();
           case 3:
               return moveRight();
           case 4:
               return moveLeft();
           default:
               return false;
       }
       
  }
   
}
