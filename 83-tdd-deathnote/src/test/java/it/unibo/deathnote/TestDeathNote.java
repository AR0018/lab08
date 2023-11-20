package it.unibo.deathnote;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;

class TestDeathNote {
    private DeathNote deathNote;
    private static final String HUMAN = "Light Yagami";
    private static final String OTHER_HUMAN = "L";
    private static final String DEATH_CAUSE = "Drowning";
    private static final String OTHER_DEATH_CAUSE = "karting accident";
    private static final String DETAILS = "ran for too long";
    private static final String OTHER_DETAILS = "fell down the stairs";

    @BeforeEach
    public void setUp() {
        deathNote = new DeathNoteImpl();
    }

    @Test
    public void testRuleNumber() {
        try{
            deathNote.getRule(0);
            fail("Expected IllegalArgumentException for invalid rule number");
        } catch(final IllegalArgumentException e) {
            assertNotNull(e.getMessage());
            assertTrue(e.getMessage().length() > 0);
        }
    }
    
    @Test
    public void testRules() {
        for(final String rule : DeathNote.RULES) {
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

    @Test
    public void testCauseOfDeath() throws InterruptedException {
        try {
            deathNote.writeDeathCause(DEATH_CAUSE);
            fail("Expected IllegalStateException due to wrong order of operations");
        } catch(final IllegalStateException e) {
            assertNotNull(e.getMessage());
            assertTrue(e.getMessage().length() > 0);
        }
        deathNote.writeName(HUMAN);
        assertEquals("heart attack", deathNote.getDeathCause(HUMAN));
        deathNote.writeName(OTHER_HUMAN);
        assertTrue(deathNote.writeDeathCause(OTHER_DEATH_CAUSE));
        assertEquals(OTHER_DEATH_CAUSE, deathNote.getDeathCause(OTHER_HUMAN));
        Thread.sleep(100);
        deathNote.writeDeathCause(DEATH_CAUSE);
        assertEquals(OTHER_DEATH_CAUSE, deathNote.getDeathCause(OTHER_HUMAN));
    }
    
    @Test
    public void testDeathDetails() throws InterruptedException {
        try {
            deathNote.writeDetails("Whatever");
            fail("Expected IllegalStateException due to wrong order of operations");
        } catch(final IllegalStateException e) {
            assertNotNull(e.getMessage());
            assertTrue(e.getMessage().length() > 0);
        }
        deathNote.writeName(HUMAN);
        assertTrue(deathNote.getDeathDetails(HUMAN) == null || deathNote.getDeathDetails(HUMAN).length() == 0);
        assertTrue(deathNote.writeDetails(DETAILS));
        assertEquals(DETAILS, deathNote.getDeathDetails(HUMAN));
        deathNote.writeName(OTHER_HUMAN);
        Thread.sleep(6100);
        deathNote.writeDetails(OTHER_DETAILS);
        assertNotEquals(OTHER_DETAILS, deathNote.getDeathDetails(OTHER_HUMAN));
    }
}