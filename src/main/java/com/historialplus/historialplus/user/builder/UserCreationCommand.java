package com.historialplus.historialplus.user.builder;

import com.historialplus.historialplus.entities.*;

import java.util.UUID;

public class UserCreationCommand {
    private final String email;
    private final String password;
    private final String username;
    private final UUID personId;
    private final Integer hospitalId;
    private final Integer roleId;
    private final Integer stateId;

    private UserCreationCommand(Builder builder) {
        this.email = builder.email;
        this.password = builder.password;
        this.username = builder.username;
        this.personId = builder.personId;
        this.hospitalId = builder.hospitalId;
        this.roleId = builder.roleId;
        this.stateId = builder.stateId;
    }

    public UserEntity toUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(this.email);
        userEntity.setPassword(this.password);
        userEntity.setUsername(this.username);

        PeopleEntity person = new PeopleEntity();
        person.setId(this.personId);
        userEntity.setPerson(person);

        if (this.hospitalId != null) {
            HospitalEntity hospital = new HospitalEntity();
            hospital.setId(this.hospitalId);
            userEntity.setHospital(hospital);
        }

        RoleEntity role = new RoleEntity();
        role.setId(this.roleId);
        userEntity.getRoleEntities().add(role);

        StateEntity state = new StateEntity();
        state.setId(this.stateId);
        userEntity.setState(state);

        return userEntity;
    }

    public static class Builder {
        private final String email;
        private final String password;
        private final String username;
        private final UUID personId;
        private Integer hospitalId;
        private final Integer roleId;
        private final Integer stateId;

        public Builder(String email, String password, String username, UUID personId, Integer roleId, Integer stateId) {
            this.email = email;
            this.password = password;
            this.username = username;
            this.personId = personId;
            this.roleId = roleId;
            this.stateId = stateId;
        }

        public Builder hospitalId(Integer hospitalId) {
            this.hospitalId = hospitalId;
            return this;
        }


        public UserCreationCommand build() {
            return new UserCreationCommand(this);
        }
    }
}