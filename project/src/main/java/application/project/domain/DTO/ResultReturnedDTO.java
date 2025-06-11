package application.project.domain.DTO;


public class ResultReturnedDTO {
    private Object metadata;
    private Object result;
    
    public ResultReturnedDTO() {
    }
    public ResultReturnedDTO(Object metadata, Object result) {
        this.metadata = metadata;
        this.result = result;
    }
    public Object getMetadata() {
        return metadata;
    }
    public void setMetadata(Object metadata) {
        this.metadata = metadata;
    }
    public Object getResult() {
        return result;
    }
    public void setResult(Object result) {
        this.result = result;
    }
    
}
