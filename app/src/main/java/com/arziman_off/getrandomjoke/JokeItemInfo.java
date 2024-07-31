package com.arziman_off.getrandomjoke;

import androidx.annotation.NonNull;

public class JokeItemInfo {
    private String type;
    private String setup;
    private String punchline;
    private Integer id;

    public JokeItemInfo(String type, String setup, String punchline, Integer id) {
        this.type = type;
        this.setup = setup;
        this.punchline = punchline;
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public String getSetup() {
        return setup;
    }

    public String getPunchline() {
        return punchline;
    }

    public Integer getId() {
        return id;
    }

    @NonNull
    @Override
    public String toString() {
        return "JokeItemInfo{" +
                "type='" + type + '\'' +
                ", setup='" + setup + '\'' +
                ", punchline='" + punchline + '\'' +
                ", id=" + id +
                '}';
    }
}
