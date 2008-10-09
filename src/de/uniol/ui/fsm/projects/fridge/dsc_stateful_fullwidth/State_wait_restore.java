package de.uniol.ui.fsm.projects.fridge.dsc_stateful_fullwidth;

import de.uniol.ui.fsm.model.states.State;

public class State_wait_restore extends State {

	private Extension_DSC_stateful_fullwidth dsc;

	public State_wait_restore(Extension_DSC_stateful_fullwidth fsm) {
		super("wait_random", fsm, null);
		this.dsc = fsm;
	}

	protected void entryAction() {
		dsc.setTau_restore(60L * Math.round(dsc.getBc().getTauCooling()
				+ dsc.getBc().getTauWarming()));
	}

	protected void exitAction() {
		// no-op
	}
}