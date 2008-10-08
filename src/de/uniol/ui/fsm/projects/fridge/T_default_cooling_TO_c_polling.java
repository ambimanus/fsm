package de.uniol.ui.fsm.projects.fridge;

import de.uniol.ui.fsm.model.FSM;
import de.uniol.ui.fsm.model.transitions.Transition;

public class T_default_cooling_TO_c_polling extends Transition {

	public T_default_cooling_TO_c_polling(FSM fsm, State_cooling source,
			State_c_polling dest) {
		super(fsm, null, source, dest);
	}

	public void action(Object... params) {
		// no-op
	}

	public boolean guard() {
		return true;
	}
}