package com.pascalhow.models;

import com.pascalhow.constants.Constants;

/**
 * Created by lisa on 11/12/2016.
 */
public class PerformanceCriteria {

    private final ArrayList<criteria> criterias;
    private final Array performance;

    public static class Builder {

        private Array element;
        private Array performance;

        public PerformanceCriteria.Builder setElements(Array element) {
            this.element = element;
            return this;
        }

        public PerformanceCriteria.Builder setPerformances(Array performance) {
            this.performance = performance;
            return this;
        }

        public PerformanceCriteria build() {
            return new PerformanceCriteria(this);
        }
    }

    private PerformanceCriteria(PerformanceCriteria.Builder builder) {
        this.element = builder.element;
        this.performance = builder.performance;
    }

    public Array getElements() {
        return this.element;
    }

    public Array getPerformances() {
        return this.performance;
    }

    public class criteria {
        private String element;

    }

    @Override
    public String toString() {
        String unitsToString = "----- Performance criteria -----\n";

        return unitsToString;
    }
}
