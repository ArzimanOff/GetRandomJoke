package com.arziman_off.getrandomjoke;

public class RulesForGeneratingJokes {
    public static final int RANDOM_JOKE = -1;
    private JokesCnt jokesCntAction;
    private int jokesCntValue;
    private ChooseId chooseIdAction;
    private int id;
    private static RulesForGeneratingJokes instance = null;
    public static RulesForGeneratingJokes getInstance() {
        if (instance == null){
            instance = new RulesForGeneratingJokes();
        }
        return instance;
    }
    public RulesForGeneratingJokes() {
        this.jokesCntAction = JokesCnt.ONE_JOKE;
        this.jokesCntValue = 1;
        this.chooseIdAction = ChooseId.RANDOM_ID;
        this.id = RANDOM_JOKE;
    }

    public JokesCnt getJokesCntAction() {
        return jokesCntAction;
    }

    public void setJokesCntAction(JokesCnt jokesCntAction) {
        this.jokesCntAction = jokesCntAction;
        if (jokesCntAction == JokesCnt.ONE_JOKE){
            this.jokesCntValue = 1;
            if (this.getChooseIdAction() == ChooseId.RANDOM_ID){
                this.id = RANDOM_JOKE;
            }
        } else if (jokesCntAction == JokesCnt.LIST_OF_JOKES){
            this.setId(RANDOM_JOKE);
        }
    }

    public int getJokesCntValue() {
        return jokesCntValue;
    }

    public void setJokesCntValue(int jokesCntValue) {
        this.jokesCntValue = jokesCntValue;
    }

    public ChooseId getChooseIdAction() {
        return chooseIdAction;
    }

    public void setChooseIdAction(ChooseId chooseIdAction) {
        this.chooseIdAction = chooseIdAction;
        if (chooseIdAction == ChooseId.RANDOM_ID){
            this.id = RANDOM_JOKE;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "{" +
                "jokesCntAction=" + jokesCntAction +
                ", jokesCntValue=" + jokesCntValue +
                ", chooseIdAction=" + chooseIdAction +
                ", id=" + id +
                '}';
    }
}

enum JokesCnt{
    ONE_JOKE,
    LIST_OF_JOKES
}
enum ChooseId{
    RANDOM_ID,
    SET_ID
}

