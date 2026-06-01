package io.student.rococo.config;

import io.student.rococo.service.api.CountryService;
import io.student.rococo.service.api.UsersClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceLocator {
    
    @Autowired
    private CountryService countryService;
    
    @Autowired
    private UsersClient usersClient;
    
    public CountryService getCountryService() {
        return countryService;
    }
    
    public UsersClient getUsersClient() {
        return usersClient;
    }
}
