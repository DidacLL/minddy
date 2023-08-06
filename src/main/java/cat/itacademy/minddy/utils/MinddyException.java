package cat.itacademy.minddy.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MinddyException extends Exception{
    private int errorCode;
    private String errorMessage;
}
