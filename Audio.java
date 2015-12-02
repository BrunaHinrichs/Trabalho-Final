import java.io.File;
import java.applet.AudioClip;
import java.applet.Applet;
import java.net.MalformedURLException;

public class Audio {
	public static void playFile(String file) {
		try{
			AudioClip clip = Applet.newAudioClip(new File(file).toURI().toURL());
			clip.play();
		}catch(MalformedURLException e){
			System.err.println("Bad file name.\n"+e.toString());
		}
	}

}
