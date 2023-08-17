package cat.itacademy.minddy.controllers;

import cat.itacademy.minddy.data.html.UserData;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;

@Tag(name="User Data Service",description = "Provides user data")

public interface UserDataController {


    @Tag(name = "User Data",
            description = "Retrieves basic dashboard data, including user name, project structure, today tasks and notifications")
    @Parameters(value = {
            @Parameter(name = "Today",description = "Today date from client side",required = true)
    })
    ResponseEntity<UserData> getUserData(Authentication auth, LocalDate today);
}
