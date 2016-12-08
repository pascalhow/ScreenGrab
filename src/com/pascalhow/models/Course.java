package com.pascalhow.models;

/**
 * Created by pascal on 07/12/2016.
 */
public class Course {

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

        public Course build() {
            return new Course(this);
        }
    }

    private Course(Builder builder) {
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
        String unitsToString = "----- Course -----\n";
        unitsToString += "Code: " + this.code + "\n";
        unitsToString += "Title: " + this.title + "\n";

        return unitsToString;
    }
}
