package Auxiliar;

import java.io.File;
import java.io.IOException;
  
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
  
public class SoundEffect 
{
   private final Clip clip;
   private final AudioInputStream audioInputStream;
   
   public SoundEffect(String fileName)
      throws UnsupportedAudioFileException,
      IOException, LineUnavailableException 
   {
      String currentPath = new java.io.File(".").getCanonicalPath();
      String filePath = currentPath + Consts.PATH_SOUND + fileName;
      audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
      clip = AudioSystem.getClip();
      clip.open(audioInputStream);
   }
   
   public void play() 
   {
      FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
      gainControl.setValue(Consts.VOLUME_ABAIXADO + 5);
      clip.start();
   }
   
}
