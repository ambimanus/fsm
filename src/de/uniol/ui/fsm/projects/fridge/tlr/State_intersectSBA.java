package de.uniol.ui.fsm.projects.fridge.tlr;

import de.uniol.ui.fsm.model.states.State;

public class State_intersectSBA extends State {

	Extension_TLR tlr;

	public State_intersectSBA(Extension_TLR fsm) {
		super("intersectSBA", fsm, null);
		this.tlr = fsm;
	}

	protected void entryAction() {
		tlr.setT_max_act(tlr.getBc().getTmin()
				+ (tlr.getBc().aw() * (tlr.getBc().getTauWarming() - tlr
						.getTau_reduce())));
		tlr.setTau_a(tlr.getTau_preload()
				- ((tlr.getT_max_act() - tlr.getBc().getTmax()) / tlr.getBc()
						.ac()));
		if (tlr.getTau_a() <= 0) {
			tlr.setT_allowed(tlr.getBc().getTmax()
					+ (tlr.getBc().ac() * (-tlr.getTau_a())));
			double tCurrent = tlr.getBc().getFridge().getTemperature();
			if (tCurrent > tlr.getT_allowed()) {
				tlr
						.setT_activ(tlr.TAfter(true, tlr.getTau_preload(),
								tCurrent));
			}
		}
	}

	protected void exitAction() {
		// no-op
	}
}