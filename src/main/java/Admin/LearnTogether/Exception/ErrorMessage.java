package Admin.LearnTogether.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
  Created by Luvbert
*/
@Data
@AllArgsConstructor
public class ErrorMessage {
    private int errorCode;
    private String errorMessage;
}
