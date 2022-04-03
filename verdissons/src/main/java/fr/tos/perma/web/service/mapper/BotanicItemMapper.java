package fr.tos.perma.web.service.mapper;

import org.mapstruct.Mapper;

import fr.tos.perma.web.domain.BotanicItem;
import fr.tos.perma.web.domain.Espece;
import fr.tos.perma.web.domain.Famille;
import fr.tos.perma.web.domain.Genre;
import fr.tos.perma.web.service.dto.BotanicItemDTO;
import fr.tos.perma.web.service.dto.EspeceDTO;
import fr.tos.perma.web.service.dto.FamilleDTO;
import fr.tos.perma.web.service.dto.GenreDTO;

@Mapper(componentModel = "spring")
public class BotanicItemMapper {

	public BotanicItem toEntity(BotanicItemDTO dto) {
		BotanicItem entity = new BotanicItem();
		if (dto == null) {
			return null;
		}
		BotanicItem parent = null;
		if (dto.getParent() != null) {
			parent = new BotanicItemMapper().toEntity(dto.getParent());
		}
		entity.setId(dto.getId());
		entity.setLibelle(dto.getNom());
		entity.setParent(parent);
		entity.setType(dto.getType());
		return entity;
	}

	public BotanicItemDTO toDto(BotanicItem entity) {
		BotanicItemDTO dto = new BotanicItemDTO();
		if (entity == null) {
			return null;
		}
		BotanicItemDTO parent = null;
		if (entity.getParent() != null) {
			parent = new BotanicItemMapper().toDto(entity.getParent());
		}
		dto.setId(entity.getId());
		dto.setNom(entity.getLibelle());
		dto.setParent(parent);
		dto.setType(entity.getType());
		return dto;
	}

}
