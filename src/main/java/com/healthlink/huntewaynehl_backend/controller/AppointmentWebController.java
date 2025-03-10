package com.healthlink.huntewaynehl_backend.controller;

import com.healthlink.huntewaynehl_backend.model.Appointment;
import com.healthlink.huntewaynehl_backend.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class AppointmentWebController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/appointment") // ✅ Matches Thymeleaf link
    public String showAppointments(Model model) {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        model.addAttribute("appointments", appointments);
        return "appointment"; // ✅ Must match `appointment.html`
    }
}
