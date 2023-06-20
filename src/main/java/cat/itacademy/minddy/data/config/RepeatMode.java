package cat.itacademy.minddy.data.config;

import cat.itacademy.minddy.data.dao.Task;

import java.time.LocalDate;

public enum RepeatMode {
    DAYS,
    WEEKS,
    MONTHS,
    YEARS;

    public boolean checkDate(LocalDate date, Task task){
        return switch (this){
            case DAYS -> (date.toEpochDay() - task.getDate().toEpochDay()) % task.getRepeatValue()==0L;
            case WEEKS -> date.getDayOfWeek()==task.getDate().getDayOfWeek();
            case MONTHS -> date.getDayOfMonth()==task.getDate().getDayOfMonth();
            case YEARS -> date.getDayOfYear()==task.getDate().getDayOfYear();
        };
    }
//    public boolean checkDate(LocalDate date, TaskDTO task){
//        return switch (this){
//            case DAYS -> (date.toEpochDay() - task.getDate().toEpochDay()) % task.getConfig().getRepeatValue()==0L;
//            case WEEKS -> date.getDayOfWeek()==task.getDate().getDayOfWeek();
//            case MONTHS -> date.getDayOfMonth()==task.getDate().getDayOfMonth();
//            case YEARS -> date.getDayOfYear()==task.getDate().getDayOfYear();
//        };
//    }



}
