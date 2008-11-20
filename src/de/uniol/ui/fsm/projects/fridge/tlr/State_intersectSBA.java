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

public class State_intersectSBA extends State {

	Extension_TLR tlr;

	public State_intersectSBA(Extension_TLR fsm) {
		super("intersectSBA", fsm, null);
		this.tlr = fsm;
	}

	protected void entryAction() {
		tlr.setT_max_act(tlr.getBc().getTmin()
				+ (tlr.getBc().aw() * (tlr.getBc().getTauWarming() - tlr
						.getTau_reduce())));
		tlr.setTau_a(tlr.getTau_preload()
				- ((tlr.getT_max_act() - tlr.getBc().getTmax()) / tlr.getBc()
						.ac()));
		if (tlr.getTau_a() <= 0) {
			tlr.setT_allowed(tlr.getBc().getTmax()
					+ (tlr.getBc().ac() * (-tlr.getTau_a())));
			double tCurrent = tlr.getBc().getFridge().getTemperature();
			if (tCurrent > tlr.getT_allowed()) {
				tlr
						.setT_activ(tlr.TAfter(true, tlr.getTau_preload(),
								tCurrent));
			}
		}
	}

	protected void exitAction() {
		// no-op
	}
}