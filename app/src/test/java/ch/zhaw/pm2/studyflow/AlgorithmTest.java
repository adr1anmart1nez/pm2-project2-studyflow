package ch.zhaw.pm2.studyflow;

import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


/**
 * Test class for the {@link Algorithm} class.<br>
 *
 * <br>This test class has the following equivalence classes:<br>
 * <p>
 * 01. previousGradesInfluencePriority Tests if the previous grade influences the learning objectives priority.
 * 02. creditsInfluencePriority Tests if the credits you get for the module influences the learning objectives priority.
 * 03. personalSecurityInfluencesPriority Tests if personal security influences the learning objectives priority.
 * 04. objectivesWithCloserDueDateHaveHigherPriority Tests if appointments with assessments close to the current date have a higher priority.
 * 05. testIfIsWithinThirtyDays Tests if the assessment is within 30 days.
 * 06. testCalculateNextPlannedTime The test checks if next study start time is calculated correctly.
 * 07. testCanStartStudy The test checks if a study period can be started.
 *
 * @author StackOverflow
 * @version 1.0
 */
class AlgorithmTest {
    private final ZonedDateTime examStartTime = ZonedDateTime.of(2023, 6, 2, 8, 0, 0, 0, ZoneId.of("UTC"));
    private final ZonedDateTime examEndTime = ZonedDateTime.of(2023, 6, 2, 8, 30, 0, 0, ZoneId.of("UTC"));
    private final ZonedDateTime assessmentDate = ZonedDateTime.of(2023, 6, 2, 8, 0, 0, 0, ZoneId.of("UTC"));
    private final Grade grade = new Grade(5, 1);
    private final Objective objective = new Objective("Analysis", "LZ1", Objective.State.PENDING, assessmentDate);

    private final List<Objective> objectiveList = List.of(objective);
    private final Assessment assessment = new Assessment(examStartTime, examEndTime, objectiveList, grade, true, "AN2 exam");

    private final List<Assessment> assessmentList = List.of(assessment);

    /**
     * This is a positive test and of the equivalence class 1. The test checks if the previous grade of a module influence the learning objectives priority.
     */
    @Test
    void previousGradesInfluencePriority() {

        double personalProgressEstimate = 0.4;
        int credits = 1;

        Grade grade0 = new Grade(6, 1);
        Grade grade1 = new Grade(5, 1);
        Grade grade2 = new Grade(4, 1);
        Grade grade3 = new Grade(3, 1);
        Grade grade4 = new Grade(2, 1);
        Grade grade5 = new Grade(0, 1); //if no grade has yet been recorded

        // priority += (6 - module.calculateCurrentAverage()) * 5;

        Assessment assessment0 = new Assessment(examStartTime, examEndTime, objectiveList, grade0, true, "AN2 exam");
        Assessment assessment1 = new Assessment(examStartTime, examEndTime, objectiveList, grade1, true, "AN2 exam");
        Assessment assessment2 = new Assessment(examStartTime, examEndTime, objectiveList, grade2, true, "AN2 exam");
        Assessment assessment3 = new Assessment(examStartTime, examEndTime, objectiveList, grade3, true, "AN2 exam");
        Assessment assessment4 = new Assessment(examStartTime, examEndTime, objectiveList, grade4, true, "AN2 exam");
        Assessment assessment5 = new Assessment(examStartTime, examEndTime, objectiveList, grade5, false, "AN2 exam");

        List<Assessment> assessmentList0 = List.of(assessment0);
        List<Assessment> assessmentList1 = List.of(assessment1);
        List<Assessment> assessmentList2 = List.of(assessment2);
        List<Assessment> assessmentList3 = List.of(assessment3);
        List<Assessment> assessmentList4 = List.of(assessment4);
        List<Assessment> assessmentList5 = List.of(assessment5);

        Module module0 = new Module("Test0", assessmentList0, personalProgressEstimate, credits);
        Module module1 = new Module("Test1", assessmentList1, personalProgressEstimate, credits);
        Module module2 = new Module("Test2", assessmentList2, personalProgressEstimate, credits);
        Module module3 = new Module("Test3", assessmentList3, personalProgressEstimate, credits);
        Module module4 = new Module("Test4", assessmentList4, personalProgressEstimate, credits);
        Module module5 = new Module("Test5", assessmentList5, personalProgressEstimate, credits);


        assertTrue(Algorithm.calculatePriority(module0, assessment0) < Algorithm.calculatePriority(module1, assessment1));
        assertTrue(Algorithm.calculatePriority(module1, assessment1) < Algorithm.calculatePriority(module2, assessment2));
        assertTrue(Algorithm.calculatePriority(module2, assessment2) < Algorithm.calculatePriority(module3, assessment3));
        assertTrue(Algorithm.calculatePriority(module3, assessment3) < Algorithm.calculatePriority(module4, assessment4));
        assertTrue(Algorithm.calculatePriority(module4, assessment4) < Algorithm.calculatePriority(module5, assessment5));

        assertFalse(Algorithm.calculatePriority(module0, assessment0) > Algorithm.calculatePriority(module1, assessment1));
        assertFalse(Algorithm.calculatePriority(module1, assessment1) > Algorithm.calculatePriority(module2, assessment2));
        assertFalse(Algorithm.calculatePriority(module2, assessment2) > Algorithm.calculatePriority(module3, assessment3));
        assertFalse(Algorithm.calculatePriority(module3, assessment3) > Algorithm.calculatePriority(module4, assessment4));
        assertFalse(Algorithm.calculatePriority(module4, assessment4) > Algorithm.calculatePriority(module5, assessment5));

        assertEquals(Algorithm.calculatePriority(module0, assessment0), Algorithm.calculatePriority(module0, assessment0));
        assertEquals(Algorithm.calculatePriority(module1, assessment1), Algorithm.calculatePriority(module1, assessment1));
        assertEquals(Algorithm.calculatePriority(module2, assessment2), Algorithm.calculatePriority(module2, assessment2));
        assertEquals(Algorithm.calculatePriority(module3, assessment3), Algorithm.calculatePriority(module3, assessment3));
        assertEquals(Algorithm.calculatePriority(module4, assessment4), Algorithm.calculatePriority(module4, assessment4));
        assertEquals(Algorithm.calculatePriority(module5, assessment5), Algorithm.calculatePriority(module5, assessment5));
    }

    /**
     * This is a positive test and of the equivalence class 2. The test checks if the credits of a module influence the learning objectives priority.
     */
    @Test
    void creditsInfluencePriority() {
        double personalProgressEstimate = 0;

        int credits1 = 1;
        int credits2 = 2;
        int credits3 = 3;
        int credits4 = 4;

        Module module1 = new Module("Test1", assessmentList, personalProgressEstimate, credits1);
        Module module2 = new Module("Test1", assessmentList, personalProgressEstimate, credits2);
        Module module3 = new Module("Test1", assessmentList, personalProgressEstimate, credits3);
        Module module4 = new Module("Test1", assessmentList, personalProgressEstimate, credits4);


        assertTrue(Algorithm.calculatePriority(module1, assessment) < Algorithm.calculatePriority(module2, assessment));
        assertTrue(Algorithm.calculatePriority(module2, assessment) < Algorithm.calculatePriority(module3, assessment));
        assertTrue(Algorithm.calculatePriority(module3, assessment) < Algorithm.calculatePriority(module4, assessment));

        assertFalse(Algorithm.calculatePriority(module1, assessment) > Algorithm.calculatePriority(module2, assessment));
        assertFalse(Algorithm.calculatePriority(module2, assessment) > Algorithm.calculatePriority(module3, assessment));
        assertFalse(Algorithm.calculatePriority(module3, assessment) > Algorithm.calculatePriority(module4, assessment));

        assertEquals(Algorithm.calculatePriority(module1, assessment), Algorithm.calculatePriority(module1, assessment));
        assertEquals(Algorithm.calculatePriority(module2, assessment), Algorithm.calculatePriority(module2, assessment));
        assertEquals(Algorithm.calculatePriority(module3, assessment), Algorithm.calculatePriority(module3, assessment));
        assertEquals(Algorithm.calculatePriority(module4, assessment), Algorithm.calculatePriority(module4, assessment));

    }

    /**
     * This is a positive test and of the equivalence class 4. The test checks if personal security of a module influences the learning objectives priority.
     */
    @Test
    void personalSecurityInfluencesPriority() {

        double personalProgressEstimatePriorityOfModule1 = 1;
        double personalProgressEstimatePriorityOfModule2 = 0.9;
        double personalProgressEstimatePriorityOfModule3 = 0.4;
        double personalProgressEstimatePriorityOfModule4 = 0.1;
        double personalProgressEstimatePriorityOfModule5 = 0;

        int credits = 1;

        Module module1 = new Module("Test1", assessmentList, personalProgressEstimatePriorityOfModule1, credits);
        Module module2 = new Module("Test2", assessmentList, personalProgressEstimatePriorityOfModule2, credits);
        Module module3 = new Module("Test3", assessmentList, personalProgressEstimatePriorityOfModule3, credits);
        Module module4 = new Module("Test4", assessmentList, personalProgressEstimatePriorityOfModule4, credits);
        Module module5 = new Module("Test5", assessmentList, personalProgressEstimatePriorityOfModule5, credits);

        assertTrue(Algorithm.calculatePriority(module1, assessment) < Algorithm.calculatePriority(module2, assessment));
        assertTrue(Algorithm.calculatePriority(module2, assessment) < Algorithm.calculatePriority(module3, assessment));
        assertTrue(Algorithm.calculatePriority(module3, assessment) < Algorithm.calculatePriority(module4, assessment));
        assertTrue(Algorithm.calculatePriority(module4, assessment) < Algorithm.calculatePriority(module5, assessment));


        assertFalse(Algorithm.calculatePriority(module1, assessment) > Algorithm.calculatePriority(module2, assessment));
        assertFalse(Algorithm.calculatePriority(module2, assessment) > Algorithm.calculatePriority(module3, assessment));
        assertFalse(Algorithm.calculatePriority(module3, assessment) > Algorithm.calculatePriority(module4, assessment));
        assertFalse(Algorithm.calculatePriority(module4, assessment) > Algorithm.calculatePriority(module5, assessment));


        assertEquals(Algorithm.calculatePriority(module1, assessment), Algorithm.calculatePriority(module1, assessment));
        assertEquals(Algorithm.calculatePriority(module2, assessment), Algorithm.calculatePriority(module2, assessment));
        assertEquals(Algorithm.calculatePriority(module3, assessment), Algorithm.calculatePriority(module3, assessment));
        assertEquals(Algorithm.calculatePriority(module4, assessment), Algorithm.calculatePriority(module4, assessment));
        assertEquals(Algorithm.calculatePriority(module5, assessment), Algorithm.calculatePriority(module5, assessment));

    }


    /**
     * This is a positive test and of the equivalence class 4. The test checks if the priority of a module is calculated correctly according to the assessments due date.
     */
    @Test
    void objectivesWithCloserDueDateHaveHigherPriority() {
        double personalProgressEstimate = 0;
        int credits = 1;

        ZonedDateTime examStartTime1 = ZonedDateTime.of(2023, 6, 5, 8, 0, 0, 0, ZoneId.of("UTC"));
        ZonedDateTime examEndTime1 = ZonedDateTime.of(2023, 6, 5, 8, 30, 0, 0, ZoneId.of("UTC"));

        ZonedDateTime examStartTime2 = ZonedDateTime.of(2023, 6, 4, 8, 0, 0, 0, ZoneId.of("UTC"));
        ZonedDateTime examEndTime2 = ZonedDateTime.of(2023, 6, 4, 8, 30, 0, 0, ZoneId.of("UTC"));

        ZonedDateTime examStartTime3 = ZonedDateTime.of(2023, 6, 3, 8, 0, 0, 0, ZoneId.of("UTC"));
        ZonedDateTime examEndTime3 = ZonedDateTime.of(2023, 6, 3, 8, 30, 0, 0, ZoneId.of("UTC"));

        ZonedDateTime examStartTime4 = ZonedDateTime.of(2023, 6, 2, 8, 0, 0, 0, ZoneId.of("UTC"));
        ZonedDateTime examEndTime4 = ZonedDateTime.of(2023, 6, 2, 8, 30, 0, 0, ZoneId.of("UTC"));

        ZonedDateTime examStartTime5 = ZonedDateTime.of(2023, 6, 1, 8, 0, 0, 0, ZoneId.of("UTC"));
        ZonedDateTime examEndTime5 = ZonedDateTime.of(2023, 6, 1, 8, 30, 0, 0, ZoneId.of("UTC"));


        Assessment assessment1 = new Assessment(examStartTime1, examEndTime1, objectiveList, grade, true, "AN2 exam");
        Assessment assessment2 = new Assessment(examStartTime2, examEndTime2, objectiveList, grade, true, "AN2 exam");
        Assessment assessment3 = new Assessment(examStartTime3, examEndTime3, objectiveList, grade, true, "AN2 exam");
        Assessment assessment4 = new Assessment(examStartTime4, examEndTime4, objectiveList, grade, true, "AN2 exam");
        Assessment assessment5 = new Assessment(examStartTime5, examEndTime5, objectiveList, grade, true, "AN2 exam");

        List<Assessment> assessmentList1 = List.of(assessment1);
        List<Assessment> assessmentList2 = List.of(assessment2);
        List<Assessment> assessmentList3 = List.of(assessment3);
        List<Assessment> assessmentList4 = List.of(assessment4);
        List<Assessment> assessmentList5 = List.of(assessment5);

        Module module1 = new Module("Test1", assessmentList1, personalProgressEstimate, credits);
        Module module2 = new Module("Test2", assessmentList2, personalProgressEstimate, credits);
        Module module3 = new Module("Test3", assessmentList3, personalProgressEstimate, credits);
        Module module4 = new Module("Test4", assessmentList4, personalProgressEstimate, credits);
        Module module5 = new Module("Test5", assessmentList5, personalProgressEstimate, credits);


        assertTrue(Algorithm.calculatePriority(module1, assessment1) < Algorithm.calculatePriority(module2, assessment2));
        assertTrue(Algorithm.calculatePriority(module2, assessment2) < Algorithm.calculatePriority(module3, assessment3));
        assertTrue(Algorithm.calculatePriority(module3, assessment3) < Algorithm.calculatePriority(module4, assessment4));
        assertTrue(Algorithm.calculatePriority(module4, assessment4) < Algorithm.calculatePriority(module5, assessment5));

        assertFalse(Algorithm.calculatePriority(module1, assessment1) > Algorithm.calculatePriority(module2, assessment2));
        assertFalse(Algorithm.calculatePriority(module2, assessment2) > Algorithm.calculatePriority(module3, assessment3));
        assertFalse(Algorithm.calculatePriority(module3, assessment3) > Algorithm.calculatePriority(module4, assessment4));
        assertFalse(Algorithm.calculatePriority(module4, assessment4) > Algorithm.calculatePriority(module5, assessment5));

        assertEquals(Algorithm.calculatePriority(module1, assessment1), Algorithm.calculatePriority(module1, assessment1));
        assertEquals(Algorithm.calculatePriority(module2, assessment2), Algorithm.calculatePriority(module2, assessment2));
        assertEquals(Algorithm.calculatePriority(module3, assessment3), Algorithm.calculatePriority(module3, assessment3));
        assertEquals(Algorithm.calculatePriority(module4, assessment4), Algorithm.calculatePriority(module4, assessment4));
        assertEquals(Algorithm.calculatePriority(module5, assessment5), Algorithm.calculatePriority(module5, assessment5));
    }

    /**
     * This is a positive test and of the equivalence class 5. The test checks if time between assessment and today is calculated correctly.
     */
    @Test
    void testIfIsWithinThirtyDays() {
        ZonedDateTime examStartTime1 = ZonedDateTime.of(2023, 6, 5, 8, 0, 0, 0, ZoneId.of("UTC"));
        ZonedDateTime examEndTime1 = ZonedDateTime.of(2023, 6, 5, 8, 30, 0, 0, ZoneId.of("UTC"));

        ZonedDateTime examStartTime2 = ZonedDateTime.of(2023, 6, 3, 8, 0, 0, 0, ZoneId.of("UTC"));
        ZonedDateTime examEndTime2 = ZonedDateTime.of(2023, 6, 3, 8, 30, 0, 0, ZoneId.of("UTC"));

        ZonedDateTime examStartTime3 = ZonedDateTime.of(2023, 6, 4, 8, 0, 0, 0, ZoneId.of("UTC"));
        ZonedDateTime examEndTime3 = ZonedDateTime.of(2023, 6, 4, 8, 30, 0, 0, ZoneId.of("UTC"));

        ZonedDateTime examStartTime4 = ZonedDateTime.of(2023, 6, 10, 8, 0, 0, 0, ZoneId.of("UTC"));
        ZonedDateTime examEndTime4 = ZonedDateTime.of(2023, 6, 10, 8, 30, 0, 0, ZoneId.of("UTC"));

        ZonedDateTime examStartTime5 = ZonedDateTime.of(2023, 9, 12, 8, 0, 0, 0, ZoneId.of("UTC"));
        ZonedDateTime examEndTime5 = ZonedDateTime.of(2023, 9, 12, 8, 30, 0, 0, ZoneId.of("UTC"));

        ZonedDateTime examStartTime6 = ZonedDateTime.of(2024, 6, 16, 8, 0, 0, 0, ZoneId.of("UTC"));
        ZonedDateTime examEndTime6 = ZonedDateTime.of(2024, 6, 16, 8, 30, 0, 0, ZoneId.of("UTC"));

        ZonedDateTime examStartTime7 = ZonedDateTime.of(2023, 12, 22, 8, 0, 0, 0, ZoneId.of("UTC"));
        ZonedDateTime examEndTime7 = ZonedDateTime.of(2023, 12, 22, 8, 30, 0, 0, ZoneId.of("UTC"));

        ZonedDateTime examStartTime8 = ZonedDateTime.of(2023, 10, 30, 8, 0, 0, 0, ZoneId.of("UTC"));
        ZonedDateTime examEndTime8 = ZonedDateTime.of(2023, 10, 30, 8, 30, 0, 0, ZoneId.of("UTC"));


        Assessment assessment1 = new Assessment(examStartTime1, examEndTime1, objectiveList, grade, true, "AN2 exam");
        Assessment assessment2 = new Assessment(examStartTime2, examEndTime2, objectiveList, grade, true, "AN2 exam");
        Assessment assessment3 = new Assessment(examStartTime3, examEndTime3, objectiveList, grade, true, "AN2 exam");
        Assessment assessment4 = new Assessment(examStartTime4, examEndTime4, objectiveList, grade, true, "AN2 exam");
        Assessment assessment5 = new Assessment(examStartTime5, examEndTime5, objectiveList, grade, true, "AN2 exam");
        Assessment assessment6 = new Assessment(examStartTime6, examEndTime6, objectiveList, grade, true, "AN2 exam");
        Assessment assessment7 = new Assessment(examStartTime7, examEndTime7, objectiveList, grade, true, "AN2 exam");
        Assessment assessment8 = new Assessment(examStartTime8, examEndTime8, objectiveList, grade, true, "AN2 exam");

        assertTrue(Algorithm.isWithinNextThirtyDays(assessment1.getStart()));
        assertTrue(Algorithm.isWithinNextThirtyDays(assessment2.getStart()));
        assertTrue(Algorithm.isWithinNextThirtyDays(assessment3.getStart()));
        assertTrue(Algorithm.isWithinNextThirtyDays(assessment4.getStart()));
        assertFalse(Algorithm.isWithinNextThirtyDays(assessment5.getStart()));
        assertFalse(Algorithm.isWithinNextThirtyDays(assessment6.getStart()));
        assertFalse(Algorithm.isWithinNextThirtyDays(assessment7.getStart()));
        assertFalse(Algorithm.isWithinNextThirtyDays(assessment8.getStart()));
    }

    /**
     * This is a positive test and of the equivalence class 6. The test checks if next study start time is calculated correctly.
     */
    @Test
    void testCalculateNextPlannedTime() {
        ZonedDateTime startTime1 = ZonedDateTime.of(2023, 6, 5, 8, 0, 0, 0, ZoneId.of("UTC"));
        ZonedDateTime startTime2 = ZonedDateTime.of(2023, 6, 5, 8, 30, 0, 0, ZoneId.of("UTC"));
        ZonedDateTime startTime3 = ZonedDateTime.of(2023, 6, 5, 9, 0, 0, 0, ZoneId.of("UTC"));
        ZonedDateTime startTime4 = ZonedDateTime.of(2023, 6, 5, 9, 30, 0, 0, ZoneId.of("UTC"));

        assertEquals(startTime1.plus(30, ChronoUnit.MINUTES), Algorithm.calculateNextPlannedTime(startTime1));
        assertEquals(startTime2.plus(30, ChronoUnit.MINUTES), Algorithm.calculateNextPlannedTime(startTime2));
        assertEquals(startTime3.plus(30, ChronoUnit.MINUTES), Algorithm.calculateNextPlannedTime(startTime3));
        assertEquals(startTime4.plus(30, ChronoUnit.MINUTES), Algorithm.calculateNextPlannedTime(startTime4));
    }

    /**
     * This is a positive test and of the equivalence class 7. The test checks if a study period can be started.
     */
    @Test
    void testCanStartStudy() {
        ZonedDateTime startTime1 = ZonedDateTime.of(2023, 6, 5, 8, 0, 0, 0, ZoneId.of("UTC"));
        ZonedDateTime startTime2 = ZonedDateTime.of(2023, 6, 5, 8, 30, 0, 0, ZoneId.of("UTC"));
        ZonedDateTime startTime3 = ZonedDateTime.of(2023, 6, 5, 9, 0, 0, 0, ZoneId.of("UTC"));
        ZonedDateTime startTime4 = ZonedDateTime.of(2023, 6, 5, 9, 30, 0, 0, ZoneId.of("UTC"));

        ZonedDateTime examStartTime1 = ZonedDateTime.of(2023, 7, 5, 8, 0, 0, 0, ZoneId.of("UTC"));
        ZonedDateTime examStartTime2 = ZonedDateTime.of(2023, 7, 5, 8, 30, 0, 0, ZoneId.of("UTC"));
        ZonedDateTime examStartTime3 = ZonedDateTime.of(2023, 5, 5, 9, 0, 0, 0, ZoneId.of("UTC"));
        ZonedDateTime examStartTime4 = ZonedDateTime.of(2023, 5, 5, 9, 30, 0, 0, ZoneId.of("UTC"));

        Objective objective1 = new Objective("Analysis", "LZ1", Objective.State.PENDING, examStartTime1);
        Objective objective2 = new Objective("Analysis", "LZ1", Objective.State.PENDING, examStartTime2);
        Objective objective3 = new Objective("Analysis", "LZ1", Objective.State.PENDING, examStartTime3);
        Objective objective4 = new Objective("Analysis", "LZ1", Objective.State.PENDING, examStartTime4);

        assertTrue(Algorithm.canStartStudy(objective1, startTime1));
        assertTrue(Algorithm.canStartStudy(objective2, startTime2));
        assertFalse(Algorithm.canStartStudy(objective3, startTime3));
        assertFalse(Algorithm.canStartStudy(objective4, startTime4));
    }
}
