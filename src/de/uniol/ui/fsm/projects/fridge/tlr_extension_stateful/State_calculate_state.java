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
package de.uniol.ui.fsm.projects.fridge.tlr_extension_stateful;

import de.uniol.ui.fsm.model.states.State;
import de.uniol.ui.fsm.projects.fridge.tlr.Extension_TLR.DeviceState;

public class State_calculate_state extends State {

	TLR_extension_stateful ext;

	public State_calculate_state(TLR_extension_stateful fsm) {
		super("calculate_state", fsm, null);
		this.ext = fsm;
	}

	protected void entryAction() {
		ext.setTau_gamma(ext.gamma(ext.getRState(), new DeviceState(ext.getBc()
				.getFridge().getTemperature(), true)));
		ext.setDState(ext.stateAfter(ext.getRState(), ext.getTau_gamma()));
	}

	protected void exitAction() {
		// no-op
	}
}