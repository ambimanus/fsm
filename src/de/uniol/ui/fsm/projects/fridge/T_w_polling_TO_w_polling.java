package de.uniol.ui.fsm.projects.fridge;

import de.uniol.ui.fsm.model.transitions.TimerTransition;

public class T_w_polling_TO_w_polling extends TimerTransition {

	private BaseController bc;
	
	public T_w_polling_TO_w_polling(BaseController fsm, State_w_polling source) {
		super(fsm, source, source, 1L);
		this.bc = fsm;
	}

	public void action(Object... params) {
		bc.setCounter(bc.getCounter() + (1.0 / 60.0));
		if (bc.getCounter() >= bc.getTauSwitch()
				|| bc.getFridge().getTemperature() >= bc.getTmax()) {
			bc.signal(BaseController.EV_COOLING, getSource(), bc.getTmin());
		}
	}

	public boolean guard() {
		return true;
	}
}