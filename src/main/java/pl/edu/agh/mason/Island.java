package pl.edu.agh.mason;

import sim.engine.SimState;
import sim.engine.Stoppable;

import java.util.*;

public class Island extends SimState {

    public Island(long seed) {
        super(seed);
    }

    AgentConfig config = new AgentConfig(
            4,
            100,
            true,
            0.2,
            50,
            0.5,
            40,
            -5.12,
            5.12
    );    private int agentNum = 100;

    Map<UUID, Agent> agents = new HashMap<>();
    Map<UUID, Stoppable> stopHandles = new HashMap<>();

    List<UUID> dead = new ArrayList<>();
    List<UUID> procreating = new ArrayList<>();
    List<UUID> meeting = new ArrayList<>();
    List<UUID> migrating = new ArrayList<>();


    public void start() {
        super.start();

        for (int i = 1; i <= agentNum; i++) {
            UUID id = UUID.randomUUID();
            Agent agent = new Agent(id, config);
            Stoppable stopHandle = schedule.scheduleRepeating(agent);

            this.stopHandles.put(id, stopHandle);
            this.agents.put(id, agent);
        }

        ActionResolutionAgent resolutionAgent = new ActionResolutionAgent(0);
        System.out.println("Created procreation agent");
        schedule.addAfter(resolutionAgent);
    }

    public static void main(String[] args) {
        doLoop(Island.class, args);
        System.exit(0);
    }

}
