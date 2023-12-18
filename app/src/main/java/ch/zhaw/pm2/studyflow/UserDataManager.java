package ch.zhaw.pm2.studyflow;

import ch.zhaw.pm2.studyflow.exceptions.GradeWeightZeroException;
import ch.zhaw.pm2.studyflow.utils.ZonedDateTimeAdapter;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The UserDataManager class manages the user's data, including their modules and study reservations.
 * It reads the data from the jsons
 *
 * @author StackOverFlow
 * @version 1.0
 */
public class UserDataManager implements UserData {

    private List<Module> moduleList;
    private List<StudyReservation> reservationList;

    /**
     * Constructor of UserDataManager.
     *
     * @param fileName the name of the file to read the user's modules from.
     * @throws IOException if the file cannot be read.
     */
    public UserDataManager(String fileName) throws IOException {
        moduleList = readUserModules(fileName);
        reservationList = readUserStudyReservations();
    }

    private List<Module> readUserModules(String fileName) throws IOException {
        Gson gson = new GsonBuilder().registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeAdapter()).create();
        Type moduleListType = new TypeToken<List<Module>>() {
        }.getType();
        String path = Objects.requireNonNull(getClass().getClassLoader().getResource("data/" + fileName)).getPath();
        try (BufferedReader reader = new BufferedReader(new FileReader(path, StandardCharsets.UTF_8))) {
            return gson.fromJson(reader, moduleListType);

        }
    }

    private List<StudyReservation> readUserStudyReservations() throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeAdapter())
                .create();
        Type moduleListType = new TypeToken<List<StudyReservation>>() {
        }.getType();
        String path = Objects.requireNonNull(getClass().getClassLoader().getResource("data/user-reservations.json")).getPath();
        try (BufferedReader reader = new BufferedReader(new FileReader(path, StandardCharsets.UTF_8))) {
            return gson.fromJson(reader, moduleListType);
        }
    }

    @Override
    public List<Module> getModules() {
        return moduleList;
    }

    protected void setModules(List<Module> moduleList) {
        this.moduleList = moduleList;
    }

    @Override
    public List<StudyReservation> getReservations() {
        return reservationList;
    }

    protected void setReservations(List<StudyReservation> reservations) {
        this.reservationList = reservations;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Entry<?>> giveBackTheAssessmentEntries() {
        List<Entry<?>> assessmentEntries = new ArrayList<>();
        for (Module module : moduleList) {
            for (Assessment assessment : module.getAssessmentList()) {
                assessmentEntries.add(new Entry<>("(" + module.getTitle().substring(0, 3) + ") " + assessment.getTitle(), new Interval(assessment.getStart(), assessment.getEnd())));
            }
        }
        return assessmentEntries;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double calculateCurrentAverage() {
        try {
            double totalWeight = 0;
            double totalScore = 0;

            for (Module module : moduleList) {
                Grade grade = module.calculateCurrentAverage() == 0.0 ? new Grade(0, 0) : new Grade(module.calculateCurrentAverage(), module.getCredits());
                totalWeight += grade.getWeight();
                totalScore += grade.getGradeValue() * grade.getWeight();
            }
            if (totalWeight == 0) {
                throw new GradeWeightZeroException("Total weight cannot be zero.");
            }

            double average = totalScore / totalWeight;
            return Math.round(average * 2) / 2.0;
        } catch (GradeWeightZeroException e) {
            return 0.0;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double calculatePossibleGrade() {
        try {
            double totalWeight = 0;
            double totalScore = 0;

            for (Module module : moduleList) {
                for (Assessment assessment : module.getAssessmentList()) {
                    Grade grade = assessment.isFinished() ? assessment.getGrade() : new Grade(5.5, assessment.getGrade().getWeight());
                    totalWeight += grade.getWeight();
                    totalScore += grade.getGradeValue() * grade.getWeight();
                }
            }
            if (totalWeight == 0) {
                throw new GradeWeightZeroException("Total weight cannot be zero.");
            }
            double average = totalScore / totalWeight;
            return Math.round(average * 2) / 2.0;
        } catch (GradeWeightZeroException e) {
            return 0.0;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int generateTotalProgress() {
        float allPassedAssessments = 0;
        float numberOfAssessments = 0;
        for (Module module : moduleList) {
            for (Assessment assessment : module.getAssessmentList()) {
                numberOfAssessments += 1;
                if (assessment.getEnd().isBefore(ZonedDateTime.now())) {
                    allPassedAssessments += 1;
                }
            }
        }
        if (numberOfAssessments == 0) {
            return 100;
        }
        float percentage = (allPassedAssessments / numberOfAssessments) * 100;
        return (int) percentage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Assessment> getNextAssessments() {
        List<Assessment> nextAssessments = new ArrayList<>();
        for (Module module : moduleList) {
            for (Assessment assessment : module.getAssessmentList()) {
                if (assessment.getStart().isAfter(ZonedDateTime.now())) {
                    nextAssessments.add(assessment);
                }
            }
        }
        nextAssessments.sort((assessment1, assessment2) -> assessment1.getStart().compareTo(assessment2.getEnd()));
        return nextAssessments;
    }
}
