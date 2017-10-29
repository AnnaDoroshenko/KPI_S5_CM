package Lab01;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DisciplineFB {

    private final double LAMBDA;
    private final double MU;
    private final double QUANTA;
    private int TASKS_TO_SIMULATE;
    private Task taskOnProcessor = null;
    private final int amountQueues = 3;
    private final double INFINITY = Double.POSITIVE_INFINITY;

    private LinkedList<Task>[] queues = new LinkedList[amountQueues];
    private List<Task> finishedTasks;
    private int currentTaskPriority = 0;

    public DisciplineFB(double lambda, double mu, double quanta){
        LAMBDA = lambda;
        MU = mu;
        QUANTA = quanta;
    }

    public List<Task> simulateDisciplineFB(int tasksToSimulate){
        TASKS_TO_SIMULATE = tasksToSimulate;
        double T;
        double t1 = 0.0;
        double t2 = INFINITY;
        int alreadySimulatedTasks = 0;

        while(alreadySimulatedTasks < TASKS_TO_SIMULATE){
            T = findMin(t1, t2);

            if(isT1Min(t1, t2)){
                double solutionTime = generateSolutionTime(MU);

                Task task = new Task(T, solutionTime);
                alreadySimulatedTasks++;

                if(isProcessorBusy()){
                    queues[currentTaskPriority].add(task);
                } else{
                    task.setArrivingOnProcessorTime(T);
                    taskOnProcessor = task;

                    double processingTime = findMin(T, QUANTA);
                    t2 = T + processingTime;
                }
                t1 = T + generateSolutionTime(LAMBDA);
            } else {
                for (int i = 0; i < amountQueues; i++) {
                    boolean isTaskFinished = taskOnProcessor.processingOfTask(QUANTA);

                    if (isTaskFinished) {
                        taskOnProcessor.setFinishTime(T);
                        finishedTasks.add(taskOnProcessor);
                    } else {
                        queues[++currentTaskPriority].add(taskOnProcessor);
                    }
                }
            }

            taskOnProcessor = getTaskFromQueue();

            if (taskOnProcessor == null){
                t2 = INFINITY;
            } else {
                taskOnProcessor.setArrivingOnProcessorTime(T);

                double timeOnProcessor = findMin(taskOnProcessor.getSolutionLeftTime(), QUANTA);
                t2 = T + timeOnProcessor;
            }
        }

        return finishedTasks;
    }

//    int getQueuePriority(LinkedList<Task> queue){
//        return Arrays.asList(queues).indexOf(queue);
//    }

    private Task getTaskFromQueue(){
        Task taskFromQueue = null;

        for (int i = 0; i < amountQueues; i++){
            if(queues[i].size() > 0){
                taskFromQueue = queues[i].removeFirst();
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
