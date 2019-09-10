package sim.app.emas;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

public class ActionResolutionAgent implements Steppable {
     private int id;

     ActionResolutionAgent(int id) {
         this.id = id;
     }

    @Override
    public void step(SimState state) {
        Island island = (Island) state;

        System.out.println("Dead agents: " + island.dead.size());
        resolveDeads(island);
        resolveProcreation(island);
        resolveMeetings(island);
        printBestAgent(island.agents);

        System.out.println("Agents in system: " + island.agents.size());
        System.out.println(Colors.ANSI_GREEN + "========================= BEGIN TURN " + (island.schedule.getTime()+1.0) + " =========================" + Colors.ANSI_RESET);
     }

     private void resolveDeads(Island island) {
         for (UUID deadId : island.dead) {
             island.stopHandles.remove(deadId).stop();
             island.agents.remove(deadId);
         }
         island.dead.clear();
     }

     private void resolveProcreation(Island island){
         System.out.println(">>> Resolving procreations");
         ArrayList<UUID> procreating = island.procreating;
         if (procreating.size() % 2 == 1) {
             procreating.remove(0);
             if (procreating.size() == 0) {
                 System.out.println("There was only one agent that wanted to procreate");
             }
         }
         System.out.println(procreating.size());
         Iterator<UUID> itr = procreating.iterator();
         while (itr.hasNext()) {
             Agent agent1 = island.agents.get(itr.next());
             Agent agent2 = island.agents.get(itr.next());

             Agent newAgent = agent1.procreate(agent2);

             island.agents.put(newAgent.getId(), newAgent);
             Stoppable stopHandle = island.schedule.scheduleRepeating(newAgent);
             island.stopHandles.put(newAgent.getId(), stopHandle);
         }
         island.procreating.clear();
     }

     private void resolveMeetings(Island island){
         System.out.println(">>> Resolving meetings");
         ArrayList<UUID> meeting = island.meeting;
         if (meeting.size() % 2 == 1) {
             meeting.remove(0);
             if (meeting.size() == 0) {
                 System.out.println("There was only one agent that wanted to meet");
             }
         }
         System.out.println(meeting.size());
         Iterator<UUID> itr = meeting.iterator();
         while (itr.hasNext()) {
             Agent agent1 = island.agents.get(itr.next());
             Agent agent2 = island.agents.get(itr.next());
             agent1.meet(agent2);
         }
         island.meeting.clear();
     }

     private void resolveMigrations(){}


     //---------------------------------------------------------------------------------------
     private void printBestAgent(HashMap<UUID, Agent> agents) {
         Agent bestAgent = (Agent) agents.values().toArray()[0];
         for (Agent agent : agents.values()) {
             if (agent.fitness >= bestAgent.fitness){
                 bestAgent = agent;
             }
         }
         bestAgent.print();
     }
}
