package ch.zhaw.pm2.studyflow;

/**
 * This class represents a grade for the {@link Assessment}.
 *
 * @author StackOverFlow
 * @version 1.0
 */
public class Grade {
    private double gradeValue;
    private double weight;

    /**
     * Creates a new Grade object with the given grade and weight.
     *
     * @param gradeValue  The grade value.
     * @param weight The weight value.
     */
    public Grade(double gradeValue, double weight) {
        this.gradeValue = gradeValue;
        this.weight = weight;
    }

    public double getGradeValue() {
        return gradeValue;
    }

    public void setGradeValue(double gradeValue) {
        this.gradeValue = gradeValue;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * This method returns a string representation of the grade value.
     * If the grade value is less than or equal to 0.0, returns "-".
     *
     * @return The string representation of the grade value.
     */
    @Override
    public String toString() {
        String result = "-";
        if (gradeValue > 0.0) {
            result = String.valueOf(gradeValue);
        }
        return result;
    }
}
