package Modelo;

public class EEFactory {
   public static ElementoEstatico criaElementoEstatico(String nomeElemento){
     
      if (nomeElemento.equalsIgnoreCase("arvore")){
         return new Arvore();
      }

      else return null;
   }
}
