package sim.app.emas;

import sim.engine.SimState;
import sim.engine.Stoppable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

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

    HashMap<UUID, Agent> agents = new HashMap<>();
    HashMap<UUID, Stoppable> stopHandles = new HashMap<>();

    ArrayList<UUID> dead = new ArrayList<>();
    ArrayList<UUID> procreating = new ArrayList<>();
    ArrayList<UUID> meeting = new ArrayList<>();
    ArrayList<UUID> migrating = new ArrayList<>();


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
