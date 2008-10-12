package de.uniol.ui.fsm.projects.fridge.tlr;

import de.uniol.ui.fsm.model.transitions.Transition;
import de.uniol.ui.fsm.projects.fridge.BaseController;

public class T_reduce_TO_idle_TMaxred extends Transition {

	private Extension_TLR tlr;

	public T_reduce_TO_idle_TMaxred(Extension_TLR fsm,
			State_reduce source, State_idle dest) {
		super(fsm, null, source, dest);
		this.tlr = fsm;
	}

	public void action(Object... params) {
		tlr.getBc().signal(BaseController.EV_WARMING,
				tlr.getBc().getC_polling(), tlr.getT_max_red());
	}

	public boolean guard() {
		return tlr.isC1();
	}
}