package ru.yandex.practicum.gym;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class GymMaster {
    public static Timetable timetable = new Timetable();
    public static Coach semenov = new Coach("Семёнов", "Виктор", "Константинович");
    public static Coach smirnov = new Coach("Смирнов", "Николай", "Александрович");
    public static Coach mironov = new Coach("Миронов", "Юрий", "Борисович");
    public static Coach morev = new Coach("Морев", "Евгений", "Васильевич");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while(running) {
            printMenu();
            int choice = Integer.parseInt(scanner.nextLine());
            switch(choice) {
                case 1:
                    addNewTrainSession(scanner);
                break;
                case 2:
                    getTrainingSessionsForDay(scanner);
                break;
                case 3:
                    getTrainingSessionsForDayAndTime(scanner);
                break;
                case 4:
                    getCountByCoaches();
                break;
                case 0:
                    running = false;
                break;
                default:
                    System.out.println("К сожалению, такой команды пока нет!");
                break;
            }
        }
    }

    public static void printMenu() {
        System.out.println("Выберете необходимый пункт меню:");
        System.out.println("1 - Добавить тренировку в расписание");
        System.out.println("2 - Получить список тренировок за выбранный день");
        System.out.println("3 - Получить список тренировок за определённое время в выбранный день");
        System.out.println("4 - Подсчитать сколько занятий провёл тренер");
        System.out.println("0 - Выход");
    }

    public static void addNewTrainSession(Scanner scanner) {
        Group group = addNewGroup(scanner);
        Coach coach = addCoachInTrainSession(scanner);
        DayOfWeek dayOfWeek = addDayOfWeek(scanner);
        TimeOfDay timeOfDay = addTimeOfDay(scanner);
        TrainingSession trainingSession = new TrainingSession(group, coach, dayOfWeek, timeOfDay);
        timetable.addNewTrainingSession(trainingSession);
    }

    public static Group addNewGroup(Scanner scanner) {
        System.out.println("Введите название группы:");
        String title = scanner.nextLine();

        System.out.println("Это будет детская или взрослая группа?");
        System.out.println("1 - взрослая");
        System.out.println("2 - детская");
        Age age = null;
        while (age == null) {
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice == 1) {
                age = Age.ADULT;
            } else if (choice == 2) {
                age = Age.CHILD;
            } else {
                System.out.println("К сожалению, такого варианта нет!");
            }
        }

        System.out.println("Введите длительность тренировки в минутах:");
        int duration = Integer.parseInt(scanner.nextLine());
        return new Group(title, age, duration);
    }

    public static Coach addCoachInTrainSession(Scanner scanner) {
        Coach coach = null;
        System.out.println("Выберете тренера, который будет вести тренировку:");
        System.out.println("1 - Семёнов В.К.");
        System.out.println("2 - Смирнов Н.А.");
        System.out.println("3 - Миронов Ю.Б.");
        System.out.println("4 - Морев Е.В.");
        int choice = Integer.parseInt(scanner.nextLine());
        while(coach == null) {
            if (choice == 1) {
                coach = semenov;
            } else if (choice == 2) {
                coach = smirnov;
            } else if (choice == 3) {
                coach = mironov;
            } else if (choice == 4) {
                coach = morev;
            } else {
                System.out.println("Такой тренер не работает в нашем зале!");
            }
        }
        return coach;
    }

    public static DayOfWeek addDayOfWeek(Scanner scanner) {
        DayOfWeek dayOfWeek = null;
        System.out.println("Укажите день недели цифрой от 1 до 7:");
        int choice = Integer.parseInt(scanner.nextLine());
        while (dayOfWeek == null) {
            if (choice == 1) {
                dayOfWeek = DayOfWeek.MONDAY;
            } else if (choice == 2) {
                dayOfWeek = DayOfWeek.TUESDAY;
            } else if (choice == 3) {
                dayOfWeek = DayOfWeek.WEDNESDAY;
            } else if (choice == 4) {
                dayOfWeek = DayOfWeek.THURSDAY;
            } else if (choice == 5) {
                dayOfWeek = DayOfWeek.FRIDAY;
            } else if (choice == 6) {
                dayOfWeek = DayOfWeek.SATURDAY;
            } else if (choice == 7) {
                dayOfWeek = DayOfWeek.SUNDAY;
            } else {
                System.out.println("Проверьте правильность ввода дня недели!");
            }
        }
        return dayOfWeek;
    }

    public static TimeOfDay addTimeOfDay(Scanner scanner) {
        System.out.println("Введите час начала тренировки:");
        int hours = -1;
        while (hours < 0 || hours > 23) {
            hours = Integer.parseInt(scanner.nextLine());
            if (hours < 0 || hours > 23) System.out.println("Проверьте правильность ввода!");
        }
        System.out.println("Введите минуты начала тренировки:");
        int minutes = -1;
        while (minutes < 0 || minutes > 59) {
            minutes = Integer.parseInt(scanner.nextLine());
            if (minutes < 0 || minutes > 59) System.out.println("Проверьте правильность ввода!!");
        }
        return new TimeOfDay(hours, minutes);
    }

    public static void getTrainingSessionsForDay(Scanner scanner) {
        DayOfWeek dayOfWeek = addDayOfWeek(scanner);

        System.out.println("Список всех тренировок в " + dayOfWeek);
        if (timetable.getTrainingSessionsForDay(dayOfWeek) == null) {
            System.out.println("К сожалению, в выбранный день нет тренировок!");
        } else {
            timetable.getTrainingSessionsForDay(dayOfWeek).forEach((timeOfDay, list) -> System.out.println(timeOfDay.toString() + "\n" + list));
        }
    }

    public static void getTrainingSessionsForDayAndTime(Scanner scanner) {
        DayOfWeek dayOfWeek = addDayOfWeek(scanner);
        TimeOfDay timeOfDay = addTimeOfDay(scanner);

        System.out.println("Список всех тренировок в " + dayOfWeek + " " + timeOfDay.toString() + ":");
        if (timetable.getTrainingSessionsForDay(dayOfWeek) == null) {
            System.out.println("К сожалению, в выбранный день нет тренировок!");
        } else if (timetable.getTrainingSessionsForDayAndTime(dayOfWeek, timeOfDay) == null) {
            System.out.println("К сожалению, в это время нет тренировок!");
        } else {
            System.out.println(timetable.getTrainingSessionsForDayAndTime(dayOfWeek, timeOfDay));
        }
    }

    public static void getCountByCoaches() {
        System.out.println("Количество тренировок у каждого тренера:");
        for (Map.Entry<Coach, Integer> entry : timetable.getCountByCoaches()) {
            System.out.println(entry.getKey().toString() + ": " + entry.getValue());
        }
    }
}