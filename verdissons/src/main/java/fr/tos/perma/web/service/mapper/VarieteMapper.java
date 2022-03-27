package fr.tos.perma.web.service.mapper;

import fr.tos.perma.web.domain.Variete;
import fr.tos.perma.web.service.dto.VarieteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Variete} and its DTO {@link VarieteDTO}.
 */
@Mapper(componentModel = "spring", uses = { EspeceMapper.class })
public interface VarieteMapper extends EntityMapper<VarieteDTO, Variete> {
    @Mapping(target = "espece", source = "espece", qualifiedByName = "nom")
    VarieteDTO toDto(Variete s);

    @Named("nom")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nom", source = "nom")
    VarieteDTO toDtoNom(Variete variete);
}
