package de.uniol.ui.fsm.projects.fridge;

import de.uniol.ui.fsm.model.states.State;

public class State_warming extends State {

	private BaseController bc;

	public State_warming(BaseController fsm) {
		super("warming", fsm, null);
		this.bc = fsm;
	}

	protected void entryAction() {
		bc.getFridge().disableCooling();
		bc.setTFrom(bc.getFridge().getTemperature());
		bc.setTauSwitch(bc.tau_reqw(bc.getTfrom(), bc.getTdest()));
		bc.setCounter(0.0);
	}

	protected void exitAction() {
		bc.setTauWarming(((bc.getTmax() - bc.getTmin()) / (bc.getTdest() - bc
				.getTfrom()))
				* bc.getCounter());
	}
}