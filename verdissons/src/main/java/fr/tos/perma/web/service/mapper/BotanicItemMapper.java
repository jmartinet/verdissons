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
		BotanicItem entity = null;
		if (dto == null) {
			return null;
		}
		BotanicItem parent = null;
		if (dto instanceof FamilleDTO) {
			entity = new Famille();
		} else if (dto instanceof GenreDTO) {
			entity = new Genre();
			parent = new BotanicItemMapper().toEntity((FamilleDTO) dto.getParent());
		} else if (dto instanceof EspeceDTO) {
			entity = new Espece();
			parent = new BotanicItemMapper().toEntity((GenreDTO) dto.getParent());
		}
		entity.setId(dto.getId());
		entity.setLibelle(dto.getNom());
		entity.setParent(parent);
		return (BotanicItem) entity;
	}

	public BotanicItemDTO toDto(BotanicItem entity) {
		BotanicItemDTO dto = null;
		if (entity == null) {
			return null;
		}
		BotanicItemDTO parent = null;
		if (entity instanceof Famille) {
			dto = new FamilleDTO();
		} else if (entity instanceof Genre) {
			dto = new GenreDTO();
			parent = new BotanicItemMapper().toDto((Famille) entity.getParent());
		} else if (entity instanceof Espece) {
			dto = new EspeceDTO();
			parent = new BotanicItemMapper().toDto((Genre) entity.getParent());
		}
		dto.setId(entity.getId());
		dto.setNom(entity.getLibelle());
		dto.setParent(parent);
		dto.setType(entity.getType());
		return (BotanicItemDTO) dto;
	}

}
