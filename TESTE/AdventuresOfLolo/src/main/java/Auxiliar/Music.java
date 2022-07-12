package Auxiliar;

import java.io.File;
import java.io.IOException;
  
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

//Classe para colocar música de fundo:
public class Music 
{
   private final Clip clip;
   private final AudioInputStream audioInputStream;

   // ]construtor
   public Music(String filePath)
      throws UnsupportedAudioFileException,
      IOException, LineUnavailableException, InterruptedException 
   {
      audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
      clip = AudioSystem.getClip();
      clip.open(audioInputStream);
      FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
      gainControl.setValue(Consts.VOLUME_ABAIXADO);
      clip.loop(Clip.LOOP_CONTINUOUSLY);
   }
   
   public void play() 
   {
      FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
      gainControl.setValue(Consts.VOLUME_ABAIXADO);
      clip.loop(Clip.LOOP_CONTINUOUSLY);
   }
   
   public void pause()
   {
      clip.stop();
   }
}