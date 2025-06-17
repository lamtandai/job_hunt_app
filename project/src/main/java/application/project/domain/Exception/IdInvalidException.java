package application.project.domain.Exception;

public class IdInvalidException extends RuntimeException  {
    public IdInvalidException(Long id){
        super("Id: " + id + " is not valid or not found!");
    }
}
