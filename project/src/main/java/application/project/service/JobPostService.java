package application.project.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import application.project.domain.Exception.InvalidException;
import application.project.domain.JobPost;
import application.project.domain.User_account;
import application.project.domain.dto.request.ReqJobPostDTO;
import application.project.domain.dto.response.RestResponse;
import application.project.domain.dto.response.ResultReturnedDTO;
import application.project.repository.JobPostRepository;
import application.project.repository.JobPostSkillRepository;
import application.project.repository.SkillRepository;
import application.project.repository.UserRepository;
import application.project.util.SecurityUtil.SecurityUtil;

@Service
public class JobPostService {
    private final JobPostRepository jpRepository;
    private final UserRepository userRepository;
    private final SkillRepository skillRepository;
    private final JobPostSkillRepository jp_SkillRepository;

    public JobPostService(JobPostSkillRepository jp_SkillRepository, SkillRepository skillRepository,
            JobPostRepository jpRepository, UserRepository userRepository) {
        this.jpRepository = jpRepository;
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
        this.jp_SkillRepository = jp_SkillRepository;
    }

    public ResultReturnedDTO handleCreateJobPost(ReqJobPostDTO jp) {

        Optional<Long> jpIdOptional = this.jpRepository.create(helper_create(jp));

        if (jpIdOptional.isPresent()) {
            Optional<JobPost> jpOpt = this.jpRepository.findById(jpIdOptional.get());
            return new ResultReturnedDTO(null, insertSkillsIntoJobPost_helper(jpOpt.get(), jp.getJpSkills()));
            
        }

        throw new InvalidException(jp.getJpTitle());
    }

    public ResultReturnedDTO handleGetAJobPost(long id) {

        Optional<JobPost> jpOpt = this.jpRepository.findById(id);
        
        if (jpOpt.isPresent()) {

            Optional<List<String>> skillsOpt = this.jp_SkillRepository.fetchJobPostSkills(id);
            JobPost currentPost = jpOpt.get();

            currentPost.setJpSkills(skillsOpt.get());
            return new ResultReturnedDTO(null,currentPost );
        }

        throw new InvalidException(String.valueOf(id));
    }

    public ResultReturnedDTO handleUpdateJobPost(long id, ReqJobPostDTO jp) {
        Optional<JobPost> jpOpt = this.jpRepository.findById(id);
        if (jpOpt.isEmpty()) {
            throw new InvalidException(String.valueOf(id));
        }
        // not null fields to be modified
        Map<String, Object> conditionsAndValues = helper_update(jp);
        this.jpRepository.update(id, conditionsAndValues);
        return new ResultReturnedDTO(null, insertSkillsIntoJobPost_helper(jpOpt.get(), jp.getJpSkills()));

    }

    private Map<String, Object> helper_create(ReqJobPostDTO jp) {
        long id = Long.parseLong(SecurityUtil.getCurrentUserLogin().get());
        Optional<User_account> userOpt = this.userRepository.find(id);

        // check skills do exist
        verifySkillExists_helper(jp.getJpSkills());

        Map<String, Object> ColumnsAndValues = new HashMap<>();
        Map<String, Object> mapValue = new HashMap<>();
        List<String> columns = new ArrayList<>();

        // created-by
        columns.add("jp_recruiter_id");
        mapValue.put("jp_recruiter_id", id);
        // updated-by
        columns.add("jp_updated_by");
        mapValue.put("jp_updated_by", id);
        // jp_belongs_to_cpn_id
        columns.add("jp_cpn_id");
        mapValue.put("jp_cpn_id", userOpt.get().getUs_cpn_id());

        if (jp.getJpNumberOfRecruitment() != 0) {
            columns.add("jp_number_of_recruitment");
            mapValue.put("jp_number_of_recruitment", jp.getJpNumberOfRecruitment());

        }

        if (jp.getJpTitle() != null) {
            columns.add("jp_title");
            mapValue.put("jp_title", jp.getJpTitle());

        }

        if (jp.getJpDescription() != null) {
            columns.add("jp_description");
            mapValue.put("jp_description", jp.getJpDescription());

        }

        if (jp.getJpSalaryRange() != null) {
            columns.add("jp_salary_range");
            mapValue.put("jp_salary_range", jp.getJpSalaryRange());
        }

        if (jp.getLocation() != null) {
            columns.add("jp_location");
            mapValue.put("jp_location", jp.getLocation());
        }

        if (jp.getJpStatus() != null) {
            columns.add("jp_status");
            mapValue.put("jp_status", jp.getJpStatus().name());
        }

        if (jp.getJpPostedAt() != null) {
            columns.add("jp_posted_at");
            mapValue.put("p_posted_at", jp.getJpPostedAt());

        }

        if (jp.getJpExpiredAt() != null) {
            columns.add("jp_expired_at");
            mapValue.put("jp_expired_at", jp.getJpExpiredAt());

        }

        ColumnsAndValues.put("columns", columns);
        ColumnsAndValues.put("values", mapValue);

        return ColumnsAndValues;
    }

    private Map<String, Object> helper_update(ReqJobPostDTO jp) {
        // check skills do exist
        verifySkillExists_helper(jp.getJpSkills());

        Map<String, Object> conditionAndValue = new HashMap<>();
        List<String> conditions = new ArrayList<>();
        Map<String, Object> values = new HashMap<>();

        
        if (jp.getJpNumberOfRecruitment() != 0) {
            conditions.add("jp_number_of_recruitment = :jp_number_of_recruitment");
            values.put("jp_number_of_recruitment", jp.getJpNumberOfRecruitment());
        }

        if (jp.getJpTitle() != null) {
            conditions.add("jp_title = :jp_title");
            values.put("jp_title", jp.getJpTitle());
        }

        if (jp.getJpDescription() != null) {
            conditions.add("jp_description = :jp_description");
            values.put("jp_description", jp.getJpDescription());

        }

        if (jp.getJpSalaryRange() != null) {
            values.put("jp_salary_range", jp.getJpSalaryRange());
            conditions.add("jp_salary_range = :jp_salary_range");
        }

        if (jp.getLocation() != null) {
            values.put("jp_location", jp.getLocation());
            conditions.add("jp_location = :jp_location");
        }

        if (jp.getJpStatus() != null) {
            values.put("jp_status", jp.getJpStatus().name());
            conditions.add("jp_status = :jp_status");
        }

        if (jp.getJpPostedAt() != null) {
            conditions.add("jp_posted_at = :jp_posted_at");
            values.put("jp_posted_at", jp.getJpPostedAt());

        }

        if (jp.getJpExpiredAt() != null) {
            conditions.add("jp_expired_at = :jp_expired_at");
            values.put("jp_expired_at", jp.getJpExpiredAt());

        }

        conditions.add("jp_updated_by = :jp_updated_by");
        values.put("jp_updated_by", Long.valueOf(SecurityUtil.getCurrentUserLogin().get()));

        conditionAndValue.put("conditions", conditions);
        conditionAndValue.put("values", values);

        return conditionAndValue;
    }

    public ResultReturnedDTO handleDeleteJobPost(long id) {
        Optional<JobPost> jpOpt = this.jpRepository.findById(id);
        if (jpOpt.isEmpty()) {
            throw new InvalidException(String.valueOf(id));
        }

        this.jp_SkillRepository.deleteSkillsOfJobPostByJobPostId(id);
        this.jpRepository.delete(id);
        return new ResultReturnedDTO(null, RestResponse.builder().setMessage("Delete successfully!").build());
    }
     
    private void verifySkillExists_helper(List<String> jp_skills) {
        List<Integer> skillList = this.skillRepository.checkSkillsExist(jp_skills);
        
        if (skillList != null && !skillList.isEmpty()){
            throw new InvalidException(skillList.toString());
        }
    }

    private JobPost insertSkillsIntoJobPost_helper(JobPost jp, List<String> skillIds) {
       
        this.jp_SkillRepository.addMultipleSkills(jp.getJpId(), skillIds);
        Optional<List<String>> skillsOpt = this.jp_SkillRepository.fetchJobPostSkills(jp.getJpId());
        jp.setJpSkills(skillsOpt.get());
        
        return jp;
    }

    
}
