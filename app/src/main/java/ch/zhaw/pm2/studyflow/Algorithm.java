package ch.zhaw.pm2.studyflow;

import ch.zhaw.pm2.studyflow.exceptions.NoMoreStudyReservationsException;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * The Algorithm class is a utility class responsible for calculating the calendar entries for studying.
 *
 * @author Team StackOverFlow
 * @version 1.0
 */
public class Algorithm {
    private static final double MAX_GRADE = 6.0;
    private static final int AVERAGE_PRIORITY_FACTOR = 5;
    private static final int DURATION_OF_A_STUDY_PERIOD = 20;
    private static final int DURATION_OF_A_BREAK = 10;
    private static final int THIRTY_DAYS = 30;
    private static final int PRIORITY_BASE = 32;
    private static final int OBJECTIVE_REPETITION_FACTOR = 3;

    private Algorithm() {
    }

    /**
     * Retrieves the calendar entries for studying based on the modules and study reservations.
     *
     * @param allModules           the list of modules
     * @param allStudyReservations the list of study reservations
     * @return a list of calendar entries for studying
     * @throws NoMoreStudyReservationsException if there are no more study reservations available
     */
    public static List<Entry<?>> generateCalendarEntries(List<Module> allModules, List<StudyReservation> allStudyReservations) throws NoMoreStudyReservationsException {
        List<Objective> objectivesByPriority = createObjectivesByPriority(allModules);
        List<Entry<?>> allStudyTimes = new ArrayList<>();
        ZonedDateTime currentlyPlannedTime = ZonedDateTime.now();

        int objectiveIndex = 0;
        int reservationIndex = 0;

        while (objectiveIndex < objectivesByPriority.size() && reservationIndex < allStudyReservations.size()) {
            Objective objective = objectivesByPriority.get(objectiveIndex);
            StudyReservation studyReservation = allStudyReservations.get(reservationIndex);

            ZonedDateTime start = calculateStudyStartTime(studyReservation.getStart(), currentlyPlannedTime);
            if (isTimeAvailableForStudy(studyReservation.getEnd(), currentlyPlannedTime) && doesNoExamTakesPlace(allModules, start)) {
                if (canStartStudy(objective, start) ) {
                    allStudyTimes.add(createStudyEntry(objective, start));
                    currentlyPlannedTime = calculateNextPlannedTime(start);
                }
                objectiveIndex++;
            } else {
                reservationIndex++;
            }
        }

        if (reservationIndex == allStudyReservations.size()) {
            throw new NoMoreStudyReservationsException("No more time left for studying.");
        }

        return allStudyTimes;
    }

    /**
     * Checks if any assessments block the study time.
     *
     * @param allModules A list of all modules.
     * @param start The start time for study.
     * @return {@code true} if no assessments block the study time, {@code false} otherwise.
     */
    private static boolean doesNoExamTakesPlace(List<Module> allModules, ZonedDateTime start) {
        boolean noAssessmentBlocksStudyTime = true;
        for (Module module: allModules){
            for (Assessment assessment : module.getAssessmentList()){
                if (assessment.getStart().isBefore(start) && assessment.getEnd().isAfter(start)){
                    noAssessmentBlocksStudyTime = false;
                }
            }
        }
        return noAssessmentBlocksStudyTime;
    }

    /**
     * Generates a priority map for assessments based on the modules.
     *
     * @param allModules the list of modules
     * @return a map with the priority as key and the corresponding assessment as value
     */
    private static Map<Integer, Assessment> generatePriorityMap(List<Module> allModules) {
        Map<Integer, Assessment> priorityMap = new HashMap<>();
        for (Module module : allModules) {
            List<Assessment> assessmentList = module.getAssessmentList();
            for (Assessment assessment : assessmentList) {
                int priority = calculatePriority(module, assessment);
                priority = findNextAvailablePriority(priorityMap, priority);
                priorityMap.put(priority, assessment);
            }
        }

        return priorityMap;
    }

    /**
     * Calculates the priority value for an assessment within a module.
     *
     * @param module     the module containing the assessment
     * @param assessment the assessment for which to calculate the priority
     * @return the priority value for the assessment
     */
    static int calculatePriority(Module module, Assessment assessment) {
        int priority = 0;

        ZonedDateTime due = assessment.getStart();
        priority += (MAX_GRADE - module.calculateCurrentAverage()) * AVERAGE_PRIORITY_FACTOR;
        priority *= calculateDatePriority(due);
        priority *= module.getCredits();
        priority *= 1 - module.getPersonalProgressEstimate();

        return priority;
    }

    /**
     * Finds the next available priority value in the priority map that is not already used.
     *
     * @param priorityMap the priority map to check for existing priorities
     * @param priority    the initial priority value to start searching from
     * @return the next available priority value
     */
    private static int findNextAvailablePriority(Map<Integer, Assessment> priorityMap, int priority) {
        if (priority != 0) {
            while (priorityMap.containsKey(priority)) {
                priority++;
            }
        }
        return priority;
    }

    /**
     * Calculates the priority value for a given date based on its proximity to the current date.
     *
     * @param date the date for which to calculate the priority
     * @return the priority value for the date
     */
    private static int calculateDatePriority(ZonedDateTime date) {
        int priority = 0;

        if (isWithinNextThirtyDays(date)) {
            priority = calculatePriorityWithinThirtyDays(date);
        }

        return priority;
    }

    /**
     * Checks if the given date is within the next thirty days.
     *
     * @param date the date to check
     * @return true if the date is within the next thirty days, false otherwise
     */
    static boolean isWithinNextThirtyDays(ZonedDateTime date) {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime dateInAMonth = now.plusDays(THIRTY_DAYS);
        return date.isAfter(ZonedDateTime.now()) && date.isBefore(dateInAMonth);
    }

    /**
     * Calculates the priority value for a date that is within the next thirty days.
     *
     * @param date the date for which to calculate the priority
     * @return the priority value for the date within the next thirty days
     */
    private static int calculatePriorityWithinThirtyDays(ZonedDateTime date) {
        int daysUntil = (int) ZonedDateTime.now().until(date, ChronoUnit.DAYS);
        int priority = PRIORITY_BASE - daysUntil;
        priority *= priority;
        return priority;
    }

    /**
     * Retrieves objectives by priority from all modules and combines them into a single list based on the priority order.
     *
     * @param allModules the list of all modules
     * @return the combined list of objectives sorted by priority
     */
    private static List<Objective> createObjectivesByPriority(List<Module> allModules) {
        Map<Integer, Assessment> priorityMap = generatePriorityMap(allModules);
        List<Integer> priorities = calculateSortedPriorities(priorityMap);

        return groupsObjectives(priorityMap, priorities);
    }


    /**
     * Retrieves the sorted list of priorities from the priority map.
     *
     * @param priorityMap the map of priorities with corresponding assessments
     * @return the sorted list of priorities
     */
    private static List<Integer> calculateSortedPriorities(Map<Integer, Assessment> priorityMap) {
        List<Integer> priorities = new ArrayList<>(new TreeSet<>(priorityMap.keySet()));
        Collections.reverse(priorities);
        return priorities;
    }

    /**
     * Retrieves and combines objectives from the priority map based on the given list of priorities in sorted order
     * and groups them in pairs of two.
     *
     * @param priorityMap the map of priorities with corresponding assessments
     * @param priorities  the list of priorities specifying the order of combination
     * @return a list of objectives sorted according to the desired priority factors.
     */
    private static List<Objective> groupsObjectives(Map<Integer, Assessment> priorityMap, List<Integer> priorities) {
        List<Objective> combinedObjectives = new ArrayList<>();
        Iterator<Integer> iterator = priorities.iterator();
        Integer previousPriority = iterator.next();

        while (iterator.hasNext()) {
            Integer currentPriority = iterator.next();
            Assessment previousAssessment = priorityMap.get(previousPriority);
            Assessment currentAssessment = priorityMap.get(currentPriority);
            List<Objective> combinedList = combineLists(previousAssessment.getObjectiveList(), currentAssessment.getObjectiveList());
            combinedObjectives.addAll(combinedList);
            previousPriority = currentPriority;
        }

        return combinedObjectives;
    }

    /**
     * Combines two lists of objectives into a single list by alternating their elements. The resulting list repeats this alternating pattern based on a repetition factor.
     *
     * @param list1 the first list of objectives
     * @param list2 the second list of objectives
     * @return the combined list of objectives with alternating elements repeated based on the repetition factor
     */
    private static List<Objective> combineLists(List<Objective> list1, List<Objective> list2) {
        List<Objective> combinedList = new ArrayList<>();
        int maxSize = Math.max(list1.size(), list2.size());

        for (int k = 0; k < OBJECTIVE_REPETITION_FACTOR; k++) {
            for (int i = 0; i < maxSize; i++) {
                if (i < list1.size()) {
                    combinedList.add(list1.get(i));
                }
                if (i < list2.size()) {
                    combinedList.add(list2.get(i));
                }
            }
        }
        return combinedList;
    }

    /**
     * Checks if there is enough time available in the current StudyReservation to fit one study period.
     *
     * @param reservationEnd the end time of the study reservation
     * @param plannedTime    the planned start time for studying
     * @return true if there is enough time available for studying, false otherwise
     */
    private static boolean isTimeAvailableForStudy(ZonedDateTime reservationEnd, ZonedDateTime plannedTime) {
        return reservationEnd.minus(DURATION_OF_A_STUDY_PERIOD, ChronoUnit.MINUTES).isAfter(plannedTime);
    }

    /**
     * Calculates the study start time based on the reservation start time and planned time.
     *
     * @param reservationStart the start time of the study reservation
     * @param plannedTime      the next possible time to start studying
     * @return the study start time, which is either the reservation start time or the planned time if it is later
     */
    private static ZonedDateTime calculateStudyStartTime(ZonedDateTime reservationStart, ZonedDateTime plannedTime) {
        return plannedTime.isBefore(reservationStart) ? reservationStart : plannedTime;
    }

    /**
     * Checks if the objective can be started at the given start time. It can be started when the whole study period can
     * take place before an exam and when the objective is not yet done.
     *
     * @param objective the objective to be checked
     * @param startTime the start time for the study period
     * @return true if the study can be started, false otherwise
     */
    static boolean canStartStudy(Objective objective, ZonedDateTime startTime) {
        return startTime.plus(DURATION_OF_A_STUDY_PERIOD, ChronoUnit.MINUTES).isBefore(objective.getAssessmentDate())
                && objective.getState() != Objective.State.DONE;
    }

    /**
     * Creates a study entry for the given objective and start time.
     *
     * @param objective the objective being studied
     * @param start     the start time of the study period
     * @return the study entry containing the objective title and the study interval
     */
    private static Entry<?> createStudyEntry(Objective objective, ZonedDateTime start) {
        ZonedDateTime endTime = start.plus(DURATION_OF_A_STUDY_PERIOD, ChronoUnit.MINUTES);
        return new Entry<>(objective.getTitle(), new Interval(start, endTime));
    }

    /**
     * Calculates the next planned time after a given start time, taking into account the duration of a study period and a break.
     *
     * @param startTime the start time of the current study period
     * @return the calculated next planned time after the study period and break
     */
    static ZonedDateTime calculateNextPlannedTime(ZonedDateTime startTime) {
        return startTime.plus(DURATION_OF_A_STUDY_PERIOD, ChronoUnit.MINUTES).plus(DURATION_OF_A_BREAK, ChronoUnit.MINUTES);
    }
}
