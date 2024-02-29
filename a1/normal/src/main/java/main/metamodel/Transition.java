package main.metamodel;

import java.util.List;

public class Transition {
	private final String event;
	private final State target;
	private final Operation operation;
	private final Condition condition;

	public Transition(String event, State target, Operation operation, Condition condition) {
		this.event = event;
		this.target = target;
		this.operation = operation;
		this.condition = condition;
	}

	public Object getEvent() {
		return event;
	}

	public State getTarget() {
		return target;
	}

	public boolean hasSetOperation() {
		return operation != null && operation.type() == Operation.OperationType.SET;
	}

	public boolean hasIncrementOperation() {
		return operation != null && operation.type() == Operation.OperationType.INCREMENT;
	}

	public boolean hasDecrementOperation() {
		return operation != null && operation.type() == Operation.OperationType.DECREMENT;
	}

	public Object getOperationVariableName() {
		return operation != null ? operation.variable() : null;
	}

	public Object getOperationRelatedValue() {
		return operation != null ? operation.value() : null;
	}

	public boolean isConditional() {
		return condition != null;
	}

	public Object getConditionVariableName() {
		return condition != null ? condition.variable() : null;
	}

	public Integer getConditionComparedValue() {
		var value = condition != null ? condition.value() : null;
		if (value instanceof Integer integer) {
			return integer;
		}
		return null;
	}

	public boolean isConditionEqual() {
		return condition != null && condition.type() == Condition.ConditionType.EQUALS;
	}

	public boolean isConditionGreaterThan() {
		return condition != null && condition.type() == Condition.ConditionType.GREATER_THAN;
	}

	public boolean isConditionLessThan() {
		return condition != null && condition.type() == Condition.ConditionType.LESS_THAN;
	}

	public boolean hasOperation() {
		return operation != null;
	}

}
