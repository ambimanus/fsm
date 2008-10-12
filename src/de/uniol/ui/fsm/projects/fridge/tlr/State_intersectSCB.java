package de.uniol.ui.fsm.projects.fridge.tlr;

import de.uniol.ui.fsm.model.states.State;

public class State_intersectSCB extends State {

	Extension_TLR tlr;

	public State_intersectSCB(Extension_TLR fsm) {
		super("intersectSCB", fsm, null);
		this.tlr = fsm;
	}

	protected void entryAction() {
		tlr.setTau_b(tlr.getTau_preload() - tlr.getBc().getTauCooling());
		if (tlr.getTau_b() <= 0) {
			double Tallowed = tlr.getBc().getTmax()
					+ (tlr.getBc().ac() * (-tlr.getTau_b()));
			tlr.setT_allowed(Tallowed);
			double TCurrent = tlr.getBc().getFridge().getTemperature();
			if (TCurrent <= Tallowed) {
				tlr.setTau_gamma_scb(tlr.calculate_tau_gamma_scb());
			} else {
				tlr
						.setT_activ(tlr.TAfter(true, tlr.getTau_preload(),
								TCurrent));
			}
		}
	}

	protected void exitAction() {
		// no-op
	}
}