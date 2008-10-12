package de.uniol.ui.fsm.projects.fridge.tlr;

import de.uniol.ui.fsm.model.transitions.Transition;

public class T_reduce_TO_intersectSCB extends Transition {

	private Extension_TLR tlr;
	
	public T_reduce_TO_intersectSCB(Extension_TLR fsm, State_reduce source,
			State_intersectSCB dest) {
		super(fsm, null, source, dest);
		this.tlr = fsm;
	}

	public void action(Object... params) {
		// no-op
	}

	public boolean guard() {
		return tlr.getTau_reduce() > tlr.getBc().getTauWarming() && !tlr.isBA()
				&& !tlr.isCC1();
	}
}