package Lab01;

public class Task {

    private double startTime;
    private double finishTime;
    private double arrivingOnProcessorTime;
    private double solutionTime;
    private double solutionLeftTime;
    private double relevance;
    private double relevanceHigh = 3;
    private double relevanceLow = 4;

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

    boolean finish(){
        solutionLeftTime = 0.0;
        return true;
    }

    public void setFinishTime(double finishTime) {
        this.finishTime = finishTime;
    }

    public void setArrivingOnProcessorTime(double time) {

        arrivingOnProcessorTime = time;
    }

    public double getSystemResponseTime(){
        return arrivingOnProcessorTime - startTime;
    }

    public double getTimeInSystem(){
        return finishTime - startTime;
    }

    public double getRelevanceOfTask(){
        if ((finishTime - startTime) < relevanceHigh){
            relevance = 1.0;
        } else if (relevanceHigh < (finishTime - startTime) || (finishTime - startTime) < relevanceLow){
            relevance = -(finishTime - startTime) + 3;
        } else {
            relevance = -1.0;
        }

        return relevance;
    }
}