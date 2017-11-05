package Lab01;

import java.util.ArrayList;
import java.util.List;

public class DisciplineSF extends Discipline {

    private List<Task> queue = new ArrayList<>();

    public DisciplineSF(double lambda, double mu) {
        super(lambda, mu);
    }

    public List<Task> simulateDisciplineSF(int tasksToSimulate) {
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

                    t2 = T + taskOnProcessor.getSolutionTime();
                }
                t1 = T + generateSolutionTime(LAMBDA);
            } else {
                taskOnProcessor.setFinishTime(T);
                finishedTasks.add(taskOnProcessor);

                taskOnProcessor = getTaskFromQueue();

                if (taskOnProcessor == null) {
                    t2 = INFINITY;
                } else {
                    taskOnProcessor.setSystemResponseTime(T);

                    t2 = T + taskOnProcessor.getSolutionTime();
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
            double currentTheSmallestSolutionTime = queue.get(0).getSolutionTime();
            for (Task task : queue) {
                double currentTaskSolutionTime = task.getSolutionTime();
                if (currentTaskSolutionTime < currentTheSmallestSolutionTime) {
                    currentTheSmallestSolutionTime = currentTaskSolutionTime;
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

