package snappfood.ordersdelay.core.validations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Validator<T extends Exception> {

    private final List<Rule> rules;

    public Validator() {
        this.rules = new ArrayList<>();
    }

    public Validator<T> addRule(Rule rule) {
        rules.add(rule);
        return this;
    }

    public Validator<T> addRules(Rule... rules) {
        this.rules.addAll(Arrays.asList(rules));
        return this;
    }

    public void validate() throws T {
        for (Rule rule : rules) {
            if (!rule.apply()) {
               this.throwException(rule.getMessage());
            }
        }
    }

    protected abstract void throwException(String message) throws T;
}
