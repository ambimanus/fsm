package de.uniol.ui.fsm.projects.fridge;

import de.uniol.ui.fsm.model.states.State;

public class State_cooling extends State {

	private BaseController bc;

	public State_cooling(BaseController fsm) {
		super("cooling", fsm, null);
		this.bc = fsm;
	}

	protected void entryAction() {
		bc.getFridge().enableCooling();
		bc.setTFrom(bc.getFridge().getTemperature());
		bc.setTauSwitch(bc.tau_reqc(bc.getTfrom(), bc.getTdest()));
		bc.setCounter(0.0);
	}

	protected void exitAction() {
		bc.setTauCooling(((bc.getTmax() - bc.getTmin()) / (bc.getTfrom() - bc
				.getFridge().getTemperature()))
				* bc.getCounter());
	}
}