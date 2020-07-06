package com.example.hanpa.Dictionary;

public class RecyclerViewModel {

    private String Word;
    private String Meaning;

    public RecyclerViewModel(String word, String meaning){
        Word = word;
        Meaning = meaning;
    }

    public String getWord() {
        return Word;
    }

    public String getMeaning() {
        return Meaning;
    }

}