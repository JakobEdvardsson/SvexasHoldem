import org.junit.jupiter.api.*;
import org.pokergame.aiClass.Ai;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAi {
    Ai ai = new Ai(1000, "Sven");

    @Test
    public void testAiBlind() {
        assertEquals(false, ai.getIsBigBlind());
        assertEquals(true, ai.getIsBigBlind());
        assertEquals(false, ai.getIsSmallBlind());
        assertEquals(true, ai.getIsSmallBlind());
    }

    @Test
    public void testAiDecision() {
        ai.setDecision("Decision");
        assertEquals("Decision", ai.getDecision());
        assertEquals("", ai.getDecision());
        assertEquals(null, ai.getDecision());
    }

    @Test
    public void testAiPaid() {
        ai.setPaidThisTurn(10);
        assertEquals(10, ai.getPaidThisTurn());
        assertEquals(5, ai.getPaidThisTurn());
        assertEquals((Integer) null, ai.getPaidThisTurn());
    }
}
