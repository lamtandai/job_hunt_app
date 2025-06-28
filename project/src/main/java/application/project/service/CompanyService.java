package application.project.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import application.project.domain.Company;
import application.project.domain.Exception.InvalidException;
import application.project.domain.dto.request.ReqCompanyDTO;
import application.project.domain.dto.response.PageMetadata;
import application.project.domain.dto.response.ResultReturnedDTO;
import application.project.repository.CompanyRepository;
import application.project.repository.FilterableJdbcRepository;
import application.project.repository.JdbcSpecification.IjdbcSpecification;
import application.project.repository.UserRepository;
import application.project.util.SecurityUtil.SecurityUtil;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final FilterableJdbcRepository repo;

    public CompanyService(CompanyRepository companyRepository, FilterableJdbcRepository repo,
            UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.repo = repo;
        this.userRepository = userRepository;
    }

    public Optional<Company> handleCreateCompany(ReqCompanyDTO companyDTO) {
        companyDTO.setCreated_by(Long.parseLong(SecurityUtil.getCurrentUserLogin().get()));
        companyDTO.setUpdated_by(Long.parseLong(SecurityUtil.getCurrentUserLogin().get()));
        return this.companyRepository.create(companyDTO).flatMap(this::handleGetOneCompany);
    }

    public Optional<Company> handleGetOneCompany(int id) {
        return this.companyRepository.findOne(id);
    }

    public ResultReturnedDTO handleGetAllCompanies(int page, int size) {
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

    public <T> ResultReturnedDTO handleGetAllCompaniesByFilter(
            Class<T> targetType,
            IjdbcSpecification<T> spec,
            Pageable pageable) {

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
                companyPage.getContent());

    }

    public Optional<Company> handleUpdateCompany(int company_id, ReqCompanyDTO companyDto) {
        return this.companyRepository.findOne(company_id).map(company -> {
            // Example: update fields from companyDto to company
            List<String> conditions = new ArrayList<>();
            Map<String, Object> value = new HashMap<>();

            if (companyDto.getCompany_name() != null
                    && !companyDto.getCompany_name().equals(company.getCpn_name())) {
                conditions.add("cpn_name = :cpn_name");
                value.put("cpn_name", companyDto.getCompany_name());
            }

            if (companyDto.getDescription() != null
                    && !companyDto.getDescription().equals(company.getCpn_description())) {
                conditions.add("cpn_description = :cpn_description");
                value.put("cpn_description", companyDto.getDescription());
            }
            if (companyDto.getIndustry_id() != 0 && companyDto.getIndustry_id() != company.getCpn_idt_id()) {
                conditions.add("cpn_idt_id = :cpn_idt_id");
                value.put("cpn_idt_id", companyDto.getIndustry_id());

            }
            if (companyDto.getLocation() != null && !companyDto.getLocation().equals(company.getCpn_location())) {
                value.put("cpn_location", companyDto.getLocation());
                conditions.add("cpn_location = :cpn_location");
            }

            if (companyDto.getLogo() != null && !companyDto.getLogo().equals(company.getCpn_logo())) {
                value.put("cpn_logo", companyDto.getLogo());
                conditions.add("cpn_logo = :cpn_logo");
            }

            if (companyDto.getNumber_of_follower() != 0
                    && companyDto.getNumber_of_follower() != company.getCpn_number_of_follower()) {
                conditions.add("cpn_number_of_follower = :cpn_number_of_follower");
                value.put("cpn_number_of_follower", companyDto.getNumber_of_follower());

            }

            if (companyDto.getSize() != 0 && companyDto.getSize() != company.getCpn_size()) {
                conditions.add("cpn_size = :cpn_size");
                value.put("cpn_size", companyDto.getSize());
            }

            if (companyDto.getWebsite_url() != null
                    && !companyDto.getWebsite_url().equals(company.getCpn_website_url())) {
                conditions.add("cpn_website_url = :cpn_website_url");
                value.put("cpn_website_url", companyDto.getWebsite_url());
            }

            if (companyDto.isVerified() != company.isCpn_verified()) {
                conditions.add("cpn_verified = :cpn_verified");
                value.put("cpn_verified", companyDto.isVerified());
            }

            conditions.add(" cpn_updated_by_user_id = :cpn_updated_by_user_id");
            value.put("cpn_updated_by_user_id", Long.valueOf(SecurityUtil.getCurrentUserLogin().get()));

            return this.companyRepository.update(company_id, conditions, value).orElseThrow();
        });
    }

    public void handleDeleteCompany(int cpn_id) throws InvalidException {
        Optional<Company> cpnOpt = handleGetOneCompany(cpn_id);
        if (cpnOpt.isPresent() && !cpnOpt.get().isCpn_deleted()) {
            this.userRepository.deleteUserByCompanyId(cpn_id);
            this.companyRepository.delete(cpn_id);
            return;
        }
        throw new InvalidException(String.valueOf(cpn_id));

    }
}
