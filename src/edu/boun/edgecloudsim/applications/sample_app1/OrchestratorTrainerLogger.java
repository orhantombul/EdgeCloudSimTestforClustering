package edu.boun.edgecloudsim.applications.sample_app1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cloudbus.cloudsim.core.CloudSim;

import edu.boun.edgecloudsim.core.SimManager;
import edu.boun.edgecloudsim.core.SimSettings;
import edu.boun.edgecloudsim.edge_client.Task;
import edu.boun.edgecloudsim.edge_server.EdgeVM;
import edu.boun.edgecloudsim.utils.SimLogger;

public class OrchestratorTrainerLogger {
    private static final String DELIMITER = ",";
    private Map<Integer, TrainerItem> trainerMap;

    private BufferedWriter learnerBW = null;

    public class TrainerItem {
        double processingTime;
        double networkDelay;

        TrainerItem(double processingTime,
                    double networkDelay)

        {
            this.processingTime = processingTime;
            this.networkDelay = networkDelay;

        }
    }

    public OrchestratorTrainerLogger() {
        trainerMap = new HashMap<Integer, TrainerItem>();
    }

    public void openTrainerOutputFile() {
        try {
            int numOfMobileDevices = SimManager.getInstance().getNumOfMobileDevice();
            String learnerOutputFile = SimLogger.getInstance().getOutputFolder() +
                    "/" + numOfMobileDevices + "_learnerOutputFile.cvs";
            File learnerFile = new File(learnerOutputFile);
            FileWriter learnerFW = new FileWriter(learnerFile);
            learnerBW = new BufferedWriter(learnerFW);

            String line = "Task Id"
                    + DELIMITER+"Service Time"
                    + DELIMITER + "Network Delay"
                    + DELIMITER + "Processing Time"
                    + DELIMITER + "WLAN Delay"
                    + DELIMITER + "Failed WM Capacity"
                    + DELIMITER + "Failed Edge"
                    + DELIMITER + "Failed Mobility"
                    + DELIMITER + "App Type"
                   ;

            //for(int i=1; i<=SimSettings.getInstance().getTaskLookUpTable().length; i++)
            //	line += DELIMITER + "Avg Edge(" + i + ") Utilization";

            learnerBW.write(line);
            learnerBW.newLine();

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
    public void closeTrainerOutputFile() {
        try {
            learnerBW.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }


    public void addStat(double processingTime, double networkDelay){

        int numberOfHost = SimSettings.getInstance().getNumOfEdgeHosts();
        double totalUtlization = 0;
        double[] edgeUtilizations = new double[numberOfHost];
        for(int hostIndex=0; hostIndex<numberOfHost; hostIndex++){
            List<EdgeVM> vmArray = SimManager.getInstance().getEdgeServerManager().getVmList(hostIndex);

            double utilization=0;
            for(int vmIndex=0; vmIndex<vmArray.size(); vmIndex++){
                utilization += vmArray.get(vmIndex).getCloudletScheduler().getTotalUtilizationOfCpu(CloudSim.clock());
            }
            totalUtlization += utilization;

            edgeUtilizations[hostIndex] = utilization / (double)(vmArray.size());
        }

        double avgEdgeUtilization = totalUtlization / SimSettings.getInstance().getNumOfEdgeVMs();

        Integer id = 1;
        trainerMap.put(id,
                new TrainerItem(processingTime,networkDelay)
        );

    }
  /*

    public synchronized void addSuccessStat(Task task, double serviceTime) {
        TrainerItem trainerItem = trainerMap.remove(task.getCloudletId());
        saveStat(trainerItem);

    }

    public synchronized void addFailStat(Task task) {
        TrainerItem trainerItem = trainerMap.remove(task.getCloudletId());
        saveStat(trainerItem);
    }

  */
}