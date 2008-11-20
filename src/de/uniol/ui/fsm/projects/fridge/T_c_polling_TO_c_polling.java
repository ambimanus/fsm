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
 * AdaptiveFridge is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with FSM.  If not, see <http://www.gnu.org/licenses/>.
 */
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