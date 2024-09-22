package com.neoflex.vacation_calculator.controller;

import com.neoflex.vacation_calculator.dto.ApiResponse;
import com.neoflex.vacation_calculator.dto.VacationPayoutResponse;
import com.neoflex.vacation_calculator.service.VacationService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1")
public class VacationController {

    @Autowired
    private VacationService vacationService;

    @GetMapping("/vacation-payout")
    public ResponseEntity<ApiResponse<VacationPayoutResponse>> calculateVacationPay(
            @Parameter(description = "Средняя зарплата за последние 12 месяцев") @RequestParam double averageSalary,
            @Parameter(description = "Количество дней отпуска") @RequestParam int vacationDays,
            @Parameter(description = "Дата начала отпуска", schema = @Schema(type = "string", format = "date"))
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "Дата окончания отпуска", schema = @Schema(type = "string", format = "date"))
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        double vacationPay = vacationService.calculateVacationPay(averageSalary, vacationDays, startDate, endDate);

        VacationPayoutResponse payoutResponse = new VacationPayoutResponse(vacationPay);

        ApiResponse<VacationPayoutResponse> response = new ApiResponse<>("success", payoutResponse);
        return ResponseEntity.ok(response);
    }
}
