package de.uniol.ui.fsm.projects.fridge.dsc_random;

import de.uniol.ui.fsm.model.states.State;

public class State_wait_restore extends State {

	private Extension_DSC_random dsc;

	public State_wait_restore(Extension_DSC_random fsm) {
		super("wait_random", fsm, null);
		this.dsc = fsm;
	}

	protected void entryAction() {
//		if (dsc.isDoUnload()) {
//			dsc.setTau_restore(60L * Math.round(dsc.getBc().tau_reqw(
//					dsc.getBc().getFridge().getTemperature(),
//					dsc.getBc().getTmax())));
//		} else {
//			dsc.setTau_restore(60L * Math.round(dsc.getBc().tau_reqc(
//					dsc.getBc().getFridge().getTemperature(),
//					dsc.getBc().getTmin())));
//		}
		dsc.setTau_restore(60L * Math.round(dsc.getBc().getTauCooling()
				+ dsc.getBc().getTauWarming()));
//		if (dsc.drawBernoulli(0.5) != 0.0) {
//			dsc.setT_dest(dsc.getBc().getTmin());
//		} else {
//			dsc.setT_dest(dsc.getBc().getTmax());
//		}
		dsc.setT_dest(dsc.drawUniformRandom(dsc.getBc().getTmin(), dsc.getBc()
				.getTmax()));
	}

	protected void exitAction() {
		// no-op
	}
}