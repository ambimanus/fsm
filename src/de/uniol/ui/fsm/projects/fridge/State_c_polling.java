package de.uniol.ui.fsm.projects.fridge;

import de.uniol.ui.fsm.model.states.State;

public class State_c_polling extends State {

	public State_c_polling(BaseController fsm, State_cooling parent) {
		super("c_polling", fsm, parent);
	}

	protected void entryAction() {
		// no-op
	}

	protected void exitAction() {
		// no-op
	}
}