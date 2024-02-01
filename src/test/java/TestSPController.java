import org.junit.jupiter.api.*;
import org.pokergame.controller.SPController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestSPController {

    private static String lower_consonants = "abcdefghijklmnopqrstuvwxzåäö";
    private static String upper_consonants = "ABCDEFGHIJKLMNOPQRSTUVWXZÅÄÖ";
        SPController SPController = new SPController();

    @Test
    public void testBetSize() {
        assertEquals(0, SPController.getCurrentMaxBet()); //TODO: Ändra expected value.
        assertEquals(0, SPController.getSmallBlind()); //TODO: Ändra expected value.
    }

    @Test
    public void testUserName() {
        SPController.setName("Username");
        assertEquals("Username", SPController.getName());
    }

    @Test
    public void testUserNameEmpty() {
        SPController.setName("");
        assertEquals("", SPController.getName());
    }

    @Test
    public void testUserNameNull() {
        SPController.setName(null);
        assertEquals("", SPController.getName());
    }

    @Test
    public void testUserNameTooLong() {
        String tooLongUsername = "a".repeat(21);
        boolean success = false;

        try {
            SPController.setName(tooLongUsername);
            success = true;
        } catch (IllegalArgumentException e) {
            // Expected exception
        }

        assertFalse(success);
    }
}
