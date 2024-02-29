package main.metamodel;

public record Operation(String variable, OperationType type, Object value) {
    public enum OperationType {
        SET,
        INCREMENT,
        DECREMENT
    }
}