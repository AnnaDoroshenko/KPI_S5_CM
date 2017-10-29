package Lab01;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DisciplineFB {

    private final double LAMBDA;
    private final double MU;
    private final double QUANTA;
    private Task taskOnProcessor = null;
    private final int amountQueues = 3;
    private final double INFINITY = Double.POSITIVE_INFINITY;

    private List<LinkedList<Task>> queues = new ArrayList<>();
    private List<Task> finishedTasks;
    private int currentTaskPriority = 0;

    public DisciplineFB(double lambda, double mu, double quanta){
        LAMBDA = lambda;
        MU = mu;
        QUANTA = quanta;

        for (int i = 0; i < amountQueues; i++){
            queues.add(new LinkedList<>());
        }

        finishedTasks = new ArrayList<>();
    }

    public List<Task> simulateDisciplineFB(int tasksToSimulate){
        double T;
        double t1 = 0.0;
        double t2 = INFINITY;
        int alreadySimulatedTasks = 0;

        while(alreadySimulatedTasks < tasksToSimulate){
            T = findMin(t1, t2);

            if(isT1Min(t1, t2)){
                double solutionTime = generateSolutionTime(MU);

                Task task = new Task(T, solutionTime);
                alreadySimulatedTasks++;

                if(isProcessorBusy()){
                    queues.get(0).add(task);
                } else{
                    task.setArrivingOnProcessorTime(T);
                    taskOnProcessor = task;

                    double processingTime = findMin(T, QUANTA);

                    t2 = T + ((currentTaskPriority == amountQueues - 1) ? taskOnProcessor.getSolutionLeftTime() : processingTime);
                }
                t1 = T + generateSolutionTime(LAMBDA);
            } else {
                boolean isTaskFinished = (currentTaskPriority == amountQueues - 1) ? taskOnProcessor.finish() : taskOnProcessor.processingOfTask(QUANTA);

                if (isTaskFinished) {
                    taskOnProcessor.setFinishTime(T);
                    finishedTasks.add(taskOnProcessor);
                } else {
                    queues.get(currentTaskPriority + 1).add(taskOnProcessor);
                }

                taskOnProcessor = getTaskFromQueueAndSetPriority();

                if (taskOnProcessor == null) {
                    t2 = INFINITY;
                } else {
                    taskOnProcessor.setArrivingOnProcessorTime(T);

                    double timeOnProcessor = findMin(taskOnProcessor.getSolutionLeftTime(), QUANTA);

                    t2 = T + ((currentTaskPriority == amountQueues - 1) ? taskOnProcessor.getSolutionLeftTime() : timeOnProcessor);
                }
            }
        }

        return finishedTasks;
    }

    private Task getTaskFromQueueAndSetPriority(){
        Task taskFromQueue = null;

        for (int i = 0; i < amountQueues; i++){
            if(queues.get(i).size() > 0){
                taskFromQueue = queues.get(i).removeFirst();
                currentTaskPriority = i;
                break;
            }
        }

        return taskFromQueue;
    }

    double findMin(double t1, double t2){
        return t1 < t2 ? t1 : t2;
    }

    boolean isT1Min(double t1, double t2){
        return t1 < t2;
    }

    double generateSolutionTime(double intensity){
        return (-1.0 / intensity * Math.log(Math.random()));
    }

    boolean isProcessorBusy(){
        return taskOnProcessor != null;
    }
}
