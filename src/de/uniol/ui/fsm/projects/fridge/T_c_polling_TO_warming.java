package de.uniol.ui.fsm.projects.fridge;

import de.uniol.ui.fsm.model.transitions.Transition;

public class T_c_polling_TO_warming extends Transition {

	private BaseController bc;
	
	public T_c_polling_TO_warming(BaseController fsm, State_c_polling source,
			State_warming dest) {
		super(fsm, BaseController.EV_WARMING, source, dest);
		this.bc = fsm;
	}

	public void action(Object... params) {
		bc.setTDest((Double) params[0]);
	}

	public boolean guard() {
		return true;
	}
}