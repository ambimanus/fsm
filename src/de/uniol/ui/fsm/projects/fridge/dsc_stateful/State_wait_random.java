package de.uniol.ui.fsm.projects.fridge.dsc_stateful;

import de.uniol.ui.fsm.model.FSM;
import de.uniol.ui.fsm.model.states.State;

public class State_wait_random extends State {

	public State_wait_random(FSM fsm) {
		super("wait_random", fsm, null);
	}

	protected void entryAction() {
		// no-op
	}

	protected void exitAction() {
		// no-op
	}
}