package de.uniol.ui.fsm.projects.fridge.tlr_extension_stateful;

import de.uniol.ui.fsm.model.states.State;
import de.uniol.ui.fsm.model.transitions.Transition;

public class T_idle_TO_wait_active extends Transition {

	private TLR_extension_stateful ext;

	public T_idle_TO_wait_active(TLR_extension_stateful fsm, State_idle source,
			State_wait_active dest) {
		super(fsm, TLR_extension_stateful.EV_REDUCE, source, dest);
		this.ext = fsm;
	}

	public void action(Object... params) {
		ext.setTau_preload((Double) params[0]);
		ext.setTau_reduce((Double) params[1]);
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