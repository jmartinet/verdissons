package fr.tos.perma.web.service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import fr.tos.perma.web.domain.Variete;
import fr.tos.perma.web.service.dto.VarieteDTO;

/**
 * Mapper for the entity {@link Variete} and its DTO {@link VarieteDTO}.
 */
@Mapper(componentModel = "spring", uses = { EspeceMapper.class })
public abstract class VarieteMapper implements EntityMapper<VarieteDTO, Variete> {

	@Mapping(target = "espece", source = "espece", qualifiedByName = "nom")
	public abstract VarieteDTO toDto(Variete variete);

	@Named("nom")
	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "id", source = "id")
	@Mapping(target = "nom", source = "nom")
	abstract VarieteDTO toDtoNom(Variete variete);
}
