package cat.itacademy.minddy.services;

import cat.itacademy.minddy.data.config.DateLog;
import cat.itacademy.minddy.data.dao.User;
import cat.itacademy.minddy.data.html.UserData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@Sql(scripts = "/testscript.sql")
@SpringBootTest
//@Sql(scripts = "clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class UserServiceTest {
    @Autowired
    UserService service;
        static User user = new User("FAUX_USER",
                "Faux Simile",
                "",
                new DateLog()
        );

    @Test
    @DisplayName("Get User Data")
    void getUserData_test() {
        UserData res= null;
        System.out.print("Get DEFAULT user data ...");
        try {
            res = service.getUserData(LocalDate.now(),"1234567890");
            System.out.println(" OK");
        } catch (Exception e) {
            System.out.println(" NOK");
            throw new RuntimeException(e);
        }
        System.out.print("Checking default userName:...");
        assertEquals("Cetato",res.getUserName());
        System.out.println(" OK");

    }

    @Test
    @DisplayName(value = "Register new User")
    void registerNewUser_test() {
        System.out.print("Registering user... ");
        assertDoesNotThrow(()->service.registerNewUser(user.getId(), user.getName(), ""));
        System.out.println("OK");
        System.out.print("Checking saved user... ");
        assertDoesNotThrow(()->service.getUserData(LocalDate.now(),user.getId()));
        System.out.println("OK");
    }

    @Test
    @DisplayName(value = "Delete User")
    void deleteUser_test() {
        try{
            System.out.print("Registering user...");
            service.registerNewUser(user.getId(), user.getName(), "");
            System.out.println("OK");
        }catch(Exception ignored){
            System.out.println("NOK");
        };
        System.out.print("Deleting user...");
        assertTrue(service.deleteUser(false,user.getId()));
        System.out.println("OK");
    }

    @Test
    @DisplayName(value = "Exist User OK")
    void existUserOK_test() {try{
        System.out.print("Registering user...");
        service.registerNewUser(user.getId(), user.getName(), "");
        System.out.println("OK");
    }catch(Exception ignored){
        System.out.println("NOK");
    };
        System.out.print("Checking user persistence...");
        assertTrue(service.existUser(user.getId()));
        System.out.println("OK");
    }

    @Test
    @DisplayName(value = "Exist User NOK")
    void existUserNOK_test() {
        System.out.print("Checking wrong user ...");
        assertFalse(service.existUser("WRONGIDREFERENCE"));
        System.out.println("OK");
    }
}
