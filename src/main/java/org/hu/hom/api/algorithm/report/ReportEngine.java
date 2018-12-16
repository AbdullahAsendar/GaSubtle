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
package org.hu.hom.api.algorithm.report;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.hu.hom.api.algorithm.GeneticAlgorithm;
import org.hu.hom.core.object.AbstractMutant;
import org.hu.hom.core.object.Population;
import org.hu.hom.core.utils.ExcelWriter;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;

import lombok.NoArgsConstructor;



/**
 * 
 * <p>
 * Generates excel reports of the run results
 * 
 * @author Asendar
 *
 * @param <T> The type of entity that we are reporting.
 */
@NoArgsConstructor(staticName = "build")
public class ReportEngine<T extends AbstractMutant> {


	private List<Record<T>> records = Lists.newArrayList();


	/**
	 * @param generationNumber reached
	 * @param population of current generation
	 * @param mutants generated so far
	 * @param liveMutants generated so far
	 * @param subtleMutants generated so far
	 */
	public void record(int generationNumber, Population<T> population, Set<T> mutants, Set<T> liveMutants,
			Set<T> subtleMutants) {
		record(Record.build(generationNumber, population.copy(), Lists.newArrayList(mutants),
				Lists.newArrayList(liveMutants), Lists.newArrayList(subtleMutants)));
	}

	/**
	 * @param record to be stored
	 */
	public void record(Record<T> record) {
		records.add(record);
	}
	
	
	/**
	 * @param outputDir to save the report in
	 * @param maxOrder not to be exceeded
	 * @param stopwatch of the {@link GeneticAlgorithm}
	 */
	public void export(File outputDir, int maxOrder, Stopwatch stopwatch) {
		File file = new File(String.format("%s%s%s", outputDir.getPath(), File.separator, "report.xlsx"));

		List<List<Object>> rows = Lists.newArrayList();

		List<Integer> degrees = getDegrees(maxOrder);
		List<String> degreeTitles = getDegreeTitles(maxOrder);
		
		List<Object> headers = Lists.newArrayList();
		
		headers.addAll(Lists.newArrayList("Generation #", "Generated Mutants","non-compilabe mutants", "Population Size"));
		headers.addAll(degreeTitles);
		headers.add("# Live Mutants");
		headers.addAll(degreeTitles);
		headers.add("# Subtle Mutants");
		headers.addAll(degreeTitles);
		
		rows.add(headers);
		
		records.forEach(record -> {
			
			List<Integer> mutantsDegrees = Lists.newArrayList();
			List<Integer> liveMutantsDegrees = Lists.newArrayList();
			List<Integer> subtleMutantsDegrees = Lists.newArrayList();
			
			for (int degree : degrees) {
				mutantsDegrees.add((int) record.getPopulation().getMutants().stream().filter(mutant -> mutant.getOrder() == degree).count());
				liveMutantsDegrees.add((int) record.getLiveMutants().stream().filter(mutant -> mutant.getOrder() == degree).count());
				subtleMutantsDegrees.add((int) record.getSubtleMutants().stream().filter(mutant -> mutant.getOrder() == degree).count());
			}
			
			List<Object> row = Lists.newArrayList();
			
			row.addAll(Lists.newArrayList(
					record.getGenerationNumber(), 
					record.getGeneratedMutants().size(),
					record.getPopulation().getMutants().stream().filter(AbstractMutant::isNonCompiled).collect(Collectors.toList()).size(),
					record.getPopulation().getMutants().size()));
			row.addAll(mutantsDegrees);
			row.add(record.getLiveMutants().size());
			row.addAll(liveMutantsDegrees);
			row.add(record.getSubtleMutants().size());
			row.addAll(subtleMutantsDegrees);

			
			rows.add(row);
			
		});
		
		
		rows.add(Lists.newArrayList(""));
		rows.add(Lists.newArrayList("Total Time:", stopwatch.elapsed(TimeUnit.MINUTES) + " MINUTES"));

		try {
			ExcelWriter.export("Report", rows, file);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private List<Integer> getDegrees(int maxDegree) {

		List<Integer> values = Lists.newArrayList();

		for (int i = 1; i <= maxDegree; i++)
			values.add(i);

		return values;

	}
	
	private List<String> getDegreeTitles(int maxDegree) {

		List<String> values = Lists.newArrayList();

		for (int i = 1; i <= maxDegree; i++)
			values.add(String.format("Order %s", i));

		return values;

	}
}
