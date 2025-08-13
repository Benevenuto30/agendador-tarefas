package com.benevenuto.agendadortarefas.business.mapper;

import com.benevenuto.agendadortarefas.business.dto.TarefasDTO;
import com.benevenuto.agendadortarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TarefasConverter {

    TarefasEntity paraTarefaEntity(TarefasDTO dto);

    TarefasDTO paraTarefaDTO(TarefasEntity entity);
}
