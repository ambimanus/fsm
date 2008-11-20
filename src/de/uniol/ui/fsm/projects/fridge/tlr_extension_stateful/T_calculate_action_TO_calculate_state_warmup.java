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

import de.uniol.ui.fsm.model.transitions.TimerTransition;

public class T_calculate_action_TO_calculate_state_warmup extends TimerTransition {

	private TLR_extension_stateful ext;

	public T_calculate_action_TO_calculate_state_warmup(TLR_extension_stateful parent,
			State_calculate_action source, State_calculate_state dest) {
		super(parent, source, dest, -1L);
		this.ext = parent;
	}

	public void action(Object... params) {
		ext.setRState(ext.stateAfter(ext.getOState(), ext.getTau_preload()
				+ ext.getTau_warmup()));
	}

	public boolean guard() {
		return ext.getTau_reduce() > ext.getTau_warmup();
	}
}