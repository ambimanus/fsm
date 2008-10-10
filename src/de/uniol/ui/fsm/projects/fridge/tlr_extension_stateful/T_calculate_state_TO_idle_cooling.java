package de.uniol.ui.fsm.projects.fridge.tlr_extension_stateful;

import de.uniol.ui.fsm.model.transitions.TimerTransition;
import de.uniol.ui.fsm.projects.fridge.BaseController;

public class T_calculate_state_TO_idle_cooling extends TimerTransition {

	private TLR_extension_stateful ext;

	public T_calculate_state_TO_idle_cooling(TLR_extension_stateful parent,
			State_calculate_state source, State_idle dest) {
		super(parent, source, dest, -1L);
		this.ext = parent;
	}

	public void action(Object... params) {
		ext.getBc().signal(BaseController.EV_COOLING,
				ext.getBc().getW_polling(), ext.getBc().getTmin());
	}

	public boolean guard() {
		return ext.getDState().active;
	}
}