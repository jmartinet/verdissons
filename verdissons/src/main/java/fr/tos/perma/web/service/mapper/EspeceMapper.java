package fr.tos.perma.web.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

import fr.tos.perma.web.domain.Espece;
import fr.tos.perma.web.service.dto.EspeceDTO;

/**
 * Mapper for the entity {@link Espece} and its DTO {@link EspeceDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public class EspeceMapper {
	
	@Named("nom")
	public EspeceDTO entityToDTO(Espece espece) {
		return new BotanicItemMapper<Espece, EspeceDTO>().toDto(espece);
	}
	
}
