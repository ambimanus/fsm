package de.uniol.ui.fsm.projects.fridge.tlr;

import de.uniol.ui.fsm.model.transitions.TimerTransition;
import de.uniol.ui.fsm.projects.fridge.BaseController;

public class T_intersectSCB_TO_idle_TMin extends TimerTransition {

	private Extension_TLR tlr;

	public T_intersectSCB_TO_idle_TMin(Extension_TLR fsm,
			State_intersectSCB source, State_idle dest) {
		super(fsm, source, dest, -1L);
		this.tlr = fsm;
	}

	public void action(Object... params) {
		tlr.getBc().signal(BaseController.EV_COOLING,
				tlr.getBc().getW_polling(), tlr.getBc().getTmin());
	}

	public boolean guard() {
		return tlr.getTau_b() <= 0
				&& tlr.getBc().getFridge().getTemperature() <= tlr
						.getT_allowed();
	}
}