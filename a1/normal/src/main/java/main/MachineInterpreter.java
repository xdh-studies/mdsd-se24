package main;

import main.metamodel.Machine;
import main.metamodel.State;
import main.metamodel.Transition;
import main.metamodel.Variable;

public class MachineInterpreter {

    private Machine machine;
    private State currentState;
    public void run(Machine m) {
        machine = m;
        currentState = m.getInitialState();
    }

    public State getCurrentState() {
        return currentState;
    }

    public void processEvent(String string) {
        for (Transition transition : currentState.getTransitions()) {
            if (string.equals(transition.getEvent())) {
                if(isTransitionValid(transition)) {
                    processOperation(transition);
                    currentState = transition.getTarget();
                    break;
                }
            }
        }
    }
    private void processOperation(Transition transition) {
        if (transition.hasOperation()) {
            Variable variable = machine.getVariable((String) transition.getOperationVariableName());
            if (transition.hasSetOperation()) {
                variable.setValue(transition.getOperationRelatedValue());
            } else if (transition.hasIncrementOperation()) {
                Integer value = ((Integer) variable.getValue());
                value = value != null ? value : 0;
                variable.setValue(value + 1);
            } else if (transition.hasDecrementOperation()) {
                Integer value = ((Integer) variable.getValue());
                value = value != null ? value : 0;
                variable.setValue(value - 1);
            }
        }
    }
    private boolean isTransitionValid(Transition transition) {
        if (!transition.isConditional()) {
            return true;
        }
        var condVariable = machine.getVariable((String) transition.getConditionVariableName());
        var condValue = condVariable.getValue() != null ? condVariable.getValue() : 0;
        if (condValue instanceof Integer conditionalInteger) {
            Integer compared = transition.getConditionComparedValue();
            if (transition.isConditionEqual()) {
                return compared.equals(conditionalInteger);
            } else if (transition.isConditionGreaterThan()) {
                return conditionalInteger > compared;
            } else if (transition.isConditionLessThan()) {
                return conditionalInteger < compared;
            }
        }
        return false;
    }

    public int getInteger(String string) {
        return machine.getVariable(string).getValue() != null ? (int) machine.getVariable(string).getValue() : 0;
    }

}
