package uc.dei.mse.supportcare.service.mapper;


import uc.dei.mse.supportcare.domain.*;
import uc.dei.mse.supportcare.service.dto.ProtocolDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Protocol} and its DTO {@link ProtocolDTO}.
 */
@Mapper(componentModel = "spring", uses = {TherapeuticRegimeMapper.class, OutcomeMapper.class, GuideMapper.class})
public interface ProtocolMapper extends EntityMapper<ProtocolDTO, Protocol> {

    @Mapping(source = "therapeuticRegime.id", target = "therapeuticRegimeId")
    @Mapping(source = "outcome.id", target = "outcomeId")
    @Mapping(source = "guide.id", target = "guideId")
    ProtocolDTO toDto(Protocol protocol);

    @Mapping(source = "therapeuticRegimeId", target = "therapeuticRegime")
    @Mapping(source = "outcomeId", target = "outcome")
    @Mapping(source = "guideId", target = "guide")
    Protocol toEntity(ProtocolDTO protocolDTO);

    default Protocol fromId(Long id) {
        if (id == null) {
            return null;
        }
        Protocol protocol = new Protocol();
        protocol.setId(id);
        return protocol;
    }
}
