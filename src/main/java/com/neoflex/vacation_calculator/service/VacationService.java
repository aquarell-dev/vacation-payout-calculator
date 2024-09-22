package com.neoflex.vacation_calculator.service;

import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

@Service
public class VacationService {
    // При расчете по датам, можно учитывать сколько дней в конкретном месяце, но тут упрощу себе задачу
    private static final double DAYS_IN_MONTH = 30;

    // В идеале подхатывать данные с какой-нибудь сторонней апишки
    private static final Set<LocalDate> HOLIDAYS = Set.of(
            LocalDate.of(2024, 1, 1),
            LocalDate.of(2024, 5, 1),
            LocalDate.of(2024, 5, 9),
            LocalDate.of(2024, 7, 4),
            LocalDate.of(2024, 5, 11),
            LocalDate.of(2024, 1, 12),
            LocalDate.of(2024, 8, 21)
    );

    public double calculateVacationPay(double averageSalary, int vacationDays, LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
            return calculateBasicVacationPay(averageSalary, vacationDays);
        }

        int actualVacationDays = calculateWorkingDaysInRange(startDate, endDate);

        return calculateBasicVacationPay(averageSalary, actualVacationDays);
    }

    private double calculateBasicVacationPay(double averageSalary, int vacationDays) {
        return (averageSalary / DAYS_IN_MONTH) * vacationDays;
    }

    private int calculateWorkingDaysInRange(LocalDate startDate, LocalDate endDate) {
        int workingDays = 0;

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            if (isDayOff(date)) continue;

            workingDays++;
        }

        return workingDays;
    }

    private boolean isDayOff(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY || HOLIDAYS.contains(date);
    }
}