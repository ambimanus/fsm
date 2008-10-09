package de.uniol.ui.fsm.projects.fridge.dsc_stateful_fullwidth;

import java.util.Random;

import de.uniol.ui.fsm.model.transitions.Transition;

public class T_idle_EV_load_TO_wait_random extends Transition {

	Extension_DSC_stateful dsc;

	public T_idle_EV_load_TO_wait_random(Extension_DSC_stateful fsm, State_idle source,
			State_wait_random dest) {
		super(fsm, Extension_DSC_stateful.EV_LOAD, source, dest);
		this.dsc = fsm;
	}

	public void action(Object... params) {
		dsc.setDoUnload(false);
		dsc.setDelay(new Random()
				.nextInt((int) Math.round((Double) params[0]) + 1));
	}

	public boolean guard() {
		return !dsc.getBc().getFridge().isActive();
	}
}