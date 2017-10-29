package Lab01;

public class Model {

    private final double LAMBDA;
    private final double MU;
    private int TASKS_TO_SIMULATE;
    private double T = 0.0; // model time
    private double t1 = 3.0; // new task entrance time
    private double t2 = 4.0; // task finish of processing time

    public Model(double lambda, double mu){
        LAMBDA = lambda;
        MU = mu;
    }

    void simulate(int TaskToSimulate){
        TASKS_TO_SIMULATE = TaskToSimulate;
        int simulatedTask = 0;

        T = findMin(t1, t2);


    }

    double findMin(double t1, double t2){
        return t1 < t2 ? t1 : t2;
    }

    double generateSolutionTime(double mu){
        return (-1.0 / mu * Math.log(Math.random()));
    }
}
