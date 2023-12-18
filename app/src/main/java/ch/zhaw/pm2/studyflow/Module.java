package ch.zhaw.pm2.studyflow;

import ch.zhaw.pm2.studyflow.exceptions.GradeWeightZeroException;

import java.util.List;

/**
 * This class represents a module of a study program.
 *
 * @author StackOverFlow
 * @version 1.0
 */
public class Module {
    private final String title;
    private final List<Assessment> assessmentList;
    private final double personalProgressEstimate;
    private final int credits;

    public Module(String title, List<Assessment> assessmentList, double personalProgressEstimate, int credits) {
        this.title = title;
        this.assessmentList = assessmentList;
        this.personalProgressEstimate = personalProgressEstimate;
        this.credits = credits;
    }

    /**
     * Calculates the current average {@link Grade} for the module based on the grades of its {@link Assessment}.
     *
     * @return the current average grade for the module
     */
    public double calculateCurrentAverage() {
        try {
            double totalWeight = 0;
            double totalScore = 0;

            for (Assessment assessment : assessmentList) {
                Grade grade = assessment.isFinished() ? assessment.getGrade() : new Grade(0, 0);
                totalWeight += grade.getWeight();
                totalScore += grade.getGradeValue() * grade.getWeight();
            }

            if (totalWeight == 0) {
                throw new GradeWeightZeroException("Total weight cannot be zero.");
            }

            double average = totalScore / totalWeight;
            return Math.round(average * 2) / 2.0; // round to the nearest 0.5
        } catch (GradeWeightZeroException e) {
            return 0.0;
        }
    }

    public String getTitle() {
        return title;
    }

    public List<Assessment> getAssessmentList() {
        return assessmentList;
    }

    public double getPersonalProgressEstimate() {
        return personalProgressEstimate;
    }

    public int getCredits() {
        return credits;
    }
}
