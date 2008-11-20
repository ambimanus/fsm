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
package de.uniol.ui.fsm.projects.fridge.dsc_stateful;

import de.uniol.ui.fsm.model.states.State;

public class State_wait_restore extends State {

	private Extension_DSC_stateful dsc;

	public State_wait_restore(Extension_DSC_stateful fsm) {
		super("wait_random", fsm, null);
		this.dsc = fsm;
	}

	protected void entryAction() {
		double Tcurrent = dsc.getBc().getFridge().getTemperature();
		double Tcrossing = dsc.getBc().getTmin() + dsc.getBc().getTmax()
				- Tcurrent;
		if (dsc.isDoUnload()) {
			dsc.setTau_restore(Math.round(dsc.getBc().tau_reqw(Tcurrent,
					dsc.getBc().getTmax())
					+ dsc.getBc().tau_reqc(dsc.getBc().getTmax(), Tcrossing)));
		} else {
			dsc.setTau_restore(Math.round(dsc.getBc().tau_reqc(Tcurrent,
					dsc.getBc().getTmin())
					+ dsc.getBc().tau_reqw(dsc.getBc().getTmin(), Tcrossing)));
		}
	}

	protected void exitAction() {
		// no-op
	}
}