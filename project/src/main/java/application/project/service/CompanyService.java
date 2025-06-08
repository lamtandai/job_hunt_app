package application.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import application.project.domain.Company.Company;
import application.project.domain.DTO.CompanyDTO;
import application.project.repository.CompanyRepository;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Optional<Company> handleCreateCompany(CompanyDTO companyDTO){
        return this.companyRepository.create(companyDTO).flatMap(this::handleGetOneCompany);
    }

    public Optional<Company> handleGetOneCompany(int id){
        return this.companyRepository.findOne(id);
    }

    public Optional<List<Company>> handleGetAllCompanies(){
        return this.companyRepository.findAll();
    }
    
    public Optional<Company> handleUpdateCompany(int company_id, CompanyDTO companyDto){
        return this.companyRepository.findOne(company_id).map(company -> {
            // Example: update fields from companyDto to company
            if (companyDto.getCompany_name() != null){
                company.setCompany_name(companyDto.getCompany_name());
            }

            if (companyDto.getDescription() != null){
                company.setDescription(companyDto.getDescription());
            }
            if (companyDto.getIndustry_id() != null){
                company.setIndustry_id(companyDto.getIndustry_id());
            }
            if (companyDto.getLocation() != null){
                company.setLocation(companyDto.getLocation());
            }

            if (companyDto.getLogo() != null){
                company.setLogo(companyDto.getLogo());
            }

            if (companyDto.getNumberOfFollower() != company.getNumberOfFollower()){
                company.setNumberOfFollower(companyDto.getNumberOfFollower());
            }

            if (companyDto.getSize() != company.getSize()){
                company.setSize(companyDto.getSize());
            }

            if (companyDto.isVerified() != company.isVerified()){
                company.setVerified(companyDto.isVerified());
            }

            return this.companyRepository.update(company).orElseThrow();
        });
    }

    // public Company handleDeleteCompany(){

    // }
}
