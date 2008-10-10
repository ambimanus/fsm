package de.uniol.ui.fsm.projects.fridge.tlr_extension_stateful;

import de.uniol.ui.fsm.model.states.State;
import de.uniol.ui.fsm.projects.fridge.tlr.Extension_TLR.DeviceState;

public class State_calculate_state extends State {

	TLR_extension_stateful ext;

	public State_calculate_state(TLR_extension_stateful fsm) {
		super("calculate_state", fsm, null);
		this.ext = fsm;
	}

	protected void entryAction() {
		ext.setTau_gamma(ext.gamma(ext.getRState(), new DeviceState(ext.getBc()
				.getFridge().getTemperature(), true)));
		ext.setDState(ext.stateAfter(ext.getRState(), ext.getTau_gamma()));
	}

	protected void exitAction() {
		// no-op
	}
}