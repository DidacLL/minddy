package cat.itacademy.minddy.controllers;

import cat.itacademy.minddy.data.html.ProjectStructure;
import cat.itacademy.minddy.data.html.TodayReport;
import cat.itacademy.minddy.data.html.UserData;
import org.apache.hc.core5.http.HttpException;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

public interface TestController {

    public ResponseEntity<String> ping(String ping);

    public ResponseEntity<UserData> getUserData(String userId, String date);

    public String getUserId(String userName) throws HttpException;

    public ResponseEntity<UserData> registerNewUser(String userId, String userName, String uiConfig) ;

    public  ResponseEntity<ProjectStructure> getProjectStructure(String userId);
    public ResponseEntity<TodayReport> getTodayReport(String userId, LocalDate date);
}
