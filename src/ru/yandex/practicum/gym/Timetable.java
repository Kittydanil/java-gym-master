package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {
    private Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable;

    public Timetable() {
        timetable = new HashMap<>();
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        TreeMap<TimeOfDay, List<TrainingSession>> training = timetable.get(trainingSession.getDayOfWeek());
        List<TrainingSession> list = new ArrayList<>();
        if (training == null) {
            training = new TreeMap<>();
            list.add(trainingSession);
            training.put(trainingSession.getTimeOfDay(), list);
            timetable.put(trainingSession.getDayOfWeek(), training);
        } else {
            if (!training.containsKey(trainingSession.getTimeOfDay())) {
                training.put(trainingSession.getTimeOfDay(), list);
            }
            timetable.get(trainingSession.getDayOfWeek()).get(trainingSession.getTimeOfDay()).add(trainingSession);
        }

    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        if (timetable.get(dayOfWeek) == null) {
            return new TreeMap<>();
        } else {
            return timetable.get(dayOfWeek);
        }
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        if (timetable.get(dayOfWeek).get(timeOfDay) == null) {
            return new ArrayList<>();
        } else {
            return timetable.get(dayOfWeek).get(timeOfDay);
        }
    }

    public List<Map.Entry<Coach, Integer>> getCountByCoaches() {
        Map<Coach, Integer> trainingCount = new HashMap<>();
        for (Map.Entry<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> map : timetable.entrySet()) {
            TreeMap<TimeOfDay, List<TrainingSession>> timeMap = map.getValue();
            for (Map.Entry<TimeOfDay, List<TrainingSession>> timeEntry : timeMap.entrySet()) {
                List<TrainingSession> trainings = timeEntry.getValue();
                for (TrainingSession training : trainings) {
                    Coach coach = training.getCoach();
                    trainingCount.put(coach, trainingCount.getOrDefault(coach, 0) + 1);
                }
            }
        }
        return trainingCount.entrySet().stream().sorted(Map.Entry.<Coach, Integer>comparingByValue().reversed())
                .toList();
    }
}