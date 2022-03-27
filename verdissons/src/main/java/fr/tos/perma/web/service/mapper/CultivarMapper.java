package fr.tos.perma.web.service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import fr.tos.perma.web.domain.Cultivar;
import fr.tos.perma.web.domain.Variete;
import fr.tos.perma.web.service.dto.CultivarDTO;
import fr.tos.perma.web.service.dto.VarieteDTO;

/**
 * Mapper for the entity {@link Variete} and its DTO {@link VarieteDTO}.
 */
@Mapper(componentModel = "spring", uses = { VarieteMapper.class })
public interface CultivarMapper extends EntityMapper<CultivarDTO, Cultivar> {

	@Mapping(target = "variete", source = "variete", qualifiedByName = "nom")
	CultivarDTO toDto(Cultivar s);

	@Named("nom")
	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "id", source = "id")
	@Mapping(target = "nom", source = "nom")
	@Mapping(target = "description", source = "description")
	CultivarDTO toDtoNom(Cultivar variete);
}
