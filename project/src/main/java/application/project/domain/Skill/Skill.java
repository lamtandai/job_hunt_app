package application.project.domain.Skill;

public class Skill {
    private int id;
    private String name;
    private Boolean activated;
    
    public Skill(int id, String name, Boolean activated) {
        this.id = id;
        this.name = name;
        this.activated = activated;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }
}
