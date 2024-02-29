package main.metamodel;

import java.util.HashMap;
import java.util.List;

public class Machine {

	private final String initialState;
	private String currentState;
	private HashMap<String, State> states;
	private HashMap<String, Variable> variables;

	public Machine(String initialState, HashMap<String, State> states, HashMap<String, Variable> variables) {
		this.initialState = initialState;
		this.states = states;
		this.variables = variables;
		this.currentState = this.initialState;
	}

	public List<State> getStates() {
		return states.values().stream().toList();
	}

	public State getInitialState() {
		return states.get(initialState);
	}

	public State getState(String string) {
		return states.get(string);
	}

	public int numberOfIntegers() {
		return this.variables.size();
	}

	public boolean hasInteger(String string) {
		return this.variables.containsKey(string);
	}
	public Variable getVariable(String variable) {
		return this.variables.get(variable);
	}
}

