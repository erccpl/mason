package pl.edu.agh.mason;

import sim.engine.SimState;

import java.util.ArrayList;

public class Simulation {
    public static void main(String[] args) {
        if (args.length != 2){
            System.out.println("Program parameters: <islands> <turns>");
        }
        long time = System.currentTimeMillis();

        int islandNum = Integer.parseInt(args[0]);
        int turns = Integer.parseInt(args[1]);
        ArrayList<Island> islands = new ArrayList<>();

        // Create islands
        for (int i = 0; i < islandNum; i++) {
            islands.add(new Island(time, i));
        }

        //Provide islands with reference to all other islands
        for (Island island : islands) {
            island.setIslandRef(islands);
        }

        // Start islands
        for(SimState island : islands) {
            island.start();
        }

        //Run simulation
        int counter = 0;
        while(counter < turns) {
            for(SimState island : islands) {
                island.schedule.step(island);
            }
            counter++;
        }

        // Finish
        for(SimState island : islands) {
            island.finish();
        }

        // Exit
        System.exit(0);
    }
}
