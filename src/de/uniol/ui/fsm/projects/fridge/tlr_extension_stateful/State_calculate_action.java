package de.uniol.ui.fsm.projects.fridge.tlr_extension_stateful;

import de.uniol.ui.fsm.model.states.State;

public class State_calculate_action extends State {

	TLR_extension_stateful ext;
	
	public State_calculate_action(TLR_extension_stateful fsm) {
		super("calculate_action", fsm, null);
		this.ext = fsm;
	}

	protected void entryAction() {
		ext.setTau_warmup(ext.getBc()
				.tau_reqw(ext.getBc().getFridge().getTemperature(),
						ext.getBc().getTmax()));
	}

	protected void exitAction() {
		//no-op
	}
}