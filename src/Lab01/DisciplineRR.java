package Lab01;

import java.util.LinkedList;
import java.util.List;

public class DisciplineRR extends Discipline {

    private final double QUANTA;

    private LinkedList<Task> queue = new LinkedList<>();

    public DisciplineRR(double lambda, double mu, double quanta) {
        super(lambda, mu);
        QUANTA = quanta;
    }

    public List<Task> simulateDisciplineRR(int tasksToSimulate) {
        final double INFINITY = Double.POSITIVE_INFINITY;
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
                    queue.add(task);
                } else {
                    task.setSystemResponseTime(T);
                    taskOnProcessor = task;

                    final double processingTime = findMin(taskOnProcessor.getSolutionTime(), QUANTA);

                    t2 = T + processingTime;
                }
                t1 = T + generateSolutionTime(LAMBDA);
            } else {
                final boolean isTaskFinished = taskOnProcessor.processingOfTask(QUANTA);

                if (isTaskFinished) {
                    taskOnProcessor.setFinishTime(T);
                    finishedTasks.add(taskOnProcessor);
                } else {
                    queue.add(taskOnProcessor);
                }

                taskOnProcessor = getTaskFromQueue();

                if (taskOnProcessor == null) {
                    t2 = INFINITY;
                } else {
                    taskOnProcessor.setSystemResponseTime(T);

                    final double timeOnProcessor = findMin(taskOnProcessor.getSolutionLeftTime(), QUANTA);
                    t2 = T + timeOnProcessor;
                }
            }
        }

        return finishedTasks;
    }

    private Task getTaskFromQueue() {
        Task taskFromQueue = null;

        if (queue.size() > 0) {
            taskFromQueue = queue.removeFirst();
        }

        return taskFromQueue;
    }
}