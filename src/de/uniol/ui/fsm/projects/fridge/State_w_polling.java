package de.uniol.ui.fsm.projects.fridge;

import de.uniol.ui.fsm.model.states.State;

public class State_w_polling extends State {

	public State_w_polling(BaseController fsm, State_warming parent) {
		super("w_polling", fsm, parent);
	}

	protected void entryAction() {
		// no-op
	}

	protected void exitAction() {
		// no-op
	}
}