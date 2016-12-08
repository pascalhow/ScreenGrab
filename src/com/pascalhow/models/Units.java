package com.pascalhow.models;

/**
 * Created by pascal on 07/12/2016.
 */
public class Units {

    private final String code;
    private final String title;

    public static class Builder {

        private String code = "";
        private String title = "";

        public Builder setCode(String code) {
            this.code = code;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Units build() {
            return new Units(this);
        }
    }

    private Units(Builder builder) {
        this.code = builder.code;
        this.title = builder.title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    @Override
    public String toString() {
        String unitsToString = "----- Unit -----\n";
        unitsToString += "Code: " + this.code + "\n";
        unitsToString += "Title: " + this.title + "\n";

        return unitsToString;
    }
}
