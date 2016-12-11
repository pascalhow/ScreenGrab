package com.pascalhow.models;

import com.pascalhow.constants.Constants;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by lisa on 11/12/2016.
 */
public class PerformanceCriteria {

    private final ArrayList<criteria> criterias;

    public static class Builder {

        private ArrayList<criteria> criterias;

        public PerformanceCriteria.Builder setCriterias(ArrayList<criteria> criteria) {
            this.criterias = criteria;
            return this;
        }

        public PerformanceCriteria build() {
            return new PerformanceCriteria(this);
        }
    }

    private PerformanceCriteria(PerformanceCriteria.Builder builder) {
        this.criterias = builder.criterias;
    }

    public class criteria {
        private String element;
        private Array performances;
    }

    @Override
    public String toString() {
        String unitsToString = "----- Performance criteria -----\n";

        return unitsToString;
    }
}
