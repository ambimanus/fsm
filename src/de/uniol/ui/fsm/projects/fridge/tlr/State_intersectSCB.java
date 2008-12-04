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
package de.uniol.ui.fsm.projects.fridge.tlr;

import de.uniol.ui.fsm.model.states.State;

public class State_intersectSCB extends State {

	Extension_TLR tlr;

	public State_intersectSCB(Extension_TLR fsm) {
		super("intersectSCB", fsm, null);
		this.tlr = fsm;
	}

	protected void entryAction() {
		tlr.setTau_b(tlr.getTau_preload() - tlr.getBc().getTauCooling());
		if (tlr.getTau_b() <= 0) {
			double Tallowed = tlr.getBc().getTmax()
					+ (tlr.getBc().ac() * (-tlr.getTau_b()));
			tlr.setT_allowed(Tallowed);
			double TCurrent = tlr.getBc().getFridge().getTemperature();
			if (TCurrent <= Tallowed) {
				tlr.setTau_gamma_scb(tlr.calculate_tau_gamma_scb());
			} else {
				tlr
						.setT_activ(tlr.TAfter(true, tlr.getTau_preload(),
								TCurrent));
			}
		}
	}

	protected void exitAction() {
		// no-op
	}
}