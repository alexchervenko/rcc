package io.student.rococo.service.api;

import io.student.rococo.model.UserJson;

public interface UsersClient {
    UserJson getCurrentUser();
    
    UserJson updateUserProfile(UserJson user);
    
    UserJson getUserById(String userId);
}