package Model;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

public class Music implements LineListener, Runnable {
    private final String audioFilePath;
    private final ID id;
    private float musicVolume = -20.0f;
    private float soundVolume = -20.0f;

    public Music(String audioFilePath, ID id){
        this.audioFilePath = audioFilePath;
        this.id = id;
    }

    boolean playCompleted = false;
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
            if(this.id == ID.BG_music)
                gainControl.setValue(musicVolume);
            else if(this.id == ID.ShootingSound)
                gainControl.setValue(soundVolume);


            audioClip.start();

            while (!playCompleted) {
                try {
                    Thread.sleep(1000);
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

    public void setMusicVolume(float musicVolume) {
        this.musicVolume = musicVolume;
    }

    public float getMusicVolume() {
        return musicVolume;
    }

    public void setSoundVolume(float soundVolume) {
        this.soundVolume = soundVolume;
    }

    public float getSoundVolume() {
        return soundVolume;
    }
}