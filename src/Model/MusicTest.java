package Model;

import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;

import java.util.ConcurrentModificationException;

import static org.junit.jupiter.api.Assertions.*;

public class MusicTest {
    @Test
    public void checkMainMenuMusic(){
        Game.getInstance(0);
        Game.setState(GameState.MainMenu);
        assertFalse(Music.isShop());
        assertTrue(Music.isMenu());
    }
}
