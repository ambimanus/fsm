package de.uniol.ui.fsm.projects.fridge.dsc_stateful;

import de.uniol.ui.fsm.model.transitions.TimerTransition;
import de.uniol.ui.fsm.projects.fridge.BaseController;

public class T_wait_random_EV_load_TO_restore extends TimerTransition {

	Extension_DSC_stateful dsc;

	public T_wait_random_EV_load_TO_restore(Extension_DSC_stateful fsm,
			State_wait_random source, State_wait_restore dest) {
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