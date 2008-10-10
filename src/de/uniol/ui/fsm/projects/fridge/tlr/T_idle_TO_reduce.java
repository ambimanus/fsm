package de.uniol.ui.fsm.projects.fridge.tlr;

import de.uniol.ui.fsm.model.transitions.Transition;

public class T_idle_TO_reduce extends Transition {

	private Extension_TLR tlr;

	public T_idle_TO_reduce(Extension_TLR fsm, State_idle source,
			State_reduce dest) {
		super(fsm, Extension_TLR.EV_REDUCE, source, dest);
		this.tlr = fsm;
	}

	public void action(Object... params) {
		tlr.setTau_preload((Double) params[0]);
		tlr.setTau_reduce((Double) params[1]);
	}

	public boolean guard() {
		return true;
	}
}