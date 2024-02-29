package main.metamodel;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class State {

	private final String name;
	private final LinkedList<Transition> transitions;

	public State(String name, LinkedList<Transition> transitions) {
		this.name = name;
		this.transitions = transitions;
	}

	public Object getName() {
		return name;
	}

	public List<Transition> getTransitions() {
		return transitions;
	}

	public Transition getTransitionByEvent(String string) {
		return transitions.stream().filter(x -> string.equals(x.getEvent())).findFirst().orElse(null);
	}
}
