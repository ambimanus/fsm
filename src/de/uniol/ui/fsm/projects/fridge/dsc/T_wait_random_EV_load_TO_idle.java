package de.uniol.ui.fsm.projects.fridge.dsc;

import de.uniol.ui.fsm.model.transitions.TimerTransition;
import de.uniol.ui.fsm.projects.fridge.BaseController;

public class T_wait_random_EV_load_TO_idle extends TimerTransition {

	Extension_DSC dsc;

	public T_wait_random_EV_load_TO_idle(Extension_DSC fsm,
			State_wait_random source, State_idle dest) {
		super(fsm, source, dest, 0L);
		this.dsc = fsm;
	}

	public void action(Object... params) {
		dsc.getBc().signal(BaseController.EV_COOLING,
				dsc.getBc().getW_polling(), dsc.getBc().getTmin());
	}

	public boolean guard() {
		return !dsc.isDoUnload();
	}
}