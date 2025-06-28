package application.project.service;


import java.util.Optional;
import org.springframework.stereotype.Service;
import application.project.domain.Industry;
import application.project.domain.Exception.InvalidException;
import application.project.domain.dto.request.ReqIndustry;
import application.project.domain.dto.response.RestResponse;
import application.project.domain.dto.response.ResultReturnedDTO;
import application.project.repository.IndustryRepository;

@Service
public class IndustryService {
    private final IndustryRepository industryRepository;

    public IndustryService(IndustryRepository industryRepository) {
        this.industryRepository = industryRepository;
    }

    public ResultReturnedDTO getIdtById(int id) {
        Optional<Industry> industryOpt = industryRepository.findById(id);
        return new ResultReturnedDTO(null, 
            industryOpt.orElseThrow(
                () -> new InvalidException(String.valueOf(id))));
        
    }

    public ResultReturnedDTO createIdt(ReqIndustry Industry) {
        Optional <Industry> industryOpt = industryRepository.findByName(Industry.getIdt_name());
        
        if (industryOpt.isPresent()){
            throw new InvalidException(Industry.getIdt_name());
        }

        return getIdtById(industryRepository.create(Industry).get());
        
    }

    public ResultReturnedDTO updateIdt(int id, ReqIndustry Industry) {
        Optional<Industry> opt =  this.industryRepository.findById(id);

        if (opt.isEmpty()){
            throw new InvalidException(String.valueOf(id));
        }

        Optional<Industry> industryOpt = this.industryRepository.findByName(Industry.getIdt_name());
        
        if (industryOpt.isPresent()){
            throw new InvalidException(Industry.getIdt_name());
        }

        this.industryRepository.update(id, Industry);

        return new ResultReturnedDTO(null, this.industryRepository.findById(id).get());
    }

    public ResultReturnedDTO deleteIdt(int id) {
        Optional<Industry> opt =  this.industryRepository.findById(id);

        if (opt.isEmpty()){
            throw new InvalidException(String.valueOf(id));
        }
        
        this.industryRepository.delete(id);

        return new  ResultReturnedDTO(null, RestResponse.builder().setMessage("Delete successfully!").build());
    }
   
    public ResultReturnedDTO findByIdtName(String name) {
        Optional<Industry> industryOpt = this.industryRepository.findByName(name);
        ResultReturnedDTO result = new ResultReturnedDTO();

        if (industryOpt.isPresent()) {
            result.setMetadata(null);
            result.setResult(industryOpt.get());
        }
        return result;
    }
}
