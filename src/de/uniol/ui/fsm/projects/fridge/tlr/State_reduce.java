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

public class State_reduce extends State {

	Extension_TLR tlr;

	public State_reduce(Extension_TLR fsm) {
		super("reduce", fsm, null);
		this.tlr = fsm;
	}

	protected void entryAction() {
		boolean A = false;
		boolean C1 = false;
		boolean CC1 = false;
		boolean BA = false;
		double TCurrent = tlr.getBc().getFridge().getTemperature();
		if (tlr.getTau_reduce() <= tlr.getBc().getTauWarming()) {
			tlr.setT_max_act(tlr.getBc().getTmin()
					+ (tlr.getBc().aw() * (tlr.getBc().getTauWarming() - tlr
							.getTau_reduce())));
			tlr.setTau_a(tlr.getTau_preload()
					- ((tlr.getT_max_act() - tlr.getBc().getTmax()) / tlr
							.getBc().ac()));
			double TAllowed_ba = tlr.getBc().getTmax()
					+ (tlr.getBc().ac() * (-tlr.getTau_a()));
			tlr.setTau_c1(tlr.getTau_preload() + tlr.getTau_reduce()
					- tlr.getBc().getTauWarming());
			double TAllowed_cc1 = tlr.getBc().getTmin()
					+ (tlr.getBc().aw() * (-tlr.getTau_c1()));
			if (tlr.getTau_a() <= 0 && TCurrent >= TAllowed_ba) {
				A = true;
				tlr.setT_activ(tlr.TAfter(true, tlr.getTau_preload(), TCurrent));
			} else if (tlr.getTau_b() <= 0 && TCurrent <= TAllowed_cc1) {
				C1 = true;
				tlr.setT_max_red(tlr.TAfter(false, tlr.getTau_preload()
						+ tlr.getTau_reduce(), TCurrent));
			} else {
				double tau_gamma_scc1 = tlr.calculate_tau_gamma_scc1();
				tlr.setTau_gamma_scc1(tau_gamma_scc1);
				CC1 = 0 <= tau_gamma_scc1 && tau_gamma_scc1 <= tlr.getTau_preload();
				double tau_gamma_sba = tlr.calculate_tau_gamma_sba();
				tlr.setTau_gamma_sba(tau_gamma_sba);
				BA = 0 <= tau_gamma_sba && tau_gamma_sba <= tlr.getTau_preload();
				if (CC1 && BA) {
					CC1 = tau_gamma_scc1 < tau_gamma_sba;
					BA = tau_gamma_sba <= tau_gamma_scc1;
				}
			}
		}
		tlr.setA(A);
		tlr.setC1(C1);
		tlr.setCC1(CC1);
		tlr.setBA(BA);
	}

	protected void exitAction() {
		// no-op
	}
}