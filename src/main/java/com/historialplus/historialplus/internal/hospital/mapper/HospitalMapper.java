package com.historialplus.historialplus.internal.hospital.mapper;

import com.historialplus.historialplus.internal.hospital.dto.response.HospitalFindByResponseDto;
import com.historialplus.historialplus.internal.hospital.dto.response.HospitalPageResponseDto;
import com.historialplus.historialplus.internal.hospital.projection.HospitalDetailsProjection;
import com.historialplus.historialplus.internal.hospital.projection.HospitalPageProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Primary;

@Primary
@Mapper(componentModel = "spring")
public interface HospitalMapper {
	@Mapping( target = "stateName", source = "state.displayName")
	HospitalPageResponseDto hospitalPageProjectionToHospitalPageResponseDto(HospitalPageProjection hospitalPageProjection);

	HospitalFindByResponseDto hospitalDetailsProjectionToHospitalFindByResponseDto(HospitalDetailsProjection projection);
}
