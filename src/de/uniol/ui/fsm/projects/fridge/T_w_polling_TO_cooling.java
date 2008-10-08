package de.uniol.ui.fsm.projects.fridge;

import de.uniol.ui.fsm.model.transitions.Transition;

public class T_w_polling_TO_cooling extends Transition {

	private BaseController bc;
	
	public T_w_polling_TO_cooling(BaseController fsm, State_w_polling source,
			State_cooling dest) {
		super(fsm, BaseController.EV_COOLING, source, dest);
		this.bc = fsm;
	}

	public void action(Object... params) {
		bc.setTDest((Double) params[0]);
	}

	public boolean guard() {
		return true;
	}
}