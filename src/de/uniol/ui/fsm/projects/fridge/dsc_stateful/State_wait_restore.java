package de.uniol.ui.fsm.projects.fridge.dsc_stateful;

import de.uniol.ui.fsm.model.states.State;

public class State_wait_restore extends State {

	private Extension_DSC_stateful dsc;

	public State_wait_restore(Extension_DSC_stateful fsm) {
		super("wait_random", fsm, null);
		this.dsc = fsm;
	}

	protected void entryAction() {
		double Tcurrent = dsc.getBc().getFridge().getTemperature();
		double Tcrossing = dsc.getBc().getTmin() + dsc.getBc().getTmax()
				- Tcurrent;
		if (dsc.isDoUnload()) {
			dsc.setTau_restore(Math.round(dsc.getBc().tau_reqw(Tcurrent,
					dsc.getBc().getTmax())
					+ dsc.getBc().tau_reqc(dsc.getBc().getTmax(), Tcrossing)));
		} else {
			dsc.setTau_restore(Math.round(dsc.getBc().tau_reqc(Tcurrent,
					dsc.getBc().getTmin())
					+ dsc.getBc().tau_reqw(dsc.getBc().getTmin(), Tcrossing)));
		}
	}

	protected void exitAction() {
		// no-op
	}
}