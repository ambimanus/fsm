package de.uniol.ui.fsm.projects.fridge;

import de.uniol.ui.fsm.model.transitions.DefaultTransition;

public class T_default_TO_warming extends DefaultTransition {

	private BaseController bc;
	
	public T_default_TO_warming(BaseController fsm, State_warming dest) {
		super(fsm, dest);
		this.bc = fsm;
	}

	public void action(Object... params) {
		super.action(params);
		bc.setTDest(bc.getTmax());
	}
}