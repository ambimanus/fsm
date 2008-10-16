package de.uniol.ui.fsm.projects.fridge.tlr_extension_random;

import de.uniol.ui.fsm.model.states.State;

public class State_wait_restore extends State {

	TLR_extension_random ext;
	
	public State_wait_restore(TLR_extension_random fsm) {
		super("wait_active", fsm, null);
		this.ext = fsm;
	}

	protected void entryAction() {
		ext.setTDest(ext.drawUniformRandom(ext.getBc().getTmin(), ext.getBc()
				.getTmax()));
	}

	protected void exitAction() {
		//no-op
	}
}