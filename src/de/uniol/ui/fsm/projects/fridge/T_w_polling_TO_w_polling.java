/*
 * FSM Copyright (C) 2008 Christian Hinrichs
 * 
 * FSM is copyright under the GNU General Public License.
 * 
 * This file is part of FSM.
 * 
 * FSM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * FSM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with FSM.  If not, see <http://www.gnu.org/licenses/>.
 */
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