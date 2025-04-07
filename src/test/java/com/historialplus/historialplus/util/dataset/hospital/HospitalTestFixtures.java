package com.historialplus.historialplus.util.dataset.hospital;

import com.historialplus.historialplus.internal.hospital.entities.HospitalEntity;
import com.historialplus.historialplus.internal.state.entities.StateEntity;

import java.sql.Timestamp;

public class HospitalTestFixtures {
	public static HospitalEntity centralHospital(StateEntity state) {
		return HospitalEntity.builder()
			.name("Hospital Central Metropolitano")
			.address("Av. Principal 123, Ciudad Capital")
			.phone("+51123456789")
			.email("contacto@hospitalcentral.com")
			.ruc("20123456789")
			.createdAt(Timestamp.valueOf("2023-01-15 08:00:00"))
			.updatedAt(Timestamp.valueOf("2023-06-20 10:30:00"))
			.state(state)
			.build();
	}

	public static HospitalEntity mediumInactiveHospital(StateEntity state) {
		return HospitalEntity.builder()
			.name("Clínica San Juan")
			.address("Calle Secundaria 456, Distrito Norte")
			.phone("+51987654321")
			.email("info@clinicasanjuan.com")
			.ruc("20234567890")
			.createdAt(Timestamp.valueOf("2022-11-10 09:15:00"))
			.updatedAt(Timestamp.valueOf("2023-05-15 14:20:00"))
			.state(state)
			.build();
	}

	public static HospitalEntity smallDeletedHospital(StateEntity state) {
		return HospitalEntity.builder()
			.name("Hospital Villa Esperanza")
			.address("Jr. Los Olivos 789, Villa Esperanza")
			.phone("+51987654321")
			.email("villahope@hospitalvillaesperanza.com")
			.ruc("20345678901")
			.createdAt(Timestamp.valueOf("2021-05-20 10:00:00"))
			.updatedAt(Timestamp.valueOf("2022-12-01 16:45:00"))
			.state(state)
			.build();
	}

	public static HospitalEntity hospitalWithoutEmail(StateEntity state) {
		return HospitalEntity.builder()
			.name("Centro Médico Rápido")
			.address("Av. Industrial 321, Zona Comercial")
			.phone("+51112233445")
			.email("") // Caso especial: email vacío
			.ruc("20456789012")
			.state(state)
			.build();
	}

	public static HospitalEntity newlyCreatedHospital(StateEntity state) {
		return HospitalEntity.builder()
			.name("Hospital Nuevo Amanecer")
			.address("Av. Futuro 555, Urbanización Progreso")
			.phone("+51198765432")
			.email("nuevoamanecer@hospital.com")
			.ruc("20567890123")
			.createdAt(Timestamp.valueOf("2023-07-01 00:00:00"))
			.updatedAt(null) // Caso especial: nunca actualizado
			.state(state)
			.build();
	}

}
