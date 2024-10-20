package com.webbanhangnongsan.vn.webbanhangnongsan.controller.admin;

import com.webbanhangnongsan.vn.webbanhangnongsan.service.admin.ChartAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class ChartAPIController {
    @Autowired
    ChartAPIService chartAPIService;
    @GetMapping("/chartData")
    public ResponseEntity<List<Object[]>> showDataMonth() {
        List<Object[]> dataMonth = chartAPIService.showDataMonth();
        return new ResponseEntity<>(dataMonth, HttpStatus.OK);
    }
}
