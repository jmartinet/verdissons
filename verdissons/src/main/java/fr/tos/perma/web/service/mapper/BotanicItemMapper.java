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
public class BotanicItemMapper<E extends BotanicItem, D extends BotanicItemDTO> {

	@SuppressWarnings("unchecked")
	public E toEntity(D dto) {
		BotanicItem entity = null;
		if (dto == null) {
			return null;
		}
		BotanicItem parent = null;
		if (dto instanceof FamilleDTO) {
			entity = new Famille();
		} else if (dto instanceof GenreDTO) {
			entity = new Genre();
			parent = new BotanicItemMapper<Famille, FamilleDTO>().toEntity((FamilleDTO) dto.getParent());
		} else if (dto instanceof EspeceDTO) {
			entity = new Espece();
			parent = new BotanicItemMapper<Genre, GenreDTO>().toEntity((GenreDTO) dto.getParent());
		}
		entity.setId(dto.getId());
		entity.setLibelle(dto.getNom());
		entity.setParent(parent);
		return (E) entity;
	}

	@SuppressWarnings("unchecked")
	public D toDto(E entity) {
		BotanicItemDTO dto = null;
		if (entity == null) {
			return null;
		}
		BotanicItemDTO parent = null;
		if (entity instanceof Famille) {
			dto = new FamilleDTO();
		} else if (entity instanceof Genre) {
			dto = new GenreDTO();
			parent = new BotanicItemMapper<Famille, FamilleDTO>().toDto((Famille) entity.getParent());
		} else if (entity instanceof Espece) {
			dto = new EspeceDTO();
			parent = new BotanicItemMapper<Genre, GenreDTO>().toDto((Genre) entity.getParent());
		}
		dto.setId(entity.getId());
		dto.setNom(entity.getLibelle());
		dto.setParent(parent);
		return (D) dto;
	}

}
