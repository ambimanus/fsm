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
package de.uniol.ui.fsm.projects.fridge.dsc_stateful_fullwidth;

import de.uniol.ui.fsm.model.states.State;

public class State_wait_restore extends State {

	private Extension_DSC_stateful_fullwidth dsc;

	public State_wait_restore(Extension_DSC_stateful_fullwidth fsm) {
		super("wait_random", fsm, null);
		this.dsc = fsm;
	}

	protected void entryAction() {
		dsc.setTau_restore(Math.round(dsc.getBc().getTauCooling()
				+ dsc.getBc().getTauWarming()));
	}

	protected void exitAction() {
		// no-op
	}
}