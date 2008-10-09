package de.uniol.ui.fsm.projects.fridge.dsc_stateful_fullwidth;

import de.uniol.ui.fsm.model.FSM;
import de.uniol.ui.fsm.model.states.State;

public class State_idle extends State {

	public State_idle(FSM fsm) {
		super("idle", fsm, null);
	}

	protected void entryAction() {
		// no-op
	}

	protected void exitAction() {
		// no-op
	}
}