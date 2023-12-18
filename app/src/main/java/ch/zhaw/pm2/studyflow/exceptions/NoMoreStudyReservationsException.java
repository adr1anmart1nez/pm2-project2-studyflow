package ch.zhaw.pm2.studyflow.exceptions;

import ch.zhaw.pm2.studyflow.StudyReservation;

/**
 * The NoMoreStudyReservationsException is an exception thrown when there are no more {@link StudyReservation} available.
 * This exception can occur when a user tries to reserve a spot to study but all spots are already taken.
 */
public class NoMoreStudyReservationsException extends Exception {
    /**
     * Constructs a new NoMoreStudyReservationsException with the specified detail message.
     *
     * @param message the detail message for the exception
     */
    public NoMoreStudyReservationsException(String message) {
        super(message);
    }
}
