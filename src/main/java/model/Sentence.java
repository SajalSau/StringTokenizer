package model;

import java.util.ArrayList;
import java.util.List;

public class Sentence {


    private List<String> word = new ArrayList<>();

    public List<String> getWord() {
        return word;
    }

    public void setWord(List<String> words) {
        this.word = words;
    }
}
