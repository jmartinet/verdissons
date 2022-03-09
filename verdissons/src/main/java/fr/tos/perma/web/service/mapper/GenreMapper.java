package fr.tos.perma.web.service.mapper;

import fr.tos.perma.web.domain.Genre;
import fr.tos.perma.web.service.dto.GenreDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Genre} and its DTO {@link GenreDTO}.
 */
@Mapper(componentModel = "spring", uses = { FamilleMapper.class })
public interface GenreMapper extends EntityMapper<GenreDTO, Genre> {
    @Mapping(target = "famille", source = "famille", qualifiedByName = "nom")
    GenreDTO toDto(Genre s);

    @Named("nom")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nom", source = "nom")
    GenreDTO toDtoNom(Genre genre);
}
