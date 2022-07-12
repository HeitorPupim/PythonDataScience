package Modelo;

import java.io.Serializable;

public class Agua extends ElementoEstatico implements Serializable {
   
   public Agua(){
      super("agua.png");
      this.bMortal = true;

   }
}
