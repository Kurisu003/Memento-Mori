package Model;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

public class Music implements LineListener, Runnable {
    private final String audioFilePath;
    private final ID id;
    private static float musicVolume = -40.0f;
    private static float soundVolume = -40.0f;
    private static int simpleMusicVolume = 2;
    private static int simpleGameVolume = 2;

    public Music(String audioFilePath, ID id){
        this.audioFilePath = audioFilePath;
        this.id = id;
    }

    boolean playCompleted = false;

    public static int getSimpleMusicVolume() {
        return simpleMusicVolume;
    }

    public static void setSimpleMusicVolume(int simpleMusicVolume) {
        simpleMusicVolume = Math.max(simpleMusicVolume, 0);
        simpleMusicVolume = Math.min(simpleMusicVolume, 5);
        Music.simpleMusicVolume = simpleMusicVolume;
    }

    public static int getSimpleGameVolume() {
        return simpleGameVolume;
    }

    public static void setSimpleGameVolume(int simpleGameVolume) {
        Music.simpleGameVolume = simpleGameVolume;
    }

    void playMusic() {
        File audioFile = new File(this.audioFilePath);

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            AudioFormat format = audioStream.getFormat();

            DataLine.Info info = new DataLine.Info(Clip.class, format);

            Clip audioClip = (Clip) AudioSystem.getLine(info);

            audioClip.addLineListener(this);

            audioClip.open(audioStream);
            FloatControl gainControl = (FloatControl)audioClip.getControl(FloatControl.Type.MASTER_GAIN);


            audioClip.start();

            while (!playCompleted) {
                if(this.id == ID.BG_music)
                    gainControl.setValue(musicVolume);
                else if(this.id == ID.ShootingSound)
                    gainControl.setValue(soundVolume);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            audioClip.close();

        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error playing the audio file.");
            ex.printStackTrace();
        }

    }

    @Override
    public void update(LineEvent event) {
        LineEvent.Type type = event.getType();

        if (this.id == ID.BG_music && (type == LineEvent.Type.STOP)) {
            playMusic();
        }
        else if(this.id == ID.ShootingSound && type == LineEvent.Type.STOP)
            this.playCompleted = true;

    }

    @Override
    public void run() {
        this.playMusic();
    }

    public static void setMusicVolume(float musicVolumePar) {
        if(musicVolumePar < -54.5)
            musicVolumePar = (float) -54.5;
        else if(musicVolumePar > -20)
            musicVolumePar = -20;
        musicVolume = musicVolumePar;
    }

    public static float getMusicVolume() {
        return musicVolume;
    }

    public static void setSoundVolume(float soundVolumePar) {
        if(soundVolumePar < -54.5)
            soundVolumePar = (float) -54.5;
        else if(soundVolumePar > -20)
            soundVolumePar = -20;
        soundVolume = soundVolumePar;
    }

    public static float getSoundVolume() {
        return soundVolume;
    }
}