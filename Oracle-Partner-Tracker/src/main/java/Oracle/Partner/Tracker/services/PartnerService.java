package Oracle.Partner.Tracker.services;

import Oracle.Partner.Tracker.repositories.PartnerRepository;
import Oracle.Partner.Tracker.entities.ChangeHistory;
import Oracle.Partner.Tracker.entities.Partner;
import Oracle.Partner.Tracker.utils.ChangeType;
import Oracle.Partner.Tracker.utils.Converter;
import Oracle.Partner.Tracker.dto.GenericDTO;
import Oracle.Partner.Tracker.dto.PartnerDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@Service
public class PartnerService implements GenericService{

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private ChangeHistoryService changeHistoryService;

    public List<Partner> findAllUsers() {
        List<Partner> allPartners = partnerRepository.findAll();
        if (allPartners.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return allPartners;
    }

    public Partner findUserById(Long id) {
        Optional<Partner> user = partnerRepository.findById(id);
        if (user.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return user.get();
    }

    public ResponseEntity registerNewPartner(PartnerDTO partnerDTO) {
        if (partnerRepository.existsByUsername(partnerDTO.getUsername())){
            return ResponseEntity.badRequest().build();
        }
        partnerRepository.save(new Partner(partnerDTO));
        return ResponseEntity.ok().build();
    }

    public ResponseEntity updateUser(Long id, PartnerDTO partnerDTO){
        Optional<Partner> partner = partnerRepository.findById(id);
        if (partner.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Partner partnerUpdate = partner.get();
        partnerUpdate.setUpdateAt(LocalDateTime.now());

        // Atualizar o Partner

        return ResponseEntity.ok().build();
    }

    public ResponseEntity deleteUser(Long id) {
        Optional<Partner> partner = partnerRepository.findById(id);
        if (partner.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        partnerRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public Class<?> getDtoClass() {
        return PartnerDTO.class;
    }

    @Override
    public void saveAllGenericDTO(List<GenericDTO> genericDTOList) {
        for(GenericDTO genericDTO : genericDTOList){
            PartnerDTO partnerDTO = (PartnerDTO) genericDTO;
            if(!partnerRepository.existsByUsername(partnerDTO.getUsername()) & partnerDTO.getUsername() != null & !partnerDTO.getUsername().isEmpty()){
                partnerRepository.save(new Partner(partnerDTO));
                changeHistoryService.saveChangeHistory(Long.decode("1"),"partner",ChangeType.CREATE, new PartnerDTO(), partnerDTO);
            }
        }

        // Como usar o hitorico do banco de dados
        Converter converter = new Converter();
        List<ChangeHistory> changeHistoryList = changeHistoryService.findAll();
        System.out.println("\nInicio de teste da tabela historica... \n");
        for(ChangeHistory changeHistory : changeHistoryList){
            System.out.println("ChangedByPartnerId: "+changeHistory.getChangedByPartnerId());
            System.out.println("TableName: "+changeHistory.getTableName());
            System.out.println("ChangeType: "+changeHistory.getChangeType());
            System.out.println("OldValueJsonFormat: "+converter.hexadecimalToString(changeHistory.getOldValueHexadecimal()));
            System.out.println("NewValueJsonFormat: "+converter.hexadecimalToString(changeHistory.getNewValueHexadecimal()));
            System.out.println();
        }
        System.out.println("... Fim de teste da tabela historica");
    }
}
