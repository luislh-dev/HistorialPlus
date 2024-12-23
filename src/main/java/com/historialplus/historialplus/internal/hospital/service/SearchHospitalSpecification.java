package com.historialplus.historialplus.internal.hospital.service;

import com.historialplus.historialplus.internal.hospital.entities.HospitalEntity;
import com.historialplus.historialplus.internal.state.entities.StateEntity;
import jakarta.persistence.criteria.*;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SearchHospitalSpecification implements Specification<HospitalEntity> {

    private final String name;
    private final String ruc;
    private final Integer id;
    private final Integer stateId;

    public SearchHospitalSpecification(String name, String ruc, Integer id, Integer stateId) {
        this.name = name;
        this.ruc = ruc;
        this.id = id;
        this.stateId = stateId;
    }

    @Override
    public Predicate toPredicate(@NonNull Root<HospitalEntity> root, CriteriaQuery<?> query, @NonNull CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            Expression<String> hospitalNameToLowerCase = criteriaBuilder.lower(root.get("name"));
            Predicate nameLikePredicate = criteriaBuilder.like(hospitalNameToLowerCase, "%" + name + "%");
            predicates.add(nameLikePredicate);
        }

        if (ruc != null && !ruc.isEmpty()) {
            Expression<String> hospitalRucToLowerCase = criteriaBuilder.lower(root.get("ruc"));
            Predicate rucLikePredicate = criteriaBuilder.like(hospitalRucToLowerCase, "%" + ruc + "%");
            predicates.add(rucLikePredicate);
        }

        if (id != null) {
            Predicate idEqualPredicate = criteriaBuilder.equal(root.get("id"), id);
            predicates.add(idEqualPredicate);
        }

        if (stateId != null) {
            Join<HospitalEntity, StateEntity> stateJoin = root.join("state", JoinType.INNER);
            Predicate stateIdEqualPredicate = criteriaBuilder.equal(stateJoin.get("id"), stateId);
            predicates.add(stateIdEqualPredicate);
        }

        // Si no hay predicados, retorna todos los registros
        if (predicates.isEmpty()) {
            return criteriaBuilder.conjunction();
        }

        // Si hay predicados, combínalos todos con AND
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
