package de.uniol.ui.fsm.projects.fridge.dsc_random;

import de.uniol.ui.fsm.model.transitions.Transition;

public class T_idle_EV_load_TO_wait_random extends Transition {

	Extension_DSC_random dsc;

	public T_idle_EV_load_TO_wait_random(Extension_DSC_random fsm, State_idle source,
			State_wait_random dest) {
		super(fsm, Extension_DSC_random.EV_LOAD, source, dest);
		this.dsc = fsm;
	}

	public void action(Object... params) {
		dsc.setDoUnload(false);
		dsc.setDelay(Math.round(dsc.drawUniformRandom(0.0,
				(Double) params[0])));
	}

	public boolean guard() {
		return !dsc.getBc().getFridge().isActive();
	}
}