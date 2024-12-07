package com.webbanhangnongsan.vn.webbanhangnongsan.controller.admin;

import com.webbanhangnongsan.vn.webbanhangnongsan.service.admin.ChartAPIService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/admin")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ChartAPIController {
    ChartAPIService chartAPIService;

    @GetMapping("/bar/monthData")
    public ResponseEntity<List<Object[]>> showDataMonth() {
        List<Object[]> dataMonth = chartAPIService.showDataMonth();
        return new ResponseEntity<>(dataMonth, HttpStatus.OK);
    }

    @GetMapping("/bar/quarterData")
    public ResponseEntity<List<Object[]>> showQuarterData() {
        List<Object[]> quarterMonth = chartAPIService.showDataQuarter();
        return new ResponseEntity<>(quarterMonth, HttpStatus.OK);
    }

    @GetMapping("/bar/catergoriesData")
    public ResponseEntity<List<Object[]>> showCategoriesData() {
        List<Object[]> catergoriesMonth = chartAPIService.showCategoriesData();
        return ResponseEntity.ok(catergoriesMonth);
    }
}
