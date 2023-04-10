package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a card, with a question, answer, ease, and time remaining
public class Card implements Writable {
    private String question;
    private String answer;
    private int ease;
    private int timeRemaining;
    private final int startingEase = 100;
    protected final double passedMultiplier = 1.1;
    protected final double failedMultiplier = 0.9;
    EventLog theLog = EventLog.getInstance();

    // EFFECTS: constructs a card with a given question and answer
    public Card(String question, String answer) {
        this.answer = answer;
        this.question = question;
        this.ease = startingEase;
        this.timeRemaining = startingEase;
    }

    // EFFECTS: constructs a card with a given parameters
    public Card(String question, String answer, int ease, int timeRemaining) {
        this.answer = answer;
        this.question = question;
        this.ease = ease;
        this.timeRemaining = timeRemaining;
    }



    /*
    * REQUIRES: change >= timeRemaining
    * MODIFIES: this
    * EFFECTS: decreases the time remaining by change
     */
    public void subtractTimeRemaining(int change) {
        this.timeRemaining -= change;
    }

    /*
    MODIFIES: this
    EFFECTS: updates the ease and the time remaining using the multiplier
     */

    public void updateCard(Boolean isPassed) {
        if (isPassed) {
            this.ease *= passedMultiplier;
            theLog.logEvent(new Event("Card with Question: " + getQuestion() + " Answer: " + getAnswer() + " passed!"));
        } else {
            this.ease *= failedMultiplier;
            theLog.logEvent(new Event("Card with Question: " + getQuestion() + " Answer: " + getAnswer() + " failed!"));

        }
        this.timeRemaining = ease;
    }


    // MODIFIES: this
    // EFFECTS: Changes the question to newQuestion
    public void changeQuestion(String newQuestion) {
        this.question = newQuestion;
    }

    // MODIFIES: this
    // EFFECTS: Changes the answer to newAnswer
    public void changeAnswer(String newAnswer) {
        this.answer = newAnswer;
    }

    public int getEase() {
        return ease;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }

    public int getStartingEase() {
        return this.startingEase;
    }

    @Override
    // EFFECTS: returns a JSON representation of the card
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("question", question);
        json.put("answer", answer);
        json.put("ease", ease);
        json.put("timeRemaining", timeRemaining);
        return json;
    }

}


