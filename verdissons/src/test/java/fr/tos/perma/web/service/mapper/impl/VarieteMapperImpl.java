package fr.tos.perma.web.service.mapper.impl;

import java.util.List;

import fr.tos.perma.web.domain.Espece;
import fr.tos.perma.web.domain.Variete;
import fr.tos.perma.web.service.dto.EspeceDTO;
import fr.tos.perma.web.service.dto.VarieteDTO;
import fr.tos.perma.web.service.mapper.VarieteMapper;

public class VarieteMapperImpl implements VarieteMapper {

	@Override
	public Variete toEntity(VarieteDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Variete> toEntity(List<VarieteDTO> dtoList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VarieteDTO> toDto(List<Variete> entityList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void partialUpdate(Variete entity, VarieteDTO dto) {
		// TODO Auto-generated method stub

	}

	@Override
	public VarieteDTO toDto(Variete s) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public EspeceDTO especeToDto(Espece entity) {
		return null;
	}

	@Override
	public VarieteDTO toDtoNom(Variete variete) {
		// TODO Auto-generated method stub
		return null;
	}

}
