# Mason

This is a evolutionary multi-agent simulation (EMAS) for computational purposes modelled in the MASON agent framework (https://cs.gmu.edu/~eclab/projects/mason/). This is an implementation for a specific problem: calculating the global optimum for an n-dimentional Rastrigin function (https://en.wikipedia.org/wiki/Rastrigin_function), although any such function may be substituted into the program. The closer the agent fitness is to 0 (which is the global optimum in the case of any Rastrigin function), the better.

## Program arguments
There are three arguments, provided in this order:
- function dimensionality,
- number of independent threads of computation,
- number of turns in the simulation

## Run
To see how this works, download the repository and use the provided jar file.
For example:
```java
git clone https://github.com/erccpl/mason.git
cd mason
java -cp mason_jar.jar pl.edu.agh.mason.Simulation 4 2 1000
```
