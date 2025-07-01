package application.project.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import application.project.domain.Exception.InvalidException;
import application.project.domain.Skill;
import application.project.domain.dto.request.ReqSkill;
import application.project.domain.dto.response.PageMetadata;
import application.project.domain.dto.response.ResSkillDTO;
import application.project.domain.dto.response.RestResponse;
import application.project.domain.dto.response.ResultReturnedDTO;
import application.project.repository.JobPostSkillRepository;
import application.project.repository.SkillRepository;
import application.project.repository.JdbcSpecification.JdbcFilterSpecification;
import application.project.util.Mapper.SkillMapper;


@Service
public class SkillService {
    private final SkillRepository skillRepository;
    private final JobPostSkillRepository jp_skilSkillRepository;

    public SkillService(SkillRepository skillRepository, JobPostSkillRepository jp_skilSkillRepository) {
        this.skillRepository = skillRepository;
        this.jp_skilSkillRepository = jp_skilSkillRepository;
    }

    public ResultReturnedDTO getSkillById(int id) {
        Optional<Skill> skillOpt = skillRepository.findById(id);
        return new ResultReturnedDTO(null,
                skillOpt.orElseThrow(
                        () -> new InvalidException(String.valueOf(id))));

    }

    public <T> ResultReturnedDTO getAllSkill(JdbcFilterSpecification<T> spec, Pageable pageable) {
        Page<T> skillPage = this.skillRepository.findAll(spec, pageable);
        PageMetadata meta = PageMetadata
                .builder()
                .currentPage(pageable.getPageNumber() + 1)
                .pageSize(pageable.getPageSize())
                .totalPage(skillPage.getTotalPages())
                .totalElement(skillPage.getNumberOfElements())
                .build();
        
        @SuppressWarnings("unchecked")
        Page<Skill> casted = (Page<Skill>) skillPage;
        List<ResSkillDTO> skillDtos = casted
            .getContent()
            .stream()
            .map(SkillMapper::toSkillResponse)
            .collect(Collectors.toList());

        return new ResultReturnedDTO(meta, skillDtos);

    }

    public ResultReturnedDTO createSkill(ReqSkill skill) {
        Optional<Skill> skillOpt = skillRepository.findByName(skill.getSk_name());

        if (skillOpt.isPresent()) {
            throw new InvalidException(skill.getSk_name());
        }

        return getSkillById(skillRepository.create(skill).get());

    }

    public ResultReturnedDTO updateSkill(int id, ReqSkill skill) {
        Optional<Skill> opt = this.skillRepository.findById(id);

        if (opt.isEmpty()) {
            throw new InvalidException(String.valueOf(id));
        }

        Optional<Skill> skillOpt = this.skillRepository.findByName(skill.getSk_name());

        if (skillOpt.isPresent()) {
            throw new InvalidException(skill.getSk_name());
        }

        this.skillRepository.update(skill, id);

        return new ResultReturnedDTO(null, this.skillRepository.findById(id).get());
    }

    public ResultReturnedDTO deleteSkill(int id) {
        Optional<Skill> opt = this.skillRepository.findById(id);

        if (opt.isEmpty()) {
            throw new InvalidException(String.valueOf(id));
        }
        this.jp_skilSkillRepository.deleteSkillsOfJobPostBySkillId(id);
        this.skillRepository.delete(id);
        return new ResultReturnedDTO(null, RestResponse.builder().setMessage("Delete successfully!").build());
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
