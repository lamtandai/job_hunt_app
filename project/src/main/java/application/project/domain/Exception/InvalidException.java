package application.project.domain.Exception;

public class InvalidException extends RuntimeException {
    public InvalidException(String value) {
        super('"' + value + '"' + " is not valid or not found!");
    }
}
