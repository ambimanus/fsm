package de.uniol.ui.fsm.projects.fridge;

import de.uniol.ui.fsm.model.transitions.TimerTransition;

public class T_c_polling_TO_c_polling extends TimerTransition {

	private BaseController bc;

	public T_c_polling_TO_c_polling(BaseController fsm, State_c_polling source) {
		super(fsm, source, source, 1L);
		this.bc = fsm;
	}

	public void action(Object... params) {
		bc.setCounter(bc.getCounter() + (1.0 / 60.0));
		if (bc.getCounter() >= bc.getTauSwitch()
				|| bc.getFridge().getTemperature() <= bc.getTmin()) {
			bc.signal(BaseController.EV_WARMING, getSource(), bc.getTmax());
		}
	}

	public boolean guard() {
		return true;
	}
}