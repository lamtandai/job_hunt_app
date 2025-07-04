package application.project.controller;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import application.project.domain.User_account;
import application.project.domain.dto.request.ReqSkill;
import application.project.domain.dto.response.ResultReturnedDTO;
import application.project.repository.JdbcSpecification.JdbcFilterSpecification;
import application.project.service.SkillService;
import application.project.util.CustomAnnotation.Filterable;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import application.project.domain.Skill;



@RestController
public class SkillController {
    private final SkillService SkillService;

    public SkillController(application.project.service.SkillService skillService) {
        SkillService = skillService;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ResultReturnedDTO> getSkillById(@PathVariable Integer id){
        return ResponseEntity.ok().body(this.SkillService.getSkillById(id));
    }
    @PostMapping()
    public ResponseEntity<ResultReturnedDTO> createSkill(@RequestBody ReqSkill skill){
        return ResponseEntity.ok().body(this.SkillService.createSkill(skill));

    }

    @GetMapping()
    public ResponseEntity<ResultReturnedDTO> getAllSkills(
            @Filterable JdbcFilterSpecification<Skill> spec,
            Pageable pageable) {

        ResultReturnedDTO result = this.SkillService.getAllSkill(spec, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResultReturnedDTO>  updateSkill(@PathVariable Integer id, @RequestBody ReqSkill skill) {
        return ResponseEntity.ok().body(this.SkillService.updateSkill(id, skill));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ResultReturnedDTO> deleteSkill(@PathVariable Integer id){
        return ResponseEntity.ok().body(this.SkillService.deleteSkill(id));

    }
}
