package ch.zhaw.pm2.studyflow.exceptions;

import ch.zhaw.pm2.studyflow.Grade;

/**
 * The GradeWeightZeroException is an exception thrown when an assessment's weight for a {@link Grade} is set to zero. A weight of zero
 * means that the assessment won't contribute to the final grade calculation, which is not allowed.
 */
public class GradeWeightZeroException extends Exception {
    /**
     * Constructs a new GradeWeightZeroException with the specified detail message.
     *
     * @param message the detail message for the exception
     */
    public GradeWeightZeroException(String message) {
        super(message);
    }
}
