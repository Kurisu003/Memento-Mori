package Model;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

/**
 * This class is here to play every music and sound effect of the game.
 */
public class Music implements LineListener, Runnable {
    private final String audioFilePath;
    private final ID id;
    private static float musicVolume = -27.489f;
    private static float soundVolume = -27.489f;
    private static int simpleMusicVolume = 4;
    private static int simpleGameVolume = 4;

    private boolean playCompleted = false;

    /**
     * This is the public constructor of Music which must be called when a Music object is created
     * @param audioFilePath String of the path of the audio file which should be played
     * @param id ID what type of music it is (shooting sound, background music)
     */
    public Music(String audioFilePath, ID id){
        this.audioFilePath = audioFilePath;
        this.id = id;
    }

    /**
     * Sets the music volume.
     * @param simpleMusicVolume integer to indicate the music volume level
     */
    public static void setSimpleMusicVolume(int simpleMusicVolume) {
        simpleMusicVolume = Math.max(simpleMusicVolume, 0);
        simpleMusicVolume = Math.min(simpleMusicVolume, 5);
        Music.simpleMusicVolume = simpleMusicVolume;
    }

    /**
     * Returns the value of simpleMusicVolume
     * @return integer value of simpleMusicVolume
     */
    public static int getSimpleMusicVolume() {
        return simpleMusicVolume;
    }

    /**
     * Sets the game volume (sound and shooting effects).
     * @param simpleGameVolume integer to indicate the sound volume level
     */
    public static void setSimpleGameVolume(int simpleGameVolume) {
        simpleGameVolume = Math.max(simpleGameVolume, 0);
        simpleGameVolume = Math.min(simpleGameVolume, 5);
        Music.simpleGameVolume = simpleGameVolume;
    }

    /**
     * Gets the game volume (sound and shooting effects).
     * @return integer value of simpleGameVolume
     */
    public static int getSimpleGameVolume() {
        return simpleGameVolume;
    }

    /**
     * Starts playing the music by loading the file and checking if everything is ok.
     */
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
                if(this.id == ID.ShootingSound)
                    gainControl.setValue(soundVolume);
                else if(this.id == ID.BG_music){
                    gainControl.setValue(musicVolume);
                    audioClip.loop(Clip.LOOP_CONTINUOUSLY);
                }
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

    /**
     * Checks if the sound is finished playing so it does not loop unconditionally
     * @param event the state of the playing sound
     */
    @Override
    public void update(LineEvent event) {
        LineEvent.Type type = event.getType();
        if(this.id == ID.ShootingSound && type == LineEvent.Type.STOP)
            this.playCompleted = true;
    }

    /**
     * Calls the method {@link #playMusic()} when the Thread starts.
     */
    @Override
    public void run() {
        this.playMusic();
    }

    /**
     * Sets the music to the desired volume
     * @param musicVolumePar desired volume
     */
    public static void setMusicVolume(float musicVolumePar) {
        musicVolumePar = musicVolumePar < -64 ? (float) -64.50 : musicVolumePar;
        musicVolumePar = musicVolumePar > -20 ? (float) -20 : musicVolumePar;
        musicVolume = musicVolumePar;
    }

    /**
     * Getter of the current music volume.
     * @return the music volume
     */
    public static float getMusicVolume() {
        return musicVolume;
    }

    /**
     * Sets the sound to the desired volume.
     * @param soundVolumePar desired volume
     */
    public static void setSoundVolume(float soundVolumePar) {
        soundVolumePar = soundVolumePar < -64 ? (float) -64.50 : soundVolumePar;
        soundVolumePar = soundVolumePar > -20 ? (float) -20 : soundVolumePar;
        soundVolume = soundVolumePar;
    }

    /**
     * Getter of the current sound volume.
     * @return the sound volume
     */
    public static float getSoundVolume() {
        return soundVolume;
    }
}