package de.uniol.ui.fsm.projects.fridge.dsc_random;

import de.uniol.ui.fsm.model.transitions.TimerTransition;
import de.uniol.ui.fsm.projects.fridge.BaseController;

public class T_wait_random_EV_unload_TO_restore extends TimerTransition {

	Extension_DSC_random dsc;

	public T_wait_random_EV_unload_TO_restore(Extension_DSC_random fsm,
			State_wait_random source, State_wait_restore dest) {
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