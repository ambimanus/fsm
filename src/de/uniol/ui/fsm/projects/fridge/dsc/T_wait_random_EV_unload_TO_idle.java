package de.uniol.ui.fsm.projects.fridge.dsc;

import de.uniol.ui.fsm.model.transitions.TimerTransition;
import de.uniol.ui.fsm.projects.fridge.BaseController;

public class T_wait_random_EV_unload_TO_idle extends TimerTransition {

	Extension_DSC dsc;

	public T_wait_random_EV_unload_TO_idle(Extension_DSC fsm,
			State_wait_random source, State_idle dest) {
		super(fsm, source, dest, -1L);
		this.dsc = fsm;
	}

	public void action(Object... params) {
		dsc.getBc().signal(BaseController.EV_WARMING,
				dsc.getBc().getC_polling(), dsc.getBc().getTmax());
	}

	public boolean guard() {
		return dsc.isDoUnload();
	}
}