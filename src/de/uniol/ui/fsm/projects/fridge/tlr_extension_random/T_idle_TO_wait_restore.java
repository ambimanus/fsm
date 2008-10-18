package de.uniol.ui.fsm.projects.fridge.tlr_extension_random;

import de.uniol.ui.fsm.model.states.State;
import de.uniol.ui.fsm.model.transitions.Transition;

public class T_idle_TO_wait_restore extends Transition {

	private TLR_extension_random ext;

	public T_idle_TO_wait_restore(TLR_extension_random fsm, State_idle source,
			State_wait_restore dest) {
		super(fsm, TLR_extension_random.EV_REDUCE, source, dest);
		this.ext = fsm;
	}

	public void action(Object... params) {
		ext.setTau_preload((Double) params[0]);
		ext.setTau_reduce((Double) params[1]);
		long tau = Math.round(60.0 * (ext.getTau_preload() + ext
				.getTau_reduce()));
		ext.getT_cooling().setWaitDelay(tau);
		ext.getT_warming().setWaitDelay(tau);
	}

	public boolean guard() {
		return true;
	}

	public boolean signalReceived(String name, Object sender, Object... params) {
//		System.out.println("Time=" + ext.getClock() + " - "
//				+ this.getClass().getSimpleName() + " received Signal <"
//				+ name + "> from "
//				+ sender.getClass().getSimpleName() + ", params="
//				+ Arrays.toString(params));
		if (getSignal().equals(name) && guard()) {
			if (sender != getSource() && sender instanceof State) {
				((State) sender).exit(getDest());
			}
			getSource().exit(getDest());
			action(params);
			getDest().entry(getDest());
			return true;
		}
		return false;
	}
}