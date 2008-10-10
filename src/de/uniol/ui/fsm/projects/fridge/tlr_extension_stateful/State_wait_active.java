package de.uniol.ui.fsm.projects.fridge.tlr_extension_stateful;

import de.uniol.ui.fsm.model.states.State;
import de.uniol.ui.fsm.projects.fridge.tlr.Extension_TLR.DeviceState;

public class State_wait_active extends State {

	TLR_extension_stateful ext;
	
	public State_wait_active(TLR_extension_stateful fsm) {
		super("wait_active", fsm, null);
		this.ext = fsm;
	}

	protected void entryAction() {
		ext.setOState(new DeviceState(ext.getBc().getFridge().getTemperature(),
				ext.getBc().getFridge().isActive()));
	}

	protected void exitAction() {
		//no-op
	}
}