package ch.zhaw.pm2.studyflow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for {@link UserDataManager}.
 *
 * <br>This test class has the following equivalence classes:<br>
 * <p>
 * 1. calculatesCurrentAverageCorrectly: The test checks if the method calculates the current average correctly.
 * 2. calculatesPossibleGradeCorrectly: The test checks if the method calculates the possible grade correctly.
 * 3. generatesTotalProgressCorrectly: The test checks if the total progress is calculated correctly.
 * 4. returnsCorrectAssessments: The test checks if the returned assessments are correct.
 *
 * @author StackOverflow
 * @version 1.0
 */
class UserDataManagerMockitoTest {

    @Mock
    private Module mockModule1;
    @Mock
    private Module mockModule2;
    @Mock
    private Assessment mockAssessment1;
    @Mock
    private Assessment mockAssessment2;
    @Mock
    private StudyReservation mockReservation1;
    @Mock
    private StudyReservation mockReservation2;

    private UserDataManager userDataManager;

    @BeforeEach
    void setup() throws IOException {
        MockitoAnnotations.openMocks(this);

        // Initialize module mocks
        when(mockModule1.getTitle()).thenReturn("Module 1");
        when(mockModule1.getCredits()).thenReturn(5);
        when(mockModule1.calculateCurrentAverage()).thenReturn(4.5);
        List<Assessment> assessments1 = new ArrayList<>();
        assessments1.add(mockAssessment1);
        assessments1.add(mockAssessment2);
        when(mockModule1.getAssessmentList()).thenReturn(assessments1);
        when(mockModule2.getTitle()).thenReturn("Module 2");
        when(mockModule2.getCredits()).thenReturn(3);
        when(mockModule2.calculateCurrentAverage()).thenReturn(0.0);
        List<Assessment> assessments2 = new ArrayList<>();
        when(mockModule2.getAssessmentList()).thenReturn(assessments2);

        // Initialize assessment mocks
        when(mockAssessment1.getTitle()).thenReturn("Assessment 1");
        when(mockAssessment1.getStart()).thenReturn(ZonedDateTime.of(LocalDateTime.of(2023, 5, 13, 8, 0), ZoneId.systemDefault()));
        when(mockAssessment1.getEnd()).thenReturn(ZonedDateTime.of(LocalDateTime.of(2023, 5, 13, 10, 0), ZoneId.systemDefault()));
        when(mockAssessment1.isFinished()).thenReturn(true);
        when(mockAssessment1.getGrade()).thenReturn(new Grade(4.0, 0.5));
        when(mockAssessment2.getTitle()).thenReturn("Assessment 2");
        when(mockAssessment2.getStart()).thenReturn(ZonedDateTime.of(LocalDateTime.of(2023, 5, 14, 14, 0), ZoneId.systemDefault()));
        when(mockAssessment2.getEnd()).thenReturn(ZonedDateTime.of(LocalDateTime.of(2023, 5, 14, 16, 0), ZoneId.systemDefault()));
        when(mockAssessment2.isFinished()).thenReturn(false);
        when(mockAssessment2.getGrade()).thenReturn(new Grade(5.5, 0.5));

        // Initialize reservation mocks
        when(mockReservation1.getStart()).thenReturn(ZonedDateTime.of(LocalDateTime.of(2023, 5, 15, 8, 0), ZoneId.systemDefault()));
        when(mockReservation1.getEnd()).thenReturn(ZonedDateTime.of(LocalDateTime.of(2023, 5, 15, 10, 0), ZoneId.systemDefault()));
        when(mockReservation2.getStart()).thenReturn(ZonedDateTime.of(LocalDateTime.of(2023, 5, 16, 14, 0), ZoneId.systemDefault()));
        when(mockReservation2.getEnd()).thenReturn(ZonedDateTime.of(LocalDateTime.of(2023, 5, 16, 15, 35), ZoneId.systemDefault()));

        // Initialize user data manager
        userDataManager = new UserDataManager("user-modules.json");
        userDataManager.setModules(List.of(mockModule1, mockModule2));
        userDataManager.setReservations(List.of(mockReservation1, mockReservation2));
    }

    /**
     * This is a positive test of equivalence class 1.
     */
    @Test
    void testCalculateCurrentAverage() {
        double actualAverage = userDataManager.calculateCurrentAverage();
        verify(mockModule1, times(2)).calculateCurrentAverage();
        assertEquals(4.5, actualAverage);
    }

    /**
     * This is a positive test of equivalence class 2.
     */
    @Test
    void testCalculatePossibleGrade() {
        double possibleGrade = userDataManager.calculatePossibleGrade();
        assertEquals(5.0, possibleGrade);
    }

    /**
     * This is a positive test of equivalence class 3.
     */
    @Test
    void testGenerateTotalProgress() {
        int totalProgress = userDataManager.generateTotalProgress();
        assertEquals(0, totalProgress);
    }

    /**
     * This is a positive test of equivalence class 4.
     */
    @Test
    void testGetNextAssessments() {
        List<Assessment> nextAssessments = List.of(mockAssessment1, mockAssessment2);
        assertEquals(nextAssessments, userDataManager.getNextAssessments());
    }
}