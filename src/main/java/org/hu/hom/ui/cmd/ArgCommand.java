/**
 *  Copyright (C) 2018  Abdullah Al-Shishani
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 * 
 */
package org.hu.hom.ui.cmd;

import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.hu.hom.api.algorithm.object.impl.selection.Selection;

import com.google.common.collect.Lists;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * <p>
 * Argument commands that can be passed to the tool
 * 
 * @author asendar
 * 
 *
 */
@AllArgsConstructor
public enum ArgCommand {

	HOME("h", "home", "specify the home\ndefault: [${current.path}/hom-ga-home]", false), 
	BENCHMARK("b", "benchmark", "specify benchmark name, directory path should be [${home}/${benchmark}]", true), 
	SELECTION_STRATEGY("s", "selection-strategy", String.format("set selection strategy\nvalues: [%s]\ndefault: use all the selection stratiges", Lists.newArrayList(Selection.values())), false);
	
	private @Getter  String opt;
	private @Getter String longOpt;
	private String description;
	private boolean required;

	/**
	 * @return the {@link Option} that can be used by {@link CommandLine}
	 */
	public Option toOption() {
		Option option = new Option(opt, longOpt, true, description);

		option.setRequired(required);

		return option;
	}

	/**
	 * @return list of {@link Option} that can be used by {@link CommandLine}
	 */
	public static List<Option> options() {
		List<Option> options = Lists.newArrayList();
		Lists.newArrayList(values()).forEach(command -> options.add(command.toOption()));
		return options;
	}

}
