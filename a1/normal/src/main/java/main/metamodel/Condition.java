package main.metamodel;

public record Condition(String variable, ConditionType type, Object value) {
    public enum ConditionType {
        GREATER_THAN,
        LESS_THAN,
        EQUALS
    }
}