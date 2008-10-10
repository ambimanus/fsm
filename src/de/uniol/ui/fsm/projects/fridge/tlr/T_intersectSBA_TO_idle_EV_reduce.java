package de.uniol.ui.fsm.projects.fridge.tlr;

import de.uniol.ui.fsm.model.transitions.TimerTransition;

public class T_intersectSBA_TO_idle_EV_reduce extends TimerTransition {

	private Extension_TLR tlr;

	public T_intersectSBA_TO_idle_EV_reduce(Extension_TLR fsm,
			State_intersectSBA source, State_idle dest) {
		super(fsm, source, dest, -1L);
		this.tlr = fsm;
	}

	public void action(Object... params) {
		tlr.signal(Extension_TLR.EV_REDUCE, tlr.getIdle(), tlr.getTau_preload()
				- tlr.getTau_a(), tlr.getTau_reduce());
	}

	public boolean guard() {
		return tlr.getTau_a() > 0;
	}
}