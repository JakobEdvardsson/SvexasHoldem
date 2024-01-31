import org.junit.jupiter.api.*;
import org.pokergame.controller.SPController;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSPController {

    @Test
    public void testSPController() {
        SPController SPController = new SPController();
        assertEquals(0, SPController.getCurrentMaxBet()); //TODO: Ändra expected value.
        assertEquals(0, SPController.getSmallBlind()); //TODO: Ändra expected value.
    }

    @Test
    public void testUserName() {

    }
}
