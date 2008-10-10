package de.uniol.ui.fsm.projects.fridge.dsc_random;

import de.uniol.ui.fsm.model.transitions.TimerTransition;
import de.uniol.ui.fsm.projects.fridge.BaseController;

public class T_wait_restore_cooling_TO_idle extends TimerTransition {

	Extension_DSC_random dsc;

	public T_wait_restore_cooling_TO_idle(Extension_DSC_random fsm,
			State_wait_restore source, State_idle dest) {
		super(fsm, source, dest, -1L);
		this.dsc = fsm;
	}

	public void action(Object... params) {
		dsc.getBc().signal(BaseController.EV_COOLING,
				dsc.getBc().getC_polling(), dsc.getT_dest());
	}

	public boolean guard() {
		return dsc.getT_dest() <= dsc.getBc().getFridge().getTemperature();
	}
}