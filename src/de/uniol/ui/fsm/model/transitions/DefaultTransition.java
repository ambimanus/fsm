package de.uniol.ui.fsm.model.transitions;

import de.uniol.ui.fsm.model.FSM;
import de.uniol.ui.fsm.model.states.NullState;
import de.uniol.ui.fsm.model.states.State;

public class DefaultTransition extends Transition {

	public DefaultTransition(FSM fsm, State dest) {
		super(fsm, null, new NullState(fsm), dest);
	}

	public void action(Object... params) {
		// no-op
	}

	public boolean guard() {
		// no guard
		return true;
	}
}