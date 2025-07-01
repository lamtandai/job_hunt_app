package application.project.domain.dto.response;

import application.project.domain.Skill;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ResSkillDTO {
    private final int sk_id;
    private final String sk_name;

    public ResSkillDTO(Skill skill){
        this.sk_id = skill.getSk_id();
        this.sk_name = skill.getSk_name();
    }
}
