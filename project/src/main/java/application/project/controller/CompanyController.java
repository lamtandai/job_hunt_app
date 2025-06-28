package application.project.controller;

import org.springframework.data.domain.Pageable;
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

import application.project.domain.dto.request.ReqCompanyDTO;
import application.project.domain.dto.response.ResultReturnedDTO;
import application.project.domain.Company;
import application.project.domain.Exception.InvalidException;
import application.project.domain.dto.response.RestResponse;
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

    @PostMapping()
    public ResponseEntity<Company> createCompany(@Valid @RequestBody ReqCompanyDTO companyDTO) {
        return this.companyService.handleCreateCompany(companyDTO)
                .map(newCompany -> ResponseEntity.status(HttpStatus.CREATED).body(newCompany))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompany(@PathVariable Integer id) {
        return this.companyService.handleGetOneCompany(id)
                .map(Company -> ResponseEntity.status(HttpStatus.ACCEPTED).body(Company))
                .orElseThrow(() -> new InvalidException(String.valueOf(id)));
    }

    // @GetMapping()
    // public ResponseEntity<ResultReturnedDTO> getAllCompanies(
    // @RequestParam (defaultValue = "1")Optional<String> page,
    // @RequestParam (defaultValue = "10") Optional<String> size) {

    // String currentPage = page.isPresent() ? page.get() : "";
    // String pageSize = size.isPresent() ? size.get() : "";
    // ResultReturnedDTO result =
    // this.companyService.handleGetAllCompanies(Integer.parseInt(currentPage),
    // Integer.parseInt(pageSize));
    // return ResponseEntity.status(HttpStatus.OK).body(result);
    // }

    @PatchMapping("/{id}")
    public ResponseEntity<Company> updateCompany(
            @PathVariable String id,
            @Valid @RequestBody ReqCompanyDTO companyDto) {
        return this.companyService.handleUpdateCompany(Integer.parseInt(id), companyDto)
                .map(Company -> ResponseEntity.ok().body(Company))
                .orElseThrow(() -> new InvalidException(id));
    }

    @GetMapping()
    public ResponseEntity<ResultReturnedDTO> list(
            @Filterable JdbcFilterSpecification<Company> spec,
            Pageable pageable) {
        ResultReturnedDTO result = this.companyService.handleGetAllCompaniesByFilter(Company.class, spec, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponse> deleteCompany(
            @PathVariable Integer id) {
        this.companyService.handleDeleteCompany(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                RestResponse
                        .builder()
                        .setMessage("Delete successfully")
                        .setStatusCode(HttpStatus.OK.value())
                        .build());
    }
}
