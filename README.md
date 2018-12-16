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



            // create a config object that defines settings and termination conditions for the algorithm
            Config config = Config
            				.builder()
            				.classPath("/path/to/junit-4/and/hamcrest")
            				.maxOrder(5) // the algorithm will not generate HOMs higher than this order
            				.maxGeneration(100) // the algorithm will terminate when it reaches this generation
            				.maxHoms(1000) // the algorithm will terminate when it reaches this number of HOMs
            				.requiredSubtleHoms(1000) // the algorithm will terminate when it reaches this number of subtle HOMs
            				.timeout(500) // tineout seconds
            				.mutationPercentage(5) // this number will be used for selection, ex (if the generation size is 100 and the mutation percentage is 5 the algorithm will use 5 mutants for operations)
            				.originalFile(/path/to/original/.java/file)
            				.populationSize(100) // the population will not exceed this number
            				.resultPath("/path/to/export/results/to")
            				.runRepeat(2) // how many times should the algorithm run
            				.testCasesPath("/path/to/test/cases")
            				.build();
            		
            		GeneticAlgorithm geneticAlgorithm = GeneticAlgorithm
            		.builder() // create new instance
            		.config(config) // set the config
            		.selectionStrategy(new RouletteWheelSelection()) // selection strategy [you can create your own by implementing SelectionStrategy]
            		.evaluation(new Evaluation()) // evaluation strategy [you can create your own by implementing AbstractOperation]
            		.operation(new Crossover()) // operations, also impelements AbstractOperation
            		.operation(new Mutation())
            		.messageListener(messageListener) // message listener
            		.geneticAlgorithmListener(gaListener) // GA listener
            		.build();

## Build
You can build the project by executing `mvn clean install`

## Launch
You can run the tool by executing `java -jar GaSubtle-x.x.x-SNAPSHOT.jar`

## License
GNU General Public License v3.0
