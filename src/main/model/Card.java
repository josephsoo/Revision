package model;

// A card, with a question, answer, ease, and time remaining
public class Card {
    private String question;
    private String answer;
    private int ease;
    private int timeRemaining;
    private int id;
    private static int nextCardID = 0;
    protected final int startingEase = 100;
    protected final double passedMultiplier = 1.1;
    protected final double failedMultiplier = 0.9;


    public Card(String question, String answer) {
        this.answer = answer;
        this.question = question;
        this.ease = startingEase;
        this.timeRemaining = startingEase;
        this.id = nextCardID++;

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
        } else {
            this.ease *= failedMultiplier;
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

    public int getId() {
        return this.id;
    }


    public int getStartingEase() {
        return this.startingEase;
    }
}

