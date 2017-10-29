package Lab01;

import java.util.ArrayList;
import java.util.List;

public class Lab01 {

    public static void main(String[] args) {
        final double LAMBDA = 1.0;
        final double MU = 1.4;
        final double QUANTA = 1.0;
        final int TASK_TO_SIMULATE = 20000;

        {
            double averageTimeInSystem = 0.0;
            double dispersionOfTimeInSystem = 0.0;
            double averageSystemResponseTime = 0.0;
            double totalAssessmentOfRelevance = 0.0;
            double totalAmountOfProcessedTasks;

            DisciplineFB disciplineFB = new DisciplineFB(LAMBDA, MU, QUANTA);
            List<Task> finishedTasks = disciplineFB.simulateDisciplineFB(TASK_TO_SIMULATE);

            averageTimeInSystem += getAverageTimeInSystem(finishedTasks);
            dispersionOfTimeInSystem += getDispersionOfTimeInSystem(finishedTasks, averageTimeInSystem);
            averageSystemResponseTime += getAverageSystemResponseTime(finishedTasks);
            totalAssessmentOfRelevance += getTotalAssessmentOfRelevance(finishedTasks);
            totalAmountOfProcessedTasks = finishedTasks.size();

            System.out.println("FB: " + "\nAverage time in system = " + averageTimeInSystem +
            "\nDispersion of time in system = " + dispersionOfTimeInSystem +
            "\nAverage system response time = " + averageSystemResponseTime +
            "\nTotal assessment Of task relevance = " + totalAssessmentOfRelevance);

            System.out.println("\nTotal amount of processed tasks = " + totalAmountOfProcessedTasks);

            System.out.println("\nBest parameter = " +
                    findBestQuanta(LAMBDA, MU, TASK_TO_SIMULATE, -1, -6, -3, 4));

            System.out.println("-------------------------------------------");
        }

        {
            double averageTimeInSystem = 0.0;
            double dispersionOfTimeInSystem = 0.0;
            double averageSystemResponseTime = 0.0;
            double totalAssessmentOfRelevance = 0.0;

            DisciplineRR disciplineRR = new DisciplineRR(LAMBDA, MU, QUANTA);
            List<Task> finishedTasks = disciplineRR.simulateDisciplineRR(TASK_TO_SIMULATE);

            averageTimeInSystem += getAverageTimeInSystem(finishedTasks);
            dispersionOfTimeInSystem += getDispersionOfTimeInSystem(finishedTasks, averageTimeInSystem);
            averageSystemResponseTime += getAverageSystemResponseTime(finishedTasks);
            totalAssessmentOfRelevance += getTotalAssessmentOfRelevance(finishedTasks);

            System.out.println("RR: " + "\nAverage time in system = " + averageTimeInSystem +
                    "\nDispersion of time in system = " + dispersionOfTimeInSystem +
                    "\nAverage system response time = " + averageSystemResponseTime +
                    "\nTotal assessment Of task relevance = " + totalAssessmentOfRelevance);

            System.out.println("\nBest parameter = " +
                    findBestQuanta(LAMBDA, MU, TASK_TO_SIMULATE, -1, -6, -3, 4));

            System.out.println("-------------------------------------------");
        }

        {
            double averageTimeInSystem = 0.0;
            double dispersionOfTimeInSystem = 0.0;
            double averageSystemResponseTime = 0.0;
            double totalAssessmentOfRelevance = 0.0;

            DisciplineSF disciplineSF = new DisciplineSF(LAMBDA, MU, QUANTA);
            List<Task> finishedTasks = disciplineSF.simulateDisciplineSF(TASK_TO_SIMULATE);

            averageTimeInSystem += getAverageTimeInSystem(finishedTasks);
            dispersionOfTimeInSystem += getDispersionOfTimeInSystem(finishedTasks, averageTimeInSystem);
            averageSystemResponseTime += getAverageSystemResponseTime(finishedTasks);
            totalAssessmentOfRelevance += getTotalAssessmentOfRelevance(finishedTasks);

            System.out.println("SF: " + "\nAverage time in system = " + averageTimeInSystem +
                    "\nDispersion of time in system = " + dispersionOfTimeInSystem +
                    "\nAverage system response time = " + averageSystemResponseTime +
                    "\nTotal assessment Of task relevance = " + totalAssessmentOfRelevance);
        }
    }

    static double getAverageTimeInSystem(List<Task> tasks) {
        double totalTimeInSystem = 0.0;
        for (Task task : tasks) {
            totalTimeInSystem += task.getTimeInSystem();
        }

        return totalTimeInSystem / tasks.size();
    }

    static double getDispersionOfTimeInSystem(List<Task> tasks, double averageTime) {
        double sum = 0.0;
        for (Task task : tasks) {
            final double time = task.getTimeInSystem() - averageTime;
            sum += time * time;
        }

        return sum / (tasks.size() - 1);
    }

    static double getAverageSystemResponseTime(List<Task> tasks){
        double totalTimeInSystem = 0.0;
        for (Task task : tasks){
            totalTimeInSystem += task.getSystemResponseTime();
        }

        return totalTimeInSystem / tasks.size();
    }

    static double getTotalAssessmentOfRelevance(List<Task> tasks){
        double totalAssessmentOfRelevance = 0.0;
        for (Task task : tasks){
            final double currentRelevance = task.getRelevanceOfTask();
            if (currentRelevance > 0){
                totalAssessmentOfRelevance += currentRelevance;
            }
        }

        return totalAssessmentOfRelevance / tasks.size();
    }

    static double findBestQuanta(double lambda, double mu, int tasksToSimulate,
                          int coeff1, int coeff2, int coeff3, int coeff5){
        final double QUANTA_STEP = 0.1;
        final double LOWER_BOUND = 0.1;
        final double HIGHER_BOUND = 2.0;
        List<Double> statistics = new ArrayList<>();

        for(double quanta = LOWER_BOUND; quanta <= HIGHER_BOUND; quanta += QUANTA_STEP){
            double averageTimeInSystem = 0.0;
            double dispersionOfTimeInSystem = 0.0;
            double averageSystemResponseTime = 0.0;
            double totalAssessmentOfRelevance = 0.0;

            DisciplineFB disciplineFB = new DisciplineFB(lambda, mu, quanta);
            List<Task> finishedTasks = disciplineFB.simulateDisciplineFB(tasksToSimulate);

            averageTimeInSystem += getAverageTimeInSystem(finishedTasks);
            dispersionOfTimeInSystem += getDispersionOfTimeInSystem(finishedTasks, averageTimeInSystem);
            averageSystemResponseTime += getAverageSystemResponseTime(finishedTasks);
            totalAssessmentOfRelevance += getTotalAssessmentOfRelevance(finishedTasks);
            
            statistics.add(coeff1 * averageTimeInSystem + coeff2 * dispersionOfTimeInSystem +
                    coeff3 * averageSystemResponseTime + coeff5 * totalAssessmentOfRelevance);
        }

        double currentMax = statistics.get(0);
        int indexOfMax = 0;
        for (int i = 0; i < statistics.size(); i++){
            if(statistics.get(i) > currentMax){
                currentMax = statistics.get(i);
                indexOfMax = i;
            }
        }

        final double BEST_QUANTA = LOWER_BOUND + QUANTA_STEP * indexOfMax;

        return BEST_QUANTA;
    }
}
