package application.project.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import application.project.domain.Company.Company;
import application.project.domain.dto.PageMetadata;
import application.project.domain.dto.ResultReturnedDTO;
import application.project.domain.dto.request.ReqCompanyDTO;
import application.project.repository.CompanyRepository;
import application.project.repository.FilterableJdbcRepository;
import application.project.repository.JdbcSpecification.IjdbcSpecification;

import application.project.util.SecurityUtil.SecurityUtil;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final FilterableJdbcRepository repo;
    public CompanyService(CompanyRepository companyRepository, FilterableJdbcRepository repo)
    {
        this.companyRepository = companyRepository;
        this.repo = repo;
    }

    public Optional<Company> handleCreateCompany(ReqCompanyDTO companyDTO){
        companyDTO.setCreated_by_user_id(Long.parseLong(SecurityUtil.getCurrentUserLogin().get()));
        companyDTO.setUpdated_by_user_id(Long.parseLong(SecurityUtil.getCurrentUserLogin().get()));
        return this.companyRepository.create(companyDTO).flatMap(this::handleGetOneCompany);
    }

    public Optional<Company> handleGetOneCompany(int id){
        return this.companyRepository.findOne(id);
    }

    public ResultReturnedDTO handleGetAllCompanies(int page, int size){
        Page<Company> companyPage = this.companyRepository.findAllByPage(page, size);
        
        PageMetadata meta = PageMetadata
            .builder()
            .currentPage(companyPage.getNumber())
            .pageSize(companyPage.getSize())
            .totalPage(companyPage.getTotalPages())
            .totalElement(companyPage.getNumberOfElements())
            .build();
        
        return new ResultReturnedDTO(meta, companyPage.getContent());
        
    }
    
    public <T>ResultReturnedDTO handleGetAllCompaniesByFilter(
                               Class<T> targetType,
                               IjdbcSpecification<T> spec,
                               Pageable pageable){
                                
        Page<T> companyPage = this.repo.findAll(spec, pageable);
        
        PageMetadata meta = PageMetadata
            .builder()
            .currentPage(pageable.getPageNumber() + 1)
            .pageSize(pageable.getPageSize())
            .totalPage(companyPage.getTotalPages())
            .totalElement(companyPage.getNumberOfElements())
            .build();
        
        return new ResultReturnedDTO(
                meta, 
                companyPage.getContent()
                );
        
    }
   
    public Optional<Company> handleUpdateCompany(int company_id, ReqCompanyDTO companyDto){
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
            if (companyDto.getIndustry_id() != 0 && companyDto.getIndustry_id() != company.getIndustry_id()){
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

            if (companyDto.getNumber_of_follower() != 0 && companyDto.getNumber_of_follower() != company.getNumber_of_follower()){
                conditions.add("number_of_follower = :number_of_follower");
                value.put("number_of_follower",companyDto.getNumber_of_follower());
                
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
