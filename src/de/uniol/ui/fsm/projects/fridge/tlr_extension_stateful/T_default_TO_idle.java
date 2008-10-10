package de.uniol.ui.fsm.projects.fridge.tlr_extension_stateful;

import de.uniol.ui.fsm.model.FSM;
import de.uniol.ui.fsm.model.transitions.DefaultTransition;

public class T_default_TO_idle extends DefaultTransition {

	public T_default_TO_idle(FSM fsm, State_idle dest) {
		super(fsm, dest);
	}
}