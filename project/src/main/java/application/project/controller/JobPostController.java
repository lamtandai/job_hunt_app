package application.project.controller;

import org.springframework.web.bind.annotation.RestController;

import application.project.domain.dto.request.ReqJobPostDTO;
import application.project.service.JobPostService;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import application.project.domain.dto.response.ResultReturnedDTO;


@RestController
public class JobPostController {
    private final JobPostService jpService;

    public JobPostController(JobPostService jpService) {
        this.jpService = jpService;
    }

    @PostMapping()
    public ResponseEntity<ResultReturnedDTO> createJobPost(@RequestBody ReqJobPostDTO jp) {
        return ResponseEntity.ok().body(this.jpService.handleCreateJobPost(jp));
        
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ResultReturnedDTO> getJobPost(@PathVariable Long id) {
        return ResponseEntity.ok().body(this.jpService.handleGetAJobPost(id));
        
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResultReturnedDTO> modifyJobPost(@Valid @RequestBody ReqJobPostDTO jp, @PathVariable Long id) {
        return ResponseEntity.ok().body(this.jpService.handleUpdateJobPost(id, jp));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultReturnedDTO> createJobPost( @PathVariable Long id) {
        return ResponseEntity.ok().body(this.jpService.handleDeleteJobPost(id));
    }
    
}
