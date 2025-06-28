package application.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import application.project.domain.dto.request.ReqIndustry;

import application.project.domain.dto.response.ResultReturnedDTO;
import application.project.service.IndustryService;

public class IndustryController {
    private final IndustryService IndustryService;
    
    public IndustryController(application.project.service.IndustryService industryService) {
        IndustryService = industryService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResultReturnedDTO> getSkillById(@PathVariable Integer id){
        return ResponseEntity.ok().body(this.IndustryService.getIdtById(id));
    }
    @PostMapping()
    public ResponseEntity<ResultReturnedDTO> createSkill(@RequestBody ReqIndustry Industry){
        return ResponseEntity.ok().body(this.IndustryService.createIdt(Industry));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResultReturnedDTO>  updateSkill(@PathVariable Integer id, @RequestBody ReqIndustry Industry ) {
        return ResponseEntity.ok().body(this.IndustryService.updateIdt(id, Industry));
    }
    
    @DeleteMapping("/id")
    public ResponseEntity<ResultReturnedDTO> deleteSkill(@PathVariable Integer id){
        return ResponseEntity.ok().body(this.IndustryService.deleteIdt(id));

    }
}
