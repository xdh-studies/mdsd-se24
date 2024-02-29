package main;

import main.metamodel.*;

import java.util.*;
import java.util.stream.Collectors;

public class StateMachine {

	String initialState = null;
	HashMap<String, LinkedList<IntermediaryTransition>> states = new HashMap<>();
	HashMap<String, Variable> variables = new HashMap<>();
	String lastSelectedState = null;
	IntermediaryTransition currentTransition = null;


	public Machine build() {
		finalizeTransition();

		final HashMap<String, State>  stateMap = new HashMap<>(states.size());
		final HashMap<State, LinkedList<Transition>> transitionMap = new HashMap<>(states.size());

		for (String stateKey : states.keySet()) {
			LinkedList<Transition> stateTransitions = new LinkedList<>();
			State state = new State(stateKey, stateTransitions);
			stateMap.put(stateKey, state);
			transitionMap.put(state, stateTransitions);
		}

		for (Map.Entry<String, LinkedList<IntermediaryTransition>> entry : states.entrySet()) {
			final LinkedList<Transition> stateTransitions = transitionMap.get(stateMap.get(entry.getKey()));
			for (IntermediaryTransition intermediaryTransition : entry.getValue()) {
				Transition transition = new Transition(
						intermediaryTransition.when(),
						stateMap.get(intermediaryTransition.to()),
						intermediaryTransition.operation(),
						intermediaryTransition.condition()
				);
				stateTransitions.add(transition);
			}
		}

		Machine machine = new Machine(initialState, stateMap, variables);
		return machine;
	}

	public StateMachine state(String string) {
		finalizeTransition();
		this.states.put(string, new LinkedList<>());
		lastSelectedState = string;
		return this;
	}

	public StateMachine initial() {
		verifyLastSelected();
		initialState = lastSelectedState;
		return this;
	}

	public StateMachine when(String string) {
		verifyLastSelected();
		finalizeTransition();
		currentTransition = new IntermediaryTransition().withWhen(string);
		return this;
	}

	public StateMachine to(String string) {
		throwIfTransitionNotInProgress();
		currentTransition = currentTransition.withTo(string);
		return this;
	}

	public StateMachine integer(String string) {
		this.variables.putIfAbsent(string, new Variable(string));
		return this;
	}

	public StateMachine set(String string, int i) {
		integer(string);
		return addOperation(string, Operation.OperationType.SET, i);
	}

	public StateMachine increment(String string) {
		integer(string);
		return addOperation(string, Operation.OperationType.INCREMENT, null);
	}

	public StateMachine decrement(String string) {
		integer(string);
		return addOperation(string, Operation.OperationType.DECREMENT, null);
	}

	public StateMachine ifEquals(String string, int i) {
		integer(string);
		return addConditional(string, Condition.ConditionType.EQUALS, i);
	}

	public StateMachine ifGreaterThan(String string, int i) {
		integer(string);
		return addConditional(string, Condition.ConditionType.GREATER_THAN, i);
	}

	public StateMachine ifLessThan(String string, int i) {
		integer(string);
		return addConditional(string, Condition.ConditionType.LESS_THAN, i);
	}

	private void verifyLastSelected() {
		if (lastSelectedState == null)
			throw new RuntimeException("Must have created a state.");
	}

	private void throwIfTransitionInProgress() {
		if(currentTransition != null) {
			throw new RuntimeException("Must finish transition.");
		}
	}
	private void throwIfTransitionNotInProgress() {
		if(currentTransition == null) {
			throw new RuntimeException("Must have started transition.");
		}
	}

	private StateMachine addOperation(String variable, Operation.OperationType operationType, Object value) {
		throwIfTransitionNotInProgress();
		currentTransition = currentTransition.withOperation(new Operation(variable, operationType, value));
		return this;
	}

	private StateMachine addConditional(String variable, Condition.ConditionType conditionalType, Object value) {
		throwIfTransitionNotInProgress();
		currentTransition = currentTransition.withConditional(new Condition(variable, conditionalType, value));
		return this;
	}
	private void finalizeTransition() {
		if (lastSelectedState != null && currentTransition != null) {
			states.get(lastSelectedState).add(currentTransition);
			currentTransition = null;
		}
	}

	private record IntermediaryTransition(
			String when,
			String to,
			Condition condition,
			Operation operation
	) {

		public IntermediaryTransition() {
			this(null, null, null, null);
		}

		IntermediaryTransition withWhen(String when) {
			return new IntermediaryTransition(when, this.to, this.condition, this.operation);
		}
		IntermediaryTransition withTo(String to) {
			return new IntermediaryTransition(this.when, to, this.condition, this.operation);
		}
		IntermediaryTransition withConditional(Condition condition) {
			return new IntermediaryTransition(this.when, this.to, condition, this.operation);
		}
		IntermediaryTransition withOperation(Operation operation) {
			return new IntermediaryTransition(this.when, this.to, condition, operation);
		}

	}
}
