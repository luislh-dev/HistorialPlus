package com.historialplus.historialplus.error.constants;

import com.historialplus.historialplus.error.dto.ApiErrorDetail;

import java.util.Map;

public class ConstraintMappings {
	private ConstraintMappings() {}

	public static final Map<String, ApiErrorDetail> GET_CONSTRAINT_MAPPINGS = Map.ofEntries(
		Map.entry("UK_file_type_name", ApiErrorDetail.builder().field("name").message("El nombre del tipo de archivo ya está en uso").build()),
		Map.entry("UK_hospital_email", ApiErrorDetail.builder().field("email").message("El correo electrónico del hospital ya está en uso").build()),
		Map.entry("UK_hospital_name", ApiErrorDetail.builder().field("name").message("El nombre del hospital ya está en uso").build()),
		Map.entry("UK_hospital_phone", ApiErrorDetail.builder().field("phone").message("El teléfono del hospital ya está en uso").build()),
		Map.entry("UK_hospital_ruc", ApiErrorDetail.builder().field("ruc").message("El RUC del hospital ya está en uso").build()),
		Map.entry("UK_people_address", ApiErrorDetail.builder().field("address").message("La dirección ya está en uso").build()),
		Map.entry("UK_people_document_number", ApiErrorDetail.builder().field("documentNumber").message("El número de documento ya está en uso").build()),
		Map.entry("UK_roles_name", ApiErrorDetail.builder().field("name").message("El nombre del rol ya está en uso").build()),
		Map.entry("UK_sex_type_name", ApiErrorDetail.builder().field("name").message("El nombre del tipo de sexo ya está en uso").build()),
		Map.entry("UK_states_name", ApiErrorDetail.builder().field("name").message("El nombre del estado ya está en uso").build()),
		Map.entry("UK_type_document_name", ApiErrorDetail.builder().field("name").message("El nombre del tipo de documento ya está en uso").build()),
		Map.entry("UKa9dydk3dj4qb8cvmjijqnrg5t", ApiErrorDetail.builder().field("userRole").message("La combinación de usuario y rol ya está en uso").build()),
		Map.entry("UK_users_email", ApiErrorDetail.builder().field("email").message("El correo electrónico del usuario ya está en uso").build()),
		Map.entry("UK_users_name", ApiErrorDetail.builder().field("name").message("El nombre del usuario ya está en uso").build())
	);
}