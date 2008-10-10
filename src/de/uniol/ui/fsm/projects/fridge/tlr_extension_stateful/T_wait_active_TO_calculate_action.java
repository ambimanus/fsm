package de.uniol.ui.fsm.projects.fridge.tlr_extension_stateful;

import de.uniol.ui.fsm.model.FSM;
import de.uniol.ui.fsm.model.transitions.TimerTransition;

public class T_wait_active_TO_calculate_action extends TimerTransition {

	public T_wait_active_TO_calculate_action(FSM parent,
			State_wait_active source, State_calculate_action dest) {
		super(parent, source, dest, -1L);
	}

	public void action(Object... params) {
		// no-op
	}

	public boolean guard() {
		return true;
	}
}