package com.example.twitterprivacy;

public class Settings {


    public Settings(Boolean location, Boolean tag, Boolean mention, Boolean words) {
        this.location = location;
        this.tag = tag;
        this.mention = mention;
        this.words = words;
    }
    Boolean location = false;
    Boolean tag = false;
    Boolean mention = false;
    Boolean words = false;

    public Boolean getLocation() {
        return location;
    }

    public void setLocation(Boolean location) {
        this.location = location;
    }

    public Boolean getTag() {
        return tag;
    }

    public void setTag(Boolean tag) {
        this.tag = tag;
    }

    public Boolean getMention() {
        return mention;
    }

    public void setMention(Boolean mention) {
        this.mention = mention;
    }

    public Boolean getWords() {
        return words;
    }

    public void setWords(Boolean words) {
        this.words = words;
    }
}
