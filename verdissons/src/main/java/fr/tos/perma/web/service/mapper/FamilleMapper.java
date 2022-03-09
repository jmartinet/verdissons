package fr.tos.perma.web.service.mapper;

import fr.tos.perma.web.domain.Famille;
import fr.tos.perma.web.service.dto.FamilleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Famille} and its DTO {@link FamilleDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FamilleMapper extends EntityMapper<FamilleDTO, Famille> {
    @Named("nom")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nom", source = "nom")
    FamilleDTO toDtoNom(Famille famille);
}
