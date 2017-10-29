package Lab01;

import java.util.ArrayList;
import java.util.List;

public class DisciplineSF extends Discipline {

    private final double QUANTA;
    private final double INFINITY = Double.POSITIVE_INFINITY;

    private List<Task> queue = new ArrayList<>();

    public DisciplineSF(double lambda, double mu, double quanta) {
        super(lambda, mu);

        QUANTA = quanta;

        finishedTasks = new ArrayList<>();
    }

    public List<Task> simulateDisciplineSF(int tasksToSimulate) {
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
                    task.setArrivingOnProcessorTime(T);
                    taskOnProcessor = task;

                    final double processingTime = findMin(T, QUANTA);

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
                    taskOnProcessor.setArrivingOnProcessorTime(T);

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
            int index = 0;
            int indexOfTheSmallest = 0;
            double currentTheSmallest = queue.get(0).getSolutionTime();
            for (Task task : queue) {
                double currentTaskSolutionTime = task.getSolutionTime();
                if (currentTaskSolutionTime < currentTheSmallest) {
                    currentTheSmallest = currentTaskSolutionTime;
                    indexOfTheSmallest = index;
                }
                index++;
            }
            taskFromQueue = queue.get(indexOfTheSmallest);
            queue.remove(indexOfTheSmallest);
        }

        return taskFromQueue;
    }
}
