package application.project.util.Mapper;

import application.project.domain.Skill;
import application.project.domain.dto.response.ResultReturnedDTO;

public class SkillMapper {
    public static ResultReturnedDTO toSkillResponse(Skill skill) {
        // Implement mapping logic as needed
        return new ResultReturnedDTO(null, skill);
    }
}
