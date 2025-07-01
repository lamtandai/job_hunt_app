package application.project.util.Mapper;

import application.project.domain.Skill;
import application.project.domain.dto.response.ResSkillDTO;

public class SkillMapper {
    public static ResSkillDTO toSkillResponse(Skill skill){
        return new ResSkillDTO(skill);
    }
}
