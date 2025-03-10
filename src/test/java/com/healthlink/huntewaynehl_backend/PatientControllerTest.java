package com.healthlink.huntewaynehl_backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthlink.huntewaynehl_backend.model.Patient;
import com.healthlink.huntewaynehl_backend.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PatientRepository patientRepository;

    @Test
    void testRegisterPatient() throws Exception {
        Patient patient = new Patient();
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setEmail("john.doe@example.com");
        patient.setPassword("securepass");
        patient.setGender("Male");
        patient.setDob(LocalDate.ofEpochDay(1980));
        patient.setPhone("123-456-7890");
        patient.setAddress("123 Elm Street");
        patient.setInsurance("InsuranceProvider");

        mockMvc.perform(post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com")); // Validate the response fields
    }

//    @Test
//    void testGetAllPatients() throws Exception {
//        Patient patient1 = new Patient();
//        patient1.setFirstName("John");
//        patient1.setLastName("Doe");
//        patient1.setEmail("john.doe@example.com");
//        patientRepository.save(patient1);
//
//        Patient patient2 = new Patient();
//        patient2.setFirstName("Jane");
//        patient2.setLastName("Doe");
//        patient2.setEmail("jane.doe@example.com");
//        patientRepository.save(patient2);
//
//        mockMvc.perform(get("/patients")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2))
//                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"))
//                .andExpect(jsonPath("$[1].email").value("jane.doe@example.com"));
//    }
//
//    @Test
//    void testDeletePatient() throws Exception {
//        Patient patient = new Patient();
//        patient.setFirstName("John");
//        patient.setLastName("Doe");
//        patient.setEmail("john.doe@example.com");
//        Patient savedPatient = patientRepository.save(patient);
//
//        mockMvc.perform(delete("/patients/" + savedPatient.getId()))
//                .andExpect(status().isOk());
//
//        mockMvc.perform(get("/patients/" + savedPatient.getId()))
//                .andExpect(status().isNotFound());
//    }
}

//package com.healthlink.huntewaynehl_backend.controller;

//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.healthlink.huntewaynehl_backend.controller.SignupPageController;
//import com.healthlink.huntewaynehl_backend.model.Patient;
//import com.healthlink.huntewaynehl_backend.repository.PatientRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(SignupPageController.class)
//class SignupPageControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Mock
//    private PatientRepository patientRepository;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @InjectMocks
//    private SignupPageController signupPageController;
//
//    private ObjectMapper objectMapper;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        objectMapper = new ObjectMapper();
//    }
//
//    @Test
//    void handlePatientSignup_ShouldSavePatientAndRedirectToLogin() throws Exception {
//        // Arrange
//        String firstName = "John";
//        String lastName = "Doe";
//        String email = "john.doe@example.com";
//        String password = "Password123!";
//        String encodedPassword = "encodedPassword123!";
//        String dob = "1990";
//        String gender = "Male";
//        String phone = "1234567890";
//        String address = "123 Main St";
//        String insurance = "HealthIns";
//        String ssn = "123-45-6789";
//
//        // Mock the password encoding
//        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
//
//        // Act
//        mockMvc.perform(post("/patients")
//                        .param("firstName", firstName)
//                        .param("lastName", lastName)
//                        .param("email", email)
//                        .param("password", password)
//                        .param("dob", dob)
//                        .param("gender", gender)
//                        .param("phone", phone)
//                        .param("address", address)
//                        .param("insurance", insurance)
//                        .param("ssn", ssn)
//                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
//                .andExpect(status().is3xxRedirection()) // Expecting redirect to /login
//                .andExpect(redirectedUrl("/login"))
//                .andExpect(model().attribute("message", "Patient signup successful!"));
//
//        // Assert
//        ArgumentCaptor<Patient> patientArgumentCaptor = ArgumentCaptor.forClass(Patient.class);
//        verify(patientRepository, times(1)).save(patientArgumentCaptor.capture());
//
//        Patient capturedPatient = patientArgumentCaptor.getValue();
//        assertEquals(firstName, capturedPatient.getFirstName());
//        assertEquals(lastName, capturedPatient.getLastName());
//        assertEquals(email, capturedPatient.getEmail());
//        assertEquals(encodedPassword, capturedPatient.getPassword()); // Confirm the password encoded successfully
//        assertEquals(Integer.parseInt(dob), capturedPatient.getDob());
//        assertEquals(gender, capturedPatient.getGender());
//        assertEquals(phone, capturedPatient.getPhone());
//        assertEquals(address, capturedPatient.getAddress());
//        assertEquals(insurance, capturedPatient.getInsurance());
//        assertEquals(ssn, capturedPatient.getSsn());
//    }
//}