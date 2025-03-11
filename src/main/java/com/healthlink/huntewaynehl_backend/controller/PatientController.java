//package com.healthlink.huntewaynehl_backend.controller;
//
//import com.healthlink.huntewaynehl_backend.model.Appointment;
//import com.healthlink.huntewaynehl_backend.model.Patient;
//import com.healthlink.huntewaynehl_backend.service.PatientService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/patients")
//public class PatientController {
//
//    @Autowired
//    private PatientService patientService;
//
//    @PostMapping("/register")
//    public Patient registerPatient(@RequestBody Patient patient) {
//        return patientService.registerPatient(patient);
//    }
//
//    @GetMapping("/{id}")
//    public Patient getPatientById(@PathVariable Long id) {
//        return patientService.getPatientById(id);
//    }
//
//    @GetMapping
//    public List<Patient> getAllPatients() {
//        return patientService.getAllPatients();
//    }
//
//    @DeleteMapping("/{id}")
//    public void deletePatient(@PathVariable Long id) {
//        patientService.deletePatient(id);
//    }
//
//    @GetMapping("/{id}/appointments")
//    public List<Appointment> getAppointments(@PathVariable Long id) {
//        return patientService.getAppointmentsOfPatient(id);
//    }
//}

//package com.healthlink.huntewaynehl_backend.controller;
//
//import com.healthlink.huntewaynehl_backend.model.Patient;
//import com.healthlink.huntewaynehl_backend.repository.PatientRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Optional;
//
//@Controller
//@RequestMapping("/patient")
//public class PatientController {
//
//    @Autowired
//    private PatientRepository patientRepository;
//
//    // ✅ Show Profile Update Form
//    @GetMapping("/update-profile")
//    public String showUpdateProfileForm(Model model, Authentication authentication) {
//        String email = authentication.getName();
//        Optional<Patient> patient = patientRepository.findByEmailIgnoreCase(email);
//
//        if (patient.isPresent()) {
//            model.addAttribute("patient", patient.get());
//            return "update-profile"; // ✅ Ensure this Thymeleaf file exists
//        }
//
//        return "redirect:/patient_dashboard/patient?error=Patient not found";
//    }
//
//    // ✅ Handle Profile Update Submission
//    @PostMapping("/update-profile")
//    public String updateProfile(@ModelAttribute Patient updatedPatient, Authentication authentication) {
//        String email = authentication.getName();
//        Optional<Patient> existingPatient = patientRepository.findByEmailIgnoreCase(email);
//
//        if (existingPatient.isPresent()) {
//            Patient patient = existingPatient.get();
//
//            // Update patient details
//            patient.setFirstName(updatedPatient.getFirstName());
//            patient.setLastName(updatedPatient.getLastName());
//            patient.setPhone(updatedPatient.getPhone());
//            patient.setAddress(updatedPatient.getAddress());
//            patient.setInsurance(updatedPatient.getInsurance());
//
//            patientRepository.save(patient);
//            return "redirect:/patient_dashboard/patient?success=Profile updated successfully";
//        }
//
//        return "redirect:/patient_dashboard/patient?error=Error updating profile";
//    }
//}
