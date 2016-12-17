package com.pascalhow.models;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by lisa on 12/12/2016.
 */
public class Criteria {

    private String element;
    private ArrayList<String> performances;

    public Criteria(String element, ArrayList<String> performances) {
        this.element = element;
        this.performances = performances;
    }

    public String getElement() {
        return this.element.toString();
    }
}
