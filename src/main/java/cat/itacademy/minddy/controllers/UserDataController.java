package cat.itacademy.minddy.controllers;

import cat.itacademy.minddy.data.html.requests.SearchRequest;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;

@Tag(name="User Data Service",description = "Provides user data")
public interface UserDataController {


    @Tag(name = "User Data",
            description = "Retrieves basic dashboard data, including user name and UI Configuration (managed by frontend UI)")
    @Operation(summary = "GET ACCOUNT: Get Any account by it's ID or AccountNumber")
    ResponseEntity<?> getUserData(Authentication auth, LocalDate today);
    @Hidden
    ResponseEntity<?> getUserData(LocalDate today);


    @Tag(name = "Project Structure",
            description = "Retrieves all projects data formatted as a ProjectNode Tree linked list")
     ResponseEntity<?> getUserProjects(Authentication auth, LocalDate today);
    @Hidden
     ResponseEntity<?> getUserProjects( LocalDate today);
    @Tag(name = "Today Report",
        description = "Retrieves Today tasks and stats for notifications")
     ResponseEntity<?> getTodayReport(Authentication auth,LocalDate today,int elements);
    @Hidden
     ResponseEntity<?> getTodayReport(LocalDate today,int elements);
    @Tag(name = "New user",
            description = "Creates new user if it doesn't exists yet, otherwise return exception")
     ResponseEntity<?> registerNewUser(Authentication auth,String uiConfig);
    @Tag(name = "Update user configuration",
            description = "Updates user UI configuration from existing user")
     ResponseEntity<?> updateUserConfig(Authentication auth, String uiConfig);
    @Hidden
     ResponseEntity<?> updateUserConfig(String uiConfig);


    @Tag(name = "Search Projects",
            description = "Retrieves projects minimal from its name and/or tags")
    
      ResponseEntity<?> search(Authentication auth, SearchRequest request);//todo change to request body class and general search for all
}
