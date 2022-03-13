package fr.tos.perma.web.service.mapper;

import fr.tos.perma.web.domain.Semence;
import fr.tos.perma.web.service.dto.SemenceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Semence} and its DTO {@link SemenceDTO}.
 */
@Mapper(componentModel = "spring", uses = { VarieteMapper.class })
public interface SemenceMapper extends EntityMapper<SemenceDTO, Semence> {
    @Mapping(target = "variete", source = "variete", qualifiedByName = "nomLatin")
    SemenceDTO toDto(Semence s);
}
