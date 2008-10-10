package de.uniol.ui.fsm.projects.fridge.tlr_extension_stateful;

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