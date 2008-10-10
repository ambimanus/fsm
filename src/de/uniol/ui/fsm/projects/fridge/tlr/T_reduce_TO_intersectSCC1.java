package de.uniol.ui.fsm.projects.fridge.tlr;

import de.uniol.ui.fsm.model.transitions.Transition;

public class T_reduce_TO_intersectSCC1 extends Transition {

	private Extension_TLR tlr;

	public T_reduce_TO_intersectSCC1(Extension_TLR fsm, State_reduce source,
			State_intersectSCC1 dest) {
		super(fsm, null, source, dest);
		this.tlr = fsm;
	}

	public void action(Object... params) {
		// no-op
	}

	public boolean guard() {
		return tlr.isCC1();
	}
}