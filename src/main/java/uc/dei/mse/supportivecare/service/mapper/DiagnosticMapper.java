package uc.dei.mse.supportivecare.service.mapper;


import uc.dei.mse.supportivecare.domain.*;
import uc.dei.mse.supportivecare.service.dto.DiagnosticDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Diagnostic} and its DTO {@link DiagnosticDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DiagnosticMapper extends EntityMapper<DiagnosticDTO, Diagnostic> {


    @Mapping(target = "therapeuticRegimes", ignore = true)
    @Mapping(target = "removeTherapeuticRegime", ignore = true)
    Diagnostic toEntity(DiagnosticDTO diagnosticDTO);

    default Diagnostic fromId(Long id) {
        if (id == null) {
            return null;
        }
        Diagnostic diagnostic = new Diagnostic();
        diagnostic.setId(id);
        return diagnostic;
    }
}
