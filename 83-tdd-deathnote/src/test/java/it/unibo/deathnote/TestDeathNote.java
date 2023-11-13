package it.unibo.deathnote;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import it.unibo.deathnode.impl.DeathNodeImpl;
import it.unibo.deathnote.api.DeathNote;

class TestDeathNote {
    private DeathNote deathNote;
    private static final String HUMAN = "Light Yagami";
    private static final String OTHER_HUMAN = "L";

    @BeforeEach
    public void setUp() {
        deathNote = new DeathNodeImpl();
    }

    @Test
    public void testRuleNumber() {
        try{
            deathNote.getRule(0);
            fail("Expected IllegalArgumentException for invalid rule number");
        } catch(IllegalArgumentException e) {
            assertNotNull(e.getMessage());
            assertTrue(e.getMessage().length() > 0);
        }
    }
    
    @Test
    public void testRules() {
        for(String rule : DeathNote.RULES) {
            assertNotNull(rule);
            assertTrue(rule.length() > 0);
        }
    }

    @Test
    public void testHumansInNotebook() {
        assertFalse(deathNote.isNameWritten(HUMAN));
        deathNote.writeName(HUMAN);
        assertTrue(deathNote.isNameWritten(HUMAN));
        assertFalse(deathNote.isNameWritten(OTHER_HUMAN));
        assertFalse(deathNote.isNameWritten(""));
    }

    
}