package de.uniol.ui.fsm.projects.fridge.tlr;

import de.uniol.ui.fsm.model.transitions.Transition;

public class T_reduce_TO_intersectSBA extends Transition {

	private Extension_TLR tlr;

	public T_reduce_TO_intersectSBA(Extension_TLR fsm, State_reduce source,
			State_intersectSBA dest) {
		super(fsm, null, source, dest);
		this.tlr = fsm;
	}

	public void action(Object... params) {
		// no-op
	}

	public boolean guard() {
		return tlr.isBA();
	}
}