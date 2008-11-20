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

import de.uniol.ui.fsm.model.states.State;

public class State_cooling extends State {

	private BaseController bc;

	public State_cooling(BaseController fsm) {
		super("cooling", fsm, null);
		this.bc = fsm;
	}

	protected void entryAction() {
		bc.getFridge().enableCooling();
		bc.setTFrom(bc.getFridge().getTemperature());
		bc.setTauSwitch(bc.tau_reqc(bc.getTfrom(), bc.getTdest()));
		bc.setCounter(0.0);
	}

	protected void exitAction() {
		bc.setTauCooling(((bc.getTmax() - bc.getTmin()) / (bc.getTfrom() - bc
				.getFridge().getTemperature()))
				* bc.getCounter());
	}
}