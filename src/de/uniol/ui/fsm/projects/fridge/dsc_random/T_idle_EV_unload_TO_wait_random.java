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
package de.uniol.ui.fsm.projects.fridge.dsc_random;

import de.uniol.ui.fsm.model.transitions.Transition;

public class T_idle_EV_unload_TO_wait_random extends Transition {

	Extension_DSC_random dsc;

	public T_idle_EV_unload_TO_wait_random(Extension_DSC_random fsm,
			State_idle source, State_wait_random dest) {
		super(fsm, Extension_DSC_random.EV_UNLOAD, source, dest);
		this.dsc = fsm;
	}

	public void action(Object... params) {
		dsc.setDoUnload(true);
		dsc.setDelay(Math.round(dsc.drawUniformRandom(0.0,
				(Double) params[0])));
	}

	public boolean guard() {
		return dsc.getBc().getFridge().isActive();
	}
}