package application.project.domain.Exception;

public class EmailExistException extends Exception{
    public EmailExistException(String message) {
        super(message);
    }
}
