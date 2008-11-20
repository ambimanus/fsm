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
package de.uniol.ui.fsm.projects.fridge.tlr_extension_stateful;

import de.uniol.ui.fsm.model.states.State;
import de.uniol.ui.fsm.projects.fridge.tlr.Extension_TLR.DeviceState;

public class State_wait_active extends State {

	TLR_extension_stateful ext;
	
	public State_wait_active(TLR_extension_stateful fsm) {
		super("wait_active", fsm, null);
		this.ext = fsm;
	}

	protected void entryAction() {
		ext.setOState(new DeviceState(ext.getBc().getFridge().getTemperature(),
				ext.getBc().getFridge().isActive()));
	}

	protected void exitAction() {
		//no-op
	}
}