package com.healthlink.huntewaynehl_backend.service;

import com.healthlink.huntewaynehl_backend.model.Provider;
import com.healthlink.huntewaynehl_backend.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderService {

    @Autowired
    private ProviderRepository providerRepository;

    public Provider addProvider(Provider provider) {
        return providerRepository.save(provider);
    }

    public Provider getProviderById(Long id) {
        return providerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Provider not found with ID: " + id));
    }

    public List<Provider> getAllProviders() {
        return providerRepository.findAll();
    }

    public void deleteProvider(Long id) {
        providerRepository.deleteById(id);
    }
}