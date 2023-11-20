package it.unibo.deathnote.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImpl implements DeathNote {

    private final Map<String, HumanDetails> humans;
    //Contains the name of the last human written in the death note
    private String lastHuman;

    public DeathNoteImpl() {
        this.humans = new HashMap<>();
    }

    @Override
    public String getRule(final int ruleNumber) {
        if (ruleNumber < 1 || ruleNumber > DeathNote.RULES.size()) {
            throw new IllegalArgumentException("There is no rule whose number is the one inserted");
        }
        return DeathNote.RULES.get(ruleNumber - 1);
    }

    @Override
    public void writeName(final String name) {
        if (name == null) {
            throw new NullPointerException();
        }
        humans.put(name, new HumanDetails());
        this.lastHuman = name;
    }

    @Override
    public boolean writeDeathCause(final String cause) {
        if(humans.isEmpty()) {
            throw new IllegalStateException("There are no names written in the Death Note");
        }
        if(cause == null) {
            throw new IllegalStateException("The cause is null");
        }
        if(calculateInterval(humans.get(lastHuman).getWritingTime()) < 40) {
            humans.get(lastHuman).setDeathCause(cause);
            humans.get(lastHuman).setDeathWritingTime();
            return true;
        }
        return false;
    }

    @Override
    public boolean writeDetails(final String details) {
        if(humans.isEmpty()) {
            throw new IllegalStateException("There are no names written in the Death Note");
        }
        if(details == null) {
            throw new IllegalStateException("The details are null");
        }
        if(calculateInterval(humans.get(lastHuman).getDeathWritingTime()) < 6040) {
            humans.get(lastHuman).setDeathDetails(details);
            return true;
        }
        return false;
    }

    @Override
    public String getDeathCause(final String name) {
        if(!isNameWritten(name)) {
            throw new IllegalArgumentException("This human's name is not written in the Death Note");
        }
        return humans.get(name).getDeathCause();
    }

    @Override
    public String getDeathDetails(final String name) {
        if(!isNameWritten(name)) {
            throw new IllegalArgumentException("This human's name is not written in the Death Note");
        }
        return humans.get(name).getDeathDetails();
    }

    @Override
    public boolean isNameWritten(final String name) {
        return humans.containsKey(name);
    }

    /**
     * Calculates the time elapsed since the starting time.
     * @param startingTime
     * @return the interval in milliseconds
    */
    private long calculateInterval(final long startingTime) {
        long nanoInterval = System.nanoTime() - startingTime;
        return TimeUnit.NANOSECONDS.toMillis(nanoInterval);
    }

    private final class HumanDetails {

        private String deathCause;
        private String deathDetails;
        private final long writingTime;
        private long deathWritingTime;

        public HumanDetails() {
            //The cause of death is set by default to "heart attack"
            this.deathCause = "heart attack";
            this.deathDetails = "";
            this.writingTime = System.nanoTime();
            this.deathWritingTime = this.writingTime;
        }

        public String getDeathCause() {
            return this.deathCause;
        }

        public String getDeathDetails() {
            return this.deathDetails;
        }

        public long getWritingTime() {
            return this.writingTime;
        }

        public long getDeathWritingTime() {
            return this.deathWritingTime;
        }

        public void setDeathCause(String deathCause) {
            this.deathCause = deathCause;
        }

        public void setDeathDetails(String deathDetails) {
            this.deathDetails = deathDetails;
        }

        public void setDeathWritingTime() {
            this.deathWritingTime = System.nanoTime();
        }
    }
}
