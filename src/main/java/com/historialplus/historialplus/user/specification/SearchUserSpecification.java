package com.historialplus.historialplus.user.specification;

import com.historialplus.historialplus.hospital.entities.HospitalEntity;
import com.historialplus.historialplus.people.entities.PeopleEntity;
import com.historialplus.historialplus.role.entites.RoleEntity;
import com.historialplus.historialplus.state.entities.StateEntity;
import com.historialplus.historialplus.user.entites.UserEntity;
import jakarta.persistence.criteria.*;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SearchUserSpecification implements Specification<UserEntity> {
    private final String name;
    private final String documentNumber;
    private final String hospitalName;
    private final Integer roleId;
    private final Integer stateId;

    public SearchUserSpecification(String name, String documentNumber, String HospitalName, Integer roleId, Integer stateId) {
        this.name = name;
        this.documentNumber = documentNumber;
        this.hospitalName = HospitalName;
        this.roleId = roleId;
        this.stateId = stateId;
    }

    @Override
    public Predicate toPredicate(@NonNull Root<UserEntity> root, CriteriaQuery<?> query, @NonNull CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if(name != null && !name.isEmpty()) {
            Expression<String> userNameToLowerCase = criteriaBuilder.lower(root.get("username"));
            Predicate nameLikePredicate = criteriaBuilder.like(userNameToLowerCase, "%" + name + "%");
            predicates.add(nameLikePredicate);
        }

        if (documentNumber != null && !documentNumber.isEmpty()) {
            Join<UserEntity, PeopleEntity> peopleJoin = root.join("person", JoinType.INNER);
            Expression<String> userDocumentNumberToLowerCase = criteriaBuilder.lower(peopleJoin.get("documentNumber"));
            Predicate dniLikePredicate = criteriaBuilder.like(userDocumentNumberToLowerCase, "%" + documentNumber + "%");
            predicates.add(dniLikePredicate);
        }

        if (hospitalName != null && !hospitalName.isEmpty()) {
            Join<UserEntity, HospitalEntity> hospitalJoin = root.join("hospital", JoinType.INNER);
            Expression<String> hospitalNameToLowerCase = criteriaBuilder.lower(hospitalJoin.get("name"));
            Predicate hospitalNameLikePredicate = criteriaBuilder.like(hospitalNameToLowerCase, "%" + hospitalName + "%");
            predicates.add(hospitalNameLikePredicate);
        }

        if (roleId != null) {
            Join<UserEntity, RoleEntity> roleJoin = root.join("roleEntities", JoinType.INNER);
            Predicate roleIdEqualPredicate = criteriaBuilder.equal(roleJoin.get("id"), roleId);
            predicates.add(roleIdEqualPredicate);
        }

        if (stateId != null) {
            Join<UserEntity, StateEntity> stateJoin = root.join("state", JoinType.INNER);
            Predicate stateIdEqualPredicate = criteriaBuilder.equal(stateJoin.get("id"), stateId);
            predicates.add(stateIdEqualPredicate);
        }

        // Si no hay predicados, retorna todos los registros
        if (predicates.isEmpty()) {
            return criteriaBuilder.conjunction();
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

    }
}
