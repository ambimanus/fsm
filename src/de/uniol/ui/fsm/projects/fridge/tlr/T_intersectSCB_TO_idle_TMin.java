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
package de.uniol.ui.fsm.projects.fridge.tlr;

import de.uniol.ui.fsm.model.transitions.TimerTransition;
import de.uniol.ui.fsm.projects.fridge.BaseController;

public class T_intersectSCB_TO_idle_TMin extends TimerTransition {

	private Extension_TLR tlr;

	public T_intersectSCB_TO_idle_TMin(Extension_TLR fsm,
			State_intersectSCB source, State_idle dest) {
		super(fsm, source, dest, -1L);
		this.tlr = fsm;
	}

	public void action(Object... params) {
		tlr.getBc().signal(BaseController.EV_COOLING,
				tlr.getBc().getW_polling(), tlr.getBc().getTmin());
	}

	public boolean guard() {
		return tlr.getTau_b() <= 0
				&& tlr.getBc().getFridge().getTemperature() <= tlr
						.getT_allowed();
	}
}