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
package de.uniol.ui.fsm.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;

/**
 * Utility class which performs calcualtions on simulation results and is able
 * to format and write them to files. Simple version, see project "Fridge" for
 * more.
 * 
 * @author <a href=
 *         "mailto:Christian%20Hinrichs%20%3Cchristian.hinrichs@uni-oldenburg.de%3E"
 *         >Christian Hinrichs, christian.hinrichs@uni-oldenburg.de</a>
 * 
 */
public class ResultWriter {

	/* some formatting */
	protected static NumberFormat tf = NumberFormat.getNumberInstance();
	protected static NumberFormat vf = NumberFormat.getNumberInstance();
	static {
		tf.setGroupingUsed(false);
		tf.setMinimumFractionDigits(5);
		tf.setMaximumFractionDigits(5);
		tf.setMinimumIntegerDigits(3);
		tf.setMaximumIntegerDigits(4);
		
		vf.setGroupingUsed(false);
		vf.setMinimumFractionDigits(3);
		vf.setMaximumFractionDigits(3);
		vf.setMinimumIntegerDigits(2);
		vf.setMaximumIntegerDigits(2);
	}

	/**
	 * This method writes the given result values to the named file. Per line
	 * <code>i</code>, there will be:<b>
	 * 
	 * <pre>
	 * temp[0][i] \t temp[1][i] \t load[0][i] \t load[1][i]
	 * </pre>
	 * 
	 * @param temp
	 * @param load
	 * @param file
	 */
	public static void writeResultsSimple(double[][] temp, double[][] load,
			File file) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(file, false);
			// Write values
			for (int i = 0; i < Math.max(temp[0].length, load[0].length); i++) {
				if (i < temp[0].length) {
					fw.write(tf.format(temp[0][i] / 60000d));
					fw.write("\t" + vf.format(temp[1][i]));
				} else {
					fw.write("\t\t\t");
				}
				if (i < load[0].length) {
					fw.write("\t" + tf.format(load[0][i] / 60000d));
					fw.write("\t" + vf.format(load[1][i]));
				}
				fw.write("\n");
			}
			// Close stream
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}