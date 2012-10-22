/*
 *    This file is part of libalexandria.
 *
 *    libalexandria is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    libalexandria is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with libalexandria.  If not, see <http://www.gnu.org/licenses/>.
 */
package lib.alexandria.logging;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LinearFormatter extends Formatter {
	private static final String endl;
	static {
		endl = System.getProperty("line.separator", "\n");
	}
	
	private final String prefix;
	
	public LinearFormatter(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public String format(LogRecord record) {
		record.setSourceClassName(prefix);
		return formatMessage(record) + endl;
	}
}
