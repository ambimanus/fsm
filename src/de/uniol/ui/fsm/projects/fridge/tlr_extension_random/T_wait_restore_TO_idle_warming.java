package de.uniol.ui.fsm.projects.fridge.tlr_extension_random;

import de.uniol.ui.fsm.model.transitions.TimerTransition;
import de.uniol.ui.fsm.projects.fridge.BaseController;

public class T_wait_restore_TO_idle_warming extends TimerTransition {

	private TLR_extension_random ext;

	public T_wait_restore_TO_idle_warming(TLR_extension_random parent,
			State_wait_restore source, State_idle dest) {
		super(parent, source, dest, -1L);
		this.ext = parent;
	}

	public void action(Object... params) {
		ext.getBc().signal(BaseController.EV_WARMING,
				ext.getBc().getC_polling(), ext.getTDest());
	}

	public boolean guard() {
		return ext.getTDest() >= ext.getBc().getFridge().getTemperature();
	}
}