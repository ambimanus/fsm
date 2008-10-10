package de.uniol.ui.fsm.projects.fridge.tlr;

import de.uniol.ui.fsm.model.transitions.Transition;
import de.uniol.ui.fsm.projects.fridge.BaseController;

public class T_intersectSCC1_TO_idle_TMax extends Transition {

	private Extension_TLR tlr;

	public T_intersectSCC1_TO_idle_TMax(Extension_TLR fsm,
			State_intersectSCC1 source, State_idle dest) {
		super(fsm, null, source, dest);
		this.tlr = fsm;
	}

	public void action(Object... params) {
		tlr.getBc().signal(BaseController.EV_WARMING,
				tlr.getBc().getC_polling(), tlr.getBc().getTmax());
	}

	public boolean guard() {
		return tlr.getTau_c1() <= 0
				&& tlr.getBc().getFridge().getTemperature() <= tlr
						.getT_allowed();
	}
}