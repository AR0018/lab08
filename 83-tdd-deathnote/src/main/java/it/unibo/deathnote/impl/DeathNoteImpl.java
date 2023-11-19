package it.unibo.deathnote.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImpl implements DeathNote {

    private final Map<String, Object> humans;
    private String lastHuman;

    public DeathNoteImpl() {
        this.humans = new HashMap<>();
    }

    @Override
    public String getRule(int ruleNumber) {
        if(ruleNumber < 1 || ruleNumber > DeathNote.RULES.size()) {
            throw new IllegalArgumentException("There is no rule whose number is the one inserted");
        }
        return DeathNote.RULES.get(ruleNumber - 1);
    }

    @Override
    public void writeName(String name) {
        if(name == null) {
            throw new NullPointerException();
        }
        humans.put(name, new Object() {
            private String deathCause = null;
            private String deathDetails = null;
            private final long writingTime = System.nanoTime();

            public String getDeathCause() {
                return this.deathCause;
            }

            public String getDeathDetails() {
                return this.deathDetails;
            }

            public long getWritingTime() {
                return this.writingTime;
            }

            public void setDeathCause(String deathCause) {
                this.deathCause = deathCause;
            }

            public void setDeathDetails(String deathDetails) {
                this.deathDetails = deathDetails;
            }
        });
        this.lastHuman = name;
    }

    @Override
    public boolean writeDeathCause(String cause) {
        if(humans.isEmpty()) {
            throw new IllegalStateException("There are no names written in the Death Note");
        }
        if(humans.get(lastHuman).)
    }

    @Override
    public boolean writeDetails(String details) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'writeDetails'");
    }

    @Override
    public String getDeathCause(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDeathCause'");
    }

    @Override
    public String getDeathDetails(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDeathDetails'");
    }

    @Override
    public boolean isNameWritten(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isNameWritten'");
    }

    private long calculateInterval(long startingTime) {
        long nanoInterval = System.nanoTime() - startingTime;
        return TimeUnit.NANOSECONDS.toMillis(nanoInterval);
    }
}
