package com.historialplus.historialplus.internal.allergy.service;

import com.historialplus.historialplus.internal.allergy.dto.request.AllergyCreateDto;
import com.historialplus.historialplus.internal.allergy.dto.request.AllergyUpdateDto;
import com.historialplus.historialplus.internal.allergy.dto.response.AllergyResponseDto;
import com.historialplus.historialplus.internal.allergy.projection.AllergyDetailsProjection;
import com.historialplus.historialplus.internal.allergy.projection.AllergyPageProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AllergyService {
    AllergyResponseDto createAllergy(AllergyCreateDto dto);
    AllergyResponseDto updateAllergy(Integer allergyId, AllergyUpdateDto dto);
    AllergyDetailsProjection findAllergyById(Integer allergyId);
    Page<AllergyPageProjection> findAllAllergies(Pageable pageable);
    Page<AllergyPageProjection> findAllergiesByPeopleId(UUID peopleId, Pageable pageable);
    void deleteAllergy(Integer allergyId);
    AllergyResponseDto toggleAllergyStatus(Integer allergyId, Boolean isActive);
}
