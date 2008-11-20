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

import de.uniol.ui.fsm.model.states.State;

public class State_intersectSCC1 extends State {

	Extension_TLR tlr;

	public State_intersectSCC1(Extension_TLR fsm) {
		super("intersectSCC1", fsm, null);
		this.tlr = fsm;
	}

	protected void entryAction() {
		tlr.setTau_c1(tlr.getTau_preload() + tlr.getTau_reduce()
				- tlr.getBc().getTauWarming());
		if (tlr.getTau_c1() <= 0) {
			tlr.setT_allowed(tlr.getBc().getTmin()
					+ (tlr.getBc().aw() * (-tlr.getTau_c1())));
		}
	}

	protected void exitAction() {
		// no-op
	}
}