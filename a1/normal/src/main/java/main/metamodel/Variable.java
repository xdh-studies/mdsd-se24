package main.metamodel;

public class Variable {
    private final String name;
    private Object value;

    public Variable(String name) {
        this.name = name;
    }
    public Object getValue() {
        return value;
    }
    public void setValue(Object value) {
        this.value = value;
    }
}

