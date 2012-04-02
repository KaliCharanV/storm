package backtype.storm.scheduler;

import java.util.List;
import java.util.Map;

public interface INimbus {
    void prepare(Map stormConf);
    //used slots are slots that are currently assigned and haven't timed out
    // mesos should:
    //   1. if some slots are used, return as much as it currently has available
    //   2. otherwise return nothing until it has enough slots, or enough time has passed
    // sets the node id as {normalized hostname (invalid chars removed}-{topologyid}
    List<WorkerSlot> availableSlots(List<SupervisorInfo> existingSupervisors, List<WorkerSlot> usedSlots, TopologyInfo topology);
    // mesos should call launchTasks on an executor for this topology... 
    // gives it the executor with:
    //   - name: the node id
    // set the task id to {nodeid-port}
    // this should be called after the assignment is changed in ZK
    void assignSlot(WorkerSlot slot, TopologyInfo topology);
    // call kill task on task id {nodeid-port}
    // this should be called before the assignment is changed in ZK
    // this is also called when a topology is killed
    void removeSlot(WorkerSlot slot, TopologyInfo topology);
}
