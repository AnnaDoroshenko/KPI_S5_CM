package Lab01;

public class Task {

    private double startTime;
    private double finishTime;
    private double arrivingOnProcessorTime;
    private double solutionTime;
    private double solutionLeftTime;

    public Task(double startTime, double solutionTime){
        this.startTime = startTime;
        this.solutionTime = solutionTime;
        this.solutionLeftTime = solutionTime;
    }

    public double getSolutionTime() {
        return solutionTime;
    }

    public double getSolutionLeftTime() {
        return solutionLeftTime;
    }

    boolean processingOfTask(double time){
        solutionLeftTime -= time;
        if(solutionLeftTime <= 0.0){
            solutionLeftTime = 0.0;
            return true;
        }

        return false;
    }

    void finishedTask(double finishTime){
        solutionLeftTime = 0.0;
        this.finishTime = finishTime;
    }

    public void setFinishTime(double finishTime) {
        this.finishTime = finishTime;
    }

    public void setArrivingOnProcessorTime(double time) {

        arrivingOnProcessorTime = time;
    }

    public double getTimeInQueue(){
        return arrivingOnProcessorTime - startTime;
    }

    public double getTimeinSystem(){
        return finishTime - startTime;
    }


}