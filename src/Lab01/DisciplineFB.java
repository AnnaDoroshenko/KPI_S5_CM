package Lab01;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DisciplineFB extends Discipline{

    private final double QUANTA;
    private final int amountQueues = 3;
    private final double INFINITY = Double.POSITIVE_INFINITY;

    private List<LinkedList<Task>> queues = new ArrayList<>();
    private int currentTaskPriority = 0;

    public DisciplineFB(double lambda, double mu, double quanta) {
        super(lambda, mu);

        QUANTA = quanta;

        for (int i = 0; i < amountQueues; i++) {
            queues.add(new LinkedList<>());
        }

        finishedTasks = new ArrayList<>();
    }

    public List<Task> simulateDisciplineFB(int tasksToSimulate) {
        double T;
        double t1 = 0.0;
        double t2 = INFINITY;
        int alreadySimulatedTasks = 0;

        while (alreadySimulatedTasks < tasksToSimulate) {
            T = findMin(t1, t2);

            if (isT1Min(t1, t2)) {
                final double solutionTime = generateSolutionTime(MU);

                Task task = new Task(T, solutionTime);
                alreadySimulatedTasks++;

                if (isProcessorBusy()) {
                    queues.get(0).add(task);
                } else {
                    task.setArrivingOnProcessorTime(T);
                    taskOnProcessor = task;

                    final double processingTime = findMin(T, QUANTA);

                    t2 = T + processingTime;
                }
                t1 = T + generateSolutionTime(LAMBDA);
            } else {
                final boolean isTaskFinished = (currentTaskPriority == amountQueues - 1) ?
                        taskOnProcessor.finish() : taskOnProcessor.processingOfTask(QUANTA);

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

                    t2 = T + ((currentTaskPriority == amountQueues - 1) ?
                            taskOnProcessor.getSolutionLeftTime() :
                            findMin(taskOnProcessor.getSolutionLeftTime(), QUANTA));
                }
            }
        }

        return finishedTasks;
    }

    private Task getTaskFromQueueAndSetPriority() {
        Task taskFromQueue = null;

        for (int i = 0; i < amountQueues; i++) {
            if (queues.get(i).size() > 0) {
                taskFromQueue = queues.get(i).removeFirst();
                currentTaskPriority = i;
                break;
            }
        }

        return taskFromQueue;
    }
}
