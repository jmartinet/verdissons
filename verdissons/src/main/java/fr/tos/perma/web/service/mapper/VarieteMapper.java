package fr.tos.perma.web.service.mapper;

import fr.tos.perma.web.domain.Variete;
import fr.tos.perma.web.service.dto.VarieteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Variete} and its DTO {@link VarieteDTO}.
 */
@Mapper(componentModel = "spring", uses = { GenreMapper.class })
public interface VarieteMapper extends EntityMapper<VarieteDTO, Variete> {
    @Mapping(target = "genre", source = "genre", qualifiedByName = "nom")
    VarieteDTO toDto(Variete s);
}
