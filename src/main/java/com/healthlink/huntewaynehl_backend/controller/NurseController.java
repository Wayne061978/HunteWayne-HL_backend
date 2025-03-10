//package com.healthlink.huntewaynehl_backend.controller;
//
//import com.healthlink.huntewaynehl_backend.model.Nurse;
//import com.healthlink.huntewaynehl_backend.service.NurseService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/nurses")
//public class NurseController {
//
//    @Autowired
//    private NurseService nurseService;
//
//    @PostMapping
//    public Nurse addNurse(@RequestBody Nurse nurse) {
//        return nurseService.addNurse(nurse);
//    }
//
//    @GetMapping("/{id}")
//    public Nurse getNurseById(@PathVariable Long id) {
//        return nurseService.getNurseById(id);
//    }
//
//    @GetMapping
//    public List<Nurse> getAllNurses() {
//        return nurseService.getAllNurses();
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteNurse(@PathVariable Long id) {
//        nurseService.deleteNurse(id);
//    }
//}