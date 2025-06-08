package application.project.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import application.project.domain.Company.Company;
import application.project.domain.DTO.CompanyDTO;
import application.project.service.CompanyService;
import jakarta.validation.Valid;

@RestController
public class CompanyController {
    private final CompanyService companyService;
    
    public CompanyController(CompanyService companyService) {
        
        this.companyService = companyService;
    }

    @PostMapping("/companies")
    public ResponseEntity<Company> createCompany(@Valid @RequestBody CompanyDTO companyDTO){
        return this.companyService.handleCreateCompany(companyDTO)
            .map(newCompany -> ResponseEntity.status(HttpStatus.CREATED).body(newCompany))
            .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        
    }

    @GetMapping("/companies/{id}")
    public ResponseEntity<Company> getCompany(@PathVariable Integer id) {
        return this.companyService.handleGetOneCompany(id)
            .map(Company -> ResponseEntity.status(HttpStatus.ACCEPTED).body(Company))
            .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }
    
    @GetMapping("/companies")
    public ResponseEntity<List<Company>> getAllCompanies(){
        List<Company> companyList = this.companyService.handleGetAllCompanies().get();
        return ResponseEntity.status(HttpStatus.OK).body(companyList);
    }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity<Object> deleteCompany(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(id);
    }

    @PatchMapping("/companies/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable Integer id, @RequestBody CompanyDTO companyDto){
        return this.companyService.handleUpdateCompany(id, companyDto)
                .map(Company -> ResponseEntity.ok().body(Company))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
