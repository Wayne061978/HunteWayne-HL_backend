package com.healthlink.huntewaynehl_backend.controller;

import com.healthlink.huntewaynehl_backend.model.Office;
import com.healthlink.huntewaynehl_backend.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offices")
public class OfficeController {

    @Autowired
    private OfficeService officeService;

    @PostMapping
    public Office addOffice(@RequestBody Office office) {
        return officeService.addOffice(office);
    }

    @GetMapping("/{id}")
    public Office getOfficeById(@PathVariable Long id) {
        return officeService.getOfficeById(id);
    }

    @GetMapping
    public List<Office> getAllOffices() {
        return officeService.getAllOffices();
    }

    @DeleteMapping("/{id}")
    public void deleteOffice(@PathVariable Long id) {
        officeService.deleteOffice(id);
    }
}