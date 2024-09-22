package com.neoflex.vacation_calculator;


import com.neoflex.vacation_calculator.controller.VacationController;
import com.neoflex.vacation_calculator.dto.ApiResponse;
import com.neoflex.vacation_calculator.dto.VacationPayoutResponse;
import com.neoflex.vacation_calculator.service.VacationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class VacationControllerTest {

    @InjectMocks
    private VacationController vacationController;

    @Mock
    private VacationService vacationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalculateVacationPay_Success() {
        double averageSalary = 60000;
        int vacationDays = 14;
        double expectedPayout = 12000;

        when(vacationService.calculateVacationPay(anyDouble(), anyInt(), any(), any())).thenReturn(expectedPayout);

        ResponseEntity<ApiResponse<VacationPayoutResponse>> response = vacationController.calculateVacationPay(
                averageSalary,
                vacationDays,
                LocalDate.of(2024, 5, 1),
                LocalDate.of(2024, 5, 10)
        );

        assertEquals(200, response.getStatusCode().value());
        assertEquals(expectedPayout, response.getBody().getData().getPayout());
    }

    @Test
    public void testCalculateVacationPay_NullDates() {
        double averageSalary = 60000;
        int vacationDays = 54;
        double expectedPayout = 108000;

        when(vacationService.calculateVacationPay(anyDouble(), anyInt(), any(), any())).thenReturn(expectedPayout);

        ResponseEntity<ApiResponse<VacationPayoutResponse>> response = vacationController.calculateVacationPay(
                averageSalary,
                vacationDays,
                null,
                null
        );

        assertEquals(200, response.getStatusCode().value());
        assertEquals(expectedPayout, response.getBody().getData().getPayout());
    }
}