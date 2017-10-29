package Lab01;

import java.util.List;

public class Discipline {

    protected final double LAMBDA;
    protected final double MU;
    protected Task taskOnProcessor;
    protected List<Task> finishedTasks;

    public Discipline(double lambda, double mu){
        LAMBDA = lambda;
        MU = mu;
        taskOnProcessor = null;
    }

    double findMin(double t1, double t2) {
        return t1 < t2 ? t1 : t2;
    }

    boolean isT1Min(double t1, double t2) {
        return t1 < t2;
    }

    double generateSolutionTime(double intensity) {
        return (-1.0 / intensity * Math.log(Math.random()));
    }

    boolean isProcessorBusy() {
        return taskOnProcessor != null;
    }
}
