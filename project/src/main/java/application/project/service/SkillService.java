package application.project.service;


import java.util.Optional;

import org.springframework.stereotype.Service;

import application.project.domain.Exception.InvalidException;
import application.project.domain.Skill;
import application.project.domain.dto.request.ReqSkill;
import application.project.domain.dto.response.RestResponse;
import application.project.domain.dto.response.ResultReturnedDTO;
import application.project.repository.SkillRepository;

@Service
public class SkillService {
    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }
  

    public ResultReturnedDTO getSkillById(int id) {
        Optional<Skill> skillOpt = skillRepository.findById(id);
        return new ResultReturnedDTO(null, 
            skillOpt.orElseThrow(
                () -> new InvalidException(String.valueOf(id))));
        
    }

    public ResultReturnedDTO createSkill(ReqSkill skill) {
        Optional <Skill> skillOpt = skillRepository.findByName(skill.getSk_name());
        
        if (skillOpt.isPresent()){
            throw new InvalidException(skill.getSk_name());
        }

        return getSkillById(skillRepository.create(skill).get());
        
    }

    public ResultReturnedDTO updateSkill(int id, ReqSkill skill) {
        Optional<Skill> opt =  this.skillRepository.findById(id);

        if (opt.isEmpty()){
            throw new InvalidException(String.valueOf(id));
        }

        Optional<Skill> skillOpt = this.skillRepository.findByName(skill.getSk_name());
        
        if (skillOpt.isPresent()){
            throw new InvalidException(skill.getSk_name());
        }

        this.skillRepository.update(skill, id);

        return new ResultReturnedDTO(null, this.skillRepository.findById(id).get());
    }

    public ResultReturnedDTO deleteSkill(int id) {
        Optional<Skill> opt =  this.skillRepository.findById(id);

        if (opt.isEmpty()){
            throw new InvalidException(String.valueOf(id));
        }
        
        this.skillRepository.delete(id);

        return new  ResultReturnedDTO(null, RestResponse.builder().setMessage("Delete successfully!").build());
    }
   
    public ResultReturnedDTO findBySkillName(String name) {
        Optional<Skill> skillOpt = this.skillRepository.findByName(name);
        ResultReturnedDTO result = new ResultReturnedDTO();

        if (skillOpt.isPresent()) {
            result.setMetadata(null);
            result.setResult(skillOpt.get());
        }
        return result;
    }


}
