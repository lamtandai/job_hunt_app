package application.project.controller;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import application.project.domain.dto.request.ReqSkill;
import application.project.domain.dto.response.ResultReturnedDTO;
import application.project.service.SkillService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;



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

    @PatchMapping("/{id}")
    public ResponseEntity<ResultReturnedDTO>  updateSkill(@PathVariable Integer id, @RequestBody ReqSkill skill) {
        return ResponseEntity.ok().body(this.SkillService.updateSkill(id, skill));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ResultReturnedDTO> deleteSkill(@PathVariable Integer id){
        return ResponseEntity.ok().body(this.SkillService.deleteSkill(id));

    }
}
