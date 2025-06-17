package application.project.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import application.project.domain.Company.Company;
import application.project.domain.DTO.CompanyDTO;
import application.project.domain.DTO.ResultReturnedDTO;

import application.project.repository.JdbcSpecification.JdbcFilterSpecification;
import application.project.service.CompanyService;
import application.project.util.CustomAnnotation.Filterable;
import jakarta.validation.Valid;

@RestController

public class CompanyController {
    private final CompanyService companyService;
    
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/companies")
    public ResponseEntity<Company> createCompany(@Valid @RequestBody CompanyDTO companyDTO) {
        return this.companyService.handleCreateCompany(companyDTO)
                .map(newCompany -> ResponseEntity.status(HttpStatus.CREATED).body(newCompany))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

    }

    @GetMapping("/companies/{id}")
    public ResponseEntity<Company> getCompany(@PathVariable Integer id) {
        return this.companyService.handleGetOneCompany(id)
                .map(Company -> ResponseEntity.status(HttpStatus.ACCEPTED).body(Company))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/companies")
    public ResponseEntity<ResultReturnedDTO> getAllCompanies(
            @RequestParam (defaultValue = "1")Optional<String> page,
            @RequestParam (defaultValue = "10") Optional<String> size) {

        String currentPage = page.isPresent() ? page.get() : "";
        String pageSize = size.isPresent() ? size.get() : "";
        ResultReturnedDTO result = this.companyService.handleGetAllCompanies(Integer.parseInt(currentPage), Integer.parseInt(pageSize));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity<Object> deleteCompany(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(id);
    }

    @PatchMapping("/companies/{id}")
    public ResponseEntity<Company> updateCompany(
        @PathVariable Integer id, 
        @Valid @RequestBody CompanyDTO companyDto) {
        return this.companyService.handleUpdateCompany(id, companyDto)
                .map(Company -> ResponseEntity.ok().body(Company))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    @GetMapping ("companies/all_user")
    public ResponseEntity<ResultReturnedDTO> list(
        @Filterable JdbcFilterSpecification<Company> spec,
        Pageable pageable) {
        ResultReturnedDTO result = this.companyService.handleGetAllCompaniesByFilter(Company.class, spec, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}

