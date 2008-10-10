package de.uniol.ui.fsm.projects.fridge.tlr_extension_stateful;

import de.uniol.ui.fsm.model.transitions.TimerTransition;

public class T_calculate_action_TO_calculate_state_warmup extends TimerTransition {

	private TLR_extension_stateful ext;

	public T_calculate_action_TO_calculate_state_warmup(TLR_extension_stateful parent,
			State_calculate_action source, State_calculate_state dest) {
		super(parent, source, dest, -1L);
		this.ext = parent;
	}

	public void action(Object... params) {
		ext.setRState(ext.stateAfter(ext.getOState(), ext.getTau_preload()
				+ ext.getTau_warmup()));
	}

	public boolean guard() {
		return ext.getTau_reduce() > ext.getTau_warmup();
	}
}