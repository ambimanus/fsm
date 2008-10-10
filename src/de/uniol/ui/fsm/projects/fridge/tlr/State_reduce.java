package de.uniol.ui.fsm.projects.fridge.tlr;

import de.uniol.ui.fsm.model.states.State;

public class State_reduce extends State {

	Extension_TLR tlr;

	public State_reduce(Extension_TLR fsm) {
		super("reduce", fsm, null);
		this.tlr = fsm;
	}

	protected void entryAction() {
		boolean CC1 = false;
		boolean BA = false;
		if (tlr.getTau_reduce() <= tlr.getBc().getTauWarming()) {
			double tau_gamma_scc1 = tlr.calculate_tau_gamma_scc1();
			tlr.setTau_gamma_scc1(tau_gamma_scc1);
			CC1 = 0 <= tau_gamma_scc1 && tau_gamma_scc1 <= tlr.getTau_preload();
			double tau_gamma_sba = tlr.calculate_tau_gamma_sba();
			tlr.setTau_gamma_sba(tau_gamma_sba);
			BA = 0 <= tau_gamma_sba && tau_gamma_sba <= tlr.getTau_preload();
			if (CC1 && BA) {
				CC1 = tau_gamma_scc1 < tau_gamma_sba;
				BA = tau_gamma_sba <= tau_gamma_scc1;
			}
		}
		tlr.setCC1(CC1);
		tlr.setBA(BA);
	}

	protected void exitAction() {
		// no-op
	}
}