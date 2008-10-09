package de.uniol.ui.fsm.projects.fridge.dsc_stateful_fullwidth;

import de.uniol.ui.fsm.model.transitions.TimerTransition;
import de.uniol.ui.fsm.projects.fridge.BaseController;

public class T_wait_restore_EV_load_TO_idle extends TimerTransition {

	Extension_DSC_stateful_fullwidth dsc;

	public T_wait_restore_EV_load_TO_idle(Extension_DSC_stateful_fullwidth fsm,
			State_wait_restore source, State_idle dest) {
		super(fsm, source, dest, 0L);
		this.dsc = fsm;
	}

	public void action(Object... params) {
		dsc.getBc().signal(BaseController.EV_WARMING,
				dsc.getBc().getC_polling(), dsc.getBc().getTmax());
	}

	public boolean guard() {
		return !dsc.isDoUnload();
	}
}