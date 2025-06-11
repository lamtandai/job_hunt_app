package application.project.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import application.project.domain.Company.Company;
import application.project.domain.DTO.CompanyDTO;
import application.project.domain.DTO.PageMetadata;
import application.project.domain.DTO.ResultReturnedDTO;

import application.project.repository.CompanyRepository;
import application.project.util.SecurityUtil.SecurityUtil;

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

    public ResultReturnedDTO handleGetAllCompanies(int page, int size){
        Page<Company> companyPage = this.companyRepository.findAllByPage(page, size);
        
        PageMetadata meta = new PageMetadata();
        meta.setCurrentPage(companyPage.getNumber() + 1);
        meta.setElementPerPage(companyPage.getSize());
        meta.setTotalPage(companyPage.getTotalPages());
        
        
        return new ResultReturnedDTO(meta, companyPage.getContent());
        
    }
   
    public Optional<Company> handleUpdateCompany(int company_id, CompanyDTO companyDto){
        return this.companyRepository.findOne(company_id).map(company -> {
            // Example: update fields from companyDto to company
            List <String> conditions = new ArrayList<>();
            Map <String, Object> value = new HashMap<>();

            if ( companyDto.getCompany_name() != null && !companyDto.getCompany_name().equals(company.getCompany_name())){
                conditions.add("company_name = :company_name");
                value.put("company_name",companyDto.getCompany_name());
            }

            if (companyDto.getDescription() != null && !companyDto.getDescription().equals(company.getDescription())){
                conditions.add("description = :description");
                value.put("description",companyDto.getDescription());
            }
            if (!companyDto.getIndustry_id().equals(company.getIndustry_id())){
                conditions.add("industry_id = :industry_id");
                value.put("industry_id",companyDto.getIndustry_id());
                
            }
            if (companyDto.getLocation() != null && !companyDto.getLocation().equals(company.getLocation())){
                value.put("location",companyDto.getLocation());
                conditions.add("location = :location" );
            }

            if (companyDto.getLogo() != null && !companyDto.getLogo().equals(company.getLogo())){
                value.put("logo",companyDto.getLogo());
                conditions.add("logo = :logo");
            }

            if (companyDto.getNumberOfFollower() != 0 && companyDto.getNumberOfFollower() != company.getNumberOfFollower()){
                conditions.add("numberOfFollower = :numberOfFollower");
                value.put("numberOfFollower",companyDto.getNumberOfFollower());
                
            }

            if (companyDto.getSize() != 0 && companyDto.getSize() != company.getSize()){
                conditions.add("size = :size");
                value.put("size",companyDto.getSize());
            }

            if (companyDto.getWebsite_url()!= null && !companyDto.getWebsite_url().equals(company.getWebsite_url())){
                conditions.add("website_url = :website_url");
                value.put("website_url",companyDto.getWebsite_url());
            }

            if (companyDto.isVerified() != company.isVerified()){
                conditions.add("verified = :verified" );
                value.put("verified",companyDto.isVerified());
            }

            conditions.add(" updated_by_user_id = :updated_by_user_id" );
            value.put("updated_by_user_id", Long.valueOf(SecurityUtil.getCurrentUserLogin().get()));
            
            return this.companyRepository.update(company_id, conditions, value).orElseThrow();
        });
    }

    // public Company handleDeleteCompany(){

    // }
}
