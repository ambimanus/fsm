package de.uniol.ui.fsm.projects.fridge.tlr;

import de.uniol.ui.fsm.model.states.State;

public class State_intersectSCC1 extends State {

	Extension_TLR tlr;

	public State_intersectSCC1(Extension_TLR fsm) {
		super("intersectSCC1", fsm, null);
		this.tlr = fsm;
	}

	protected void entryAction() {
		tlr.setTau_c1(tlr.getTau_preload() + tlr.getTau_reduce()
				- tlr.getBc().getTauWarming());
		if (tlr.getTau_c1() <= 0) {
			tlr.setT_allowed(tlr.getBc().getTmin()
					+ (tlr.getBc().aw() * (-tlr.getTau_c1())));
		}
	}

	protected void exitAction() {
		// no-op
	}
}