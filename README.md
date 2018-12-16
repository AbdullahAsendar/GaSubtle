# GaSubtle - Generate Subtle Higher Order Mutants

GaSubtle is based on Java that uses genatic algorithm to generate subtle higher order mutants. For more details check our [website](https://abdullahasendar.github.io/GaSubtle/).

## Prerequisite

### PC
You will need a PC with a minimum of Core-i5 CPU and a 4 GBs of RAM.

### Java SE Development Kit 8
GaSubtle is based on Java 8, so in order to run, compile and build the tool Java SE Development Kit 8 is required.

### IDE
To edit the code you will need a Java IDE (ex. Eclipse, NetBeans).

### Project Lombok
Project Lombok is a java library that automatically plugs into your editor and build tools, spicing up your java. Never write another getter or equals method again. It is used to generate constructors, setter, getters, build methods, etc. using annotations only.

Download Project Lombok and run the downloaded file. It will ask you to locate you IDE installation directory. Press install and restart your IDE.
 
## Setup
 - Download [Lombok](https://projectlombok.org/) and open the downloaded file.
 - Locate your IDE and press install.
 - Restart your IDE.
 - Import the project to youe IDE as Maven project.
 
## How To Use The API



		GeneticAlgorithm
		.builder()
		.mutationPercentage(10) \\ the mutation percentage
		.maxOrder(5) \\ max order of the HOMs not to be exceeded
		.runRepeat(1) \\ how many time to run the algorithm [for benchmarking]
            \\ stopping conditions, at least one should be provided
		.requiredSubtleHoms(1000) \\ stopping condition: minimum subtle HOMs needed
		.maxHoms(1500) \\ stopping condition: maximum HOMs to generate
		.maxGeneration(300) \\ stopping condition: maximum generation to reach
		.timeout(1000) \\ stopping condition: timeout in seconds
		.originalFile("/some/.java/file") \\ path to the original file
		.testCasesPath("/some/.class/test/path") \\ path to test cases
		.resultPath("/some/path") \\ path to store the results in
		.mutantsPath("/some/path") \\ path to store the mutants in
		.evaluation(new EvaluationDefaultImpl())
		.evaluation(new EvaluationDefaultImpl())
		.crossover(new CrossoverDefaultImpl())
		.mutation(new MutationDefaultImpl())
		.selection(selection)
		.messageListener(new MessageListener() {
			
			@Override
			public void info(String value) {
                            System.out.println("Info message from the GA is: "+ value);
			}
			
			@Override
			public void error(String value) {
			    System.out.println("There was an error:" + value);
			}
			
			@Override
			public void debug(String value) {
			    System.out.println("Debugging: "+ value);
			}
		})
		.geneticAlgorithmListener((int generation, int populationSize, int liveMutants, int subtleMutants)
				-> LOG.info(String.format("Generation [%s] Population [%s] Live Mutants [%s] Subtle Mutants [%s]",
						generation, populationSize, liveMutants, subtleMutants)))
				.build()
		.run();

## Build
You can build the project by executing `mvn clean install`

## Launch
You can run the tool by executing `java -jar GaSubtle-x.x.x-SNAPSHOT.jar`

## License
GNU General Public License v3.0
