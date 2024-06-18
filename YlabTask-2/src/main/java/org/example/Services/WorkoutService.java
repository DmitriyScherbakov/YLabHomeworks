package org.example.Services;

import org.example.Entity.AuditRecord;
import org.example.Input.InputManager;
import org.example.Repository.AuditService;
import org.example.Repository.WorkoutRepositoryImpl;
import org.example.Entity.User;
import org.example.Entity.Workout;
import org.example.Repository.WorkoutRepository;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Set;


public class WorkoutService {

    private final User currentUser;
    WorkoutRepository workoutRepository = new WorkoutRepositoryImpl();
    InputManager inputManager = new InputManager();
    WorkoutTypeService workoutTypeService = new WorkoutTypeService();

    AuditService auditService = new AuditService();


    public WorkoutService(User currentUser) {
        this.currentUser = currentUser;
    }

    public Workout createNewWorkout(User currentUser) throws ParseException {
        System.out.println("Adding a new workout:");

        // Запрос данных о тренировке
        Date date = inputManager.readDateFromInput("Enter the date (dd.MM.yyyy): ");

        // Получение доступных типов тренировок
        workoutTypeService.showWorkoutTypes();
        String type = inputManager.readString("Choose a workout type from the available options: ");

        int duration = inputManager.readIntegerFromInput("Enter the duration in minutes: ");
        int caloriesBurned = inputManager.readIntegerFromInput("Enter the calories burned: ");
        String additionalInfo = inputManager.readString("Enter additional information (optional): ");

        // Создание нового объекта тренировки
        Workout newWorkout = new Workout(date, type, duration, caloriesBurned, additionalInfo,  currentUser.getId());
        workoutRepository.save(newWorkout);
        System.out.println("Successfully adding a new workout");
        auditService.addAuditRecord(new AuditRecord(currentUser.getId(), "add new workout", "currentTimestamp"));
        return newWorkout;
    }


    public void displayWorkouts(User currentUser){
        List<Workout> listOfWorkOuts = workoutRepository.getAllWorkouts(currentUser);
        if (listOfWorkOuts.isEmpty()) {
            System.out.println("No workouts found.");
        } else {
            System.out.println("List of workouts:");
            for (Workout workout : listOfWorkOuts) {
                System.out.println(workout);
            }
        }
    }

    public void deleteWorkout(User currentUser) {
        System.out.println("Deleting a workout:");
        List<Workout> listOfWorkOuts = workoutRepository.getAllWorkouts(currentUser);
        if (listOfWorkOuts.isEmpty()) {
            System.out.println("No workouts to delete.");
        } else {
            System.out.println("Choose the workout to delete:");
            for (int i = 0; i < listOfWorkOuts.size(); i++) {
                System.out.println((i + 1) + ". " + listOfWorkOuts.get(i));
            }
            int choice = this.inputManager.readIntegerFromInput("Enter the workout number to delete: ");
            if (choice >= 1 && choice <= listOfWorkOuts.size()) {
                Workout workoutToDelete = listOfWorkOuts.get(choice - 1);
                workoutRepository.deleteWorkout(currentUser, workoutToDelete.getId());
                System.out.println("Workout deleted.");
                auditService.addAuditRecord(new AuditRecord(currentUser.getId(), "delete his workout", "currentTimestamp"));
            } else {
                System.out.println("Invalid choice. Workout not deleted.");
            }
        }
    }

    public void updateWorkout(User currentUser) throws ParseException {
        System.out.println("Updating a workout:");
        List<Workout> listOfWorkOuts = workoutRepository.getAllWorkouts(currentUser);
        if (listOfWorkOuts.isEmpty()) {
            System.out.println("No workouts to update.");
        } else {
            System.out.println("Choose the workout to update:");
            for (int i = 0; i < listOfWorkOuts.size(); i++) {
                System.out.println((i + 1) + ". " + listOfWorkOuts.get(i));
            }
            int choice = this.inputManager.readIntegerFromInput("Enter the workout number to update: ");
            if (choice >= 1 && choice <= listOfWorkOuts.size()) {
                Workout workoutToUpdate = listOfWorkOuts.get(choice - 1);
                System.out.println("Enter the updated information:");
                Date date = inputManager.readDateFromInput("Enter the date (dd.MM.yyyy): ");
                Set<String> availableWorkoutTypes = workoutTypeService.getWorkoutTypes();
                String type = inputManager.readWorkoutTypeFromInput(availableWorkoutTypes);
                int duration = inputManager.readIntegerFromInput("Enter the duration in minutes: ");
                int caloriesBurned = inputManager.readIntegerFromInput("Enter the calories burned: ");
                String additionalInfo = inputManager.readString("Enter additional information (optional): ");

                // Create an updated workout object
                Workout updatedWorkout = new Workout(
                        workoutToUpdate.getId(),
                        date,
                        type,
                        duration,
                        caloriesBurned,
                        additionalInfo,
                        currentUser.getId() // Corrected to use currentUser instead of workoutToUpdate.getWhoCreatedId()
                );

                // Call the repository method to update the workout
                workoutRepository.updateWorkout(updatedWorkout);
                System.out.println("Workout updated.");
                auditService.addAuditRecord(new AuditRecord(currentUser.getId(), "update his workout", "currentTimestamp"));
            } else {
                System.out.println("Invalid choice. Workout not updated.");
            }
        }
    }



    public void showStatistics(Date startDate, Date endDate) {
        List<Workout> workouts = workoutRepository.getAllWorkouts(currentUser);
        int totalCaloriesBurned = 0;

        for (Workout workout : workouts) {
            // Check if the workout date is within the specified range
            Date workoutDate = workout.getDate();
            if (workoutDate.compareTo(startDate) >= 0 && workoutDate.compareTo(endDate) <= 0) {
                totalCaloriesBurned += workout.getCaloriesBurned();
            }
        }

        System.out.println("Statistics for the period from " + startDate + " to " + endDate + ":");
        System.out.println("Total calories burned: " + totalCaloriesBurned);
        auditService.addAuditRecord(new AuditRecord(currentUser.getId(), "the user requested training statistics", "currentTimestamp"));
    }

}
