package Auxiliar;

import Modelo.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class CriarObjetoSerializavel {
    public static void main(String[] args){
        File elemento = new File("Link");
        if (elemento.exists()) {
            try {
                elemento.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        try {
            FileOutputStream fileStream = new FileOutputStream(elemento);
            ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
            Link link = new Link();
            
            objectStream.writeObject(link);

            objectStream.close();
            fileStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
