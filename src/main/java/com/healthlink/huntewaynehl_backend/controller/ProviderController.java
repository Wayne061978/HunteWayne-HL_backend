//package com.healthlink.huntewaynehl_backend.controller;
//
//import com.healthlink.huntewaynehl_backend.model.Provider;
//import com.healthlink.huntewaynehl_backend.service.ProviderService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/providers")
//public class ProviderController {
//
//    @Autowired
//    private ProviderService providerService;
//
//    @PostMapping
//    public Provider addProvider(@RequestBody Provider provider) {
//        return providerService.addProvider(provider);
//    }
//
//    @GetMapping("/{id}")
//    public Provider getProviderById(@PathVariable Long id) {
//        return providerService.getProviderById(id);
//    }
//
//    @GetMapping
//    public List<Provider> getAllProviders() {
//        return providerService.getAllProviders();
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteProvider(@PathVariable Long id) {
//        providerService.deleteProvider(id);
//    }
//}