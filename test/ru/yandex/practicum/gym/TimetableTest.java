package ru.yandex.practicum.gym;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Семёнов", "Виктор", "Константинович");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        timetable.addNewTrainingSession(singleTrainingSession);
        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());//Проверить, что за понедельник вернулось одно занятие
        assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).isEmpty());//Проверить, что за вторник не вернулось занятий
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();
        TimeOfDay time13 = new TimeOfDay(13, 0);
        TimeOfDay time20 = new TimeOfDay(20, 0);
        Coach coach = new Coach("Семёнов", "Виктор", "Константинович");
        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);

        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());// Проверить, что за понедельник вернулось одно занятие
        assertEquals(2, timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).size());
        assertEquals(timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).firstKey(), time13);
        assertEquals(timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).lastKey(), time20);// Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).isEmpty());// Проверить, что за вторник не вернулось занятий
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();
        TimeOfDay time13 = new TimeOfDay(13, 0);
        TimeOfDay time14 = new TimeOfDay(14, 0);
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Семёнов", "Виктор", "Константинович");

        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        assertEquals(1, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, time13).size());//Проверить, что за понедельник в 13:00 вернулось одно занятие
        assertTrue(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, time14).isEmpty());//Проверить, что за понедельник в 14:00 не вернулось занятий
    }

    @Test
    void testGetTrainingSessionForDayAndTimeMultipleSession() {
        Timetable timetable = new Timetable();
        Coach coach = new Coach("Семёнов", "Виктор", "Константинович");
        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TimeOfDay time13 = new TimeOfDay(13, 0);
        TimeOfDay time14 = new TimeOfDay(14, 0);

        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, time13);

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(mondayChildTrainingSession);

        assertEquals(3, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, time13).size()); //Проверка, что за 13:00 вернулось 3 занятия
        assertTrue(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, time14).isEmpty()); //Проверка, что за 14:00 не вернулось тренировок
    }

    @Test
    void testGetCountByCoachesSingleCouchAndSingleTrainSession() {
        Timetable timetable = new Timetable();
        Coach coach = new Coach("Семёнов", "Виктор", "Константинович");
        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TimeOfDay time13 = new TimeOfDay(13, 0);

        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, time13);

        timetable.addNewTrainingSession(mondayChildTrainingSession);

        assertEquals(1, timetable.getCountByCoaches().size()); //Проверка размера списка
        assertEquals(1, timetable.getCountByCoaches().getFirst().getValue()); //Проверка кол-ва тренировок тренера
    }

    @Test
    void testGetCountByCoachesMultipleCouchAndSingleTrainSession() {
        Timetable timetable = new Timetable();
        Coach semenov = new Coach("Семёнов", "Виктор", "Константинович");
        Coach smirnov = new Coach("Смирнов", "Николай", "Александрович");
        Coach mironov = new Coach("Миронов", "Юрий", "Борисович");
        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TimeOfDay time13 = new TimeOfDay(13, 0);

        TrainingSession mondayChildTrainingSessionSevenov = new TrainingSession(groupChild, semenov,
                DayOfWeek.MONDAY, time13);
        TrainingSession mondayChildTrainingSessionSmirnov = new TrainingSession(groupChild, smirnov,
                DayOfWeek.MONDAY, time13);
        TrainingSession mondayChildTrainingSessionMironov = new TrainingSession(groupChild, mironov,
                DayOfWeek.MONDAY, time13);

        timetable.addNewTrainingSession(mondayChildTrainingSessionSevenov);
        timetable.addNewTrainingSession(mondayChildTrainingSessionSmirnov);
        timetable.addNewTrainingSession(mondayChildTrainingSessionMironov);

        assertEquals(3, timetable.getCountByCoaches().size()); //Проверка размера списка
        assertEquals(1, timetable.getCountByCoaches().get(0).getValue()); //Проверка кол-ва тренировок тренера
        assertEquals(1, timetable.getCountByCoaches().get(1).getValue()); //Проверка кол-ва тренировок тренера
        assertEquals(1, timetable.getCountByCoaches().get(2).getValue()); //Проверка кол-ва тренировок тренера
    }

    @Test
    void testGetCountByCoachesMultipleCouchAndMultipleTrainSession() {
        Timetable timetable = new Timetable();
        Coach semenov = new Coach("Семёнов", "Виктор", "Константинович");
        Coach smirnov = new Coach("Смирнов", "Николай", "Александрович");
        Coach mironov = new Coach("Миронов", "Юрий", "Борисович");
        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TimeOfDay time13 = new TimeOfDay(13, 0);

        TrainingSession mondayChildTrainingSessionSevenov = new TrainingSession(groupChild, semenov,
                DayOfWeek.MONDAY, time13);
        TrainingSession mondayChildTrainingSessionSmirnov = new TrainingSession(groupChild, smirnov,
                DayOfWeek.MONDAY, time13);
        TrainingSession mondayChildTrainingSessionMironov = new TrainingSession(groupChild, mironov,
                DayOfWeek.MONDAY, time13);

        timetable.addNewTrainingSession(mondayChildTrainingSessionSevenov);
        timetable.addNewTrainingSession(mondayChildTrainingSessionSmirnov);
        timetable.addNewTrainingSession(mondayChildTrainingSessionMironov);
        timetable.addNewTrainingSession(mondayChildTrainingSessionSevenov);
        timetable.addNewTrainingSession(mondayChildTrainingSessionSmirnov);
        timetable.addNewTrainingSession(mondayChildTrainingSessionMironov);
        timetable.addNewTrainingSession(mondayChildTrainingSessionSevenov);
        timetable.addNewTrainingSession(mondayChildTrainingSessionSmirnov);
        timetable.addNewTrainingSession(mondayChildTrainingSessionMironov);

        assertEquals(3, timetable.getCountByCoaches().size()); //Проверка размера списка
        assertEquals(3, timetable.getCountByCoaches().get(0).getValue()); //Проверка кол-ва тренировок тренера
        assertEquals(3, timetable.getCountByCoaches().get(1).getValue()); //Проверка кол-ва тренировок тренера
        assertEquals(3, timetable.getCountByCoaches().get(2).getValue()); //Проверка кол-ва тренировок тренера
    }

    @Test
    void testGetCountByCoachesSortingOrder() {
        Timetable timetable = new Timetable();
        Coach semenov = new Coach("Семёнов", "Виктор", "Константинович");
        Coach smirnov = new Coach("Смирнов", "Николай", "Александрович");
        Coach mironov = new Coach("Миронов", "Юрий", "Борисович");
        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TimeOfDay time13 = new TimeOfDay(13, 0);

        TrainingSession mondayChildTrainingSessionSevenov = new TrainingSession(groupChild, semenov,
                DayOfWeek.MONDAY, time13);
        TrainingSession mondayChildTrainingSessionSmirnov = new TrainingSession(groupChild, smirnov,
                DayOfWeek.MONDAY, time13);
        TrainingSession mondayChildTrainingSessionMironov = new TrainingSession(groupChild, mironov,
                DayOfWeek.MONDAY, time13);

        timetable.addNewTrainingSession(mondayChildTrainingSessionSevenov);
        timetable.addNewTrainingSession(mondayChildTrainingSessionSmirnov);
        timetable.addNewTrainingSession(mondayChildTrainingSessionMironov);
        timetable.addNewTrainingSession(mondayChildTrainingSessionSevenov);
        timetable.addNewTrainingSession(mondayChildTrainingSessionMironov);
        timetable.addNewTrainingSession(mondayChildTrainingSessionSevenov);

        assertEquals(3, timetable.getCountByCoaches().size()); //Проверка размера списка
        assertEquals(3, timetable.getCountByCoaches().get(0).getValue()); //Проверка кол-ва тренировок тренера
        assertEquals(2, timetable.getCountByCoaches().get(1).getValue()); //Проверка кол-ва тренировок тренера
        assertEquals(1, timetable.getCountByCoaches().get(2).getValue()); //Проверка кол-ва тренировок тренера
    }
}
