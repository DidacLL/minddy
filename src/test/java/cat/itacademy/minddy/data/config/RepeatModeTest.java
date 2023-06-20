package cat.itacademy.minddy.data.config;

import cat.itacademy.minddy.data.dao.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static cat.itacademy.minddy.data.config.RepeatMode.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RepeatModeTest {

    @Test
    void checkDate_test() {
        LocalDate today=LocalDate.now();
        LocalDate setDate=today.minusDays(14);
        assertTrue(DAYS.checkDate(today,new Task().setDate(LocalDate.of(setDate.getYear(),setDate.getMonth(),setDate.getDayOfMonth())).setRepeatValue(7)));
        assertTrue(WEEKS.checkDate(today,new Task().setDate(LocalDate.of(setDate.getYear(),setDate.getMonth(),setDate.getDayOfMonth())).setRepeatValue(2)));
        setDate=today.minusWeeks(844);
        assertTrue(WEEKS.checkDate(today,new Task().setDate(LocalDate.of(setDate.getYear(),setDate.getMonth(),setDate.getDayOfMonth())).setRepeatValue(2)));
        setDate=today.minusMonths(9);
        assertTrue(MONTHS.checkDate(today,new Task().setDate(LocalDate.of(setDate.getYear(),setDate.getMonth(),setDate.getDayOfMonth())).setRepeatValue(3)));
        setDate=today.minusYears(30);
        assertTrue(YEARS.checkDate(today,new Task().setDate(LocalDate.of(setDate.getYear(),setDate.getMonth(),setDate.getDayOfMonth())).setRepeatValue(3)));
        assertTrue(YEARS.checkDate(today,new Task().setDate(LocalDate.of(setDate.getYear(),setDate.getMonth(),setDate.getDayOfMonth())).setRepeatValue(1)));
        assertTrue(YEARS.checkDate(today,new Task().setDate(LocalDate.of(setDate.getYear(),setDate.getMonth(),setDate.getDayOfMonth())).setRepeatValue(10)));
        setDate=setDate.minusDays(1);
        assertFalse(YEARS.checkDate(today,new Task().setDate(LocalDate.of(setDate.getYear(),setDate.getMonth(),setDate.getDayOfMonth())).setRepeatValue(1)));
        assertFalse(MONTHS.checkDate(today,new Task().setDate(LocalDate.of(setDate.getYear(),setDate.getMonth(),setDate.getDayOfMonth())).setRepeatValue(1)));
        assertFalse(WEEKS.checkDate(today,new Task().setDate(LocalDate.of(setDate.getYear(),setDate.getMonth(),setDate.getDayOfMonth())).setRepeatValue(1)));
        assertTrue(DAYS.checkDate(today,new Task().setDate(LocalDate.of(setDate.getYear(),setDate.getMonth(),setDate.getDayOfMonth())).setRepeatValue(1)));


    }
}
