package cat.itacademy.minddy.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public class MinddyException extends Exception{
    private int errorCode;
    private String errorMessage;

    public static Map<String, String> getErrorResponse(MinddyException e) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", e.getErrorMessage());
        errorResponse.put("code", String.valueOf(e.getErrorCode()));
        return errorResponse;
    }
}
