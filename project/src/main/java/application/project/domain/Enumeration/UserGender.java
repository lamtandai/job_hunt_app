package application.project.domain.Enumeration;

public enum UserGender {
    MALE("Male"), 
    FEMAlE("Female"), 
    OTHER("Other"),
    BLANK("blank");

    private final String value;

    UserGender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
