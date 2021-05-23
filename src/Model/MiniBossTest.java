package Model;

import Controller.Handler1;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MiniBossTest {

    @Test
    public void checkSpeed(){
        Miniboss miniboss = new Miniboss(0, 0, ID.Miniboss, 10000);

        while(miniboss.getHp() > 0){
            miniboss.doAction(10);
            if(miniboss.getHp() > 9000)
                assertEquals( 1.5, miniboss.getIncreasingSpeed());
            else if(miniboss.getHp() > 8000)
                assertEquals(1.8, miniboss.getIncreasingSpeed());
            else if(miniboss.getHp() > 7000)
                assertEquals(2.1, miniboss.getIncreasingSpeed());
            else if(miniboss.getHp() > 6000)
                assertEquals(2.4, miniboss.getIncreasingSpeed());
            else if(miniboss.getHp() > 5000)
                assertEquals(2.7, miniboss.getIncreasingSpeed());
            else if(miniboss.getHp() > 4000)
                assertEquals(3.0, miniboss.getIncreasingSpeed());
            else if(miniboss.getHp() > 3000)
                assertEquals(3.3, miniboss.getIncreasingSpeed());
            else if(miniboss.getHp() > 2000)
                assertEquals(3.6, miniboss.getIncreasingSpeed());
            else if(miniboss.getHp() > 1000)
                assertEquals(3.9, miniboss.getIncreasingSpeed());
            else if(miniboss.getHp() > 0)
                assertEquals(4.2, miniboss.getIncreasingSpeed());
            else if(miniboss.getHp() == 0){
                for (GameObject obj: Handler1.getInstance().objects
                     ) {
                    assertNotEquals(miniboss, obj);
                }
            }
        }
    }
}
