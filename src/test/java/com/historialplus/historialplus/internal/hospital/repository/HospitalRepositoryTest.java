package com.historialplus.historialplus.internal.hospital.repository;

import com.historialplus.historialplus.internal.hospital.entities.HospitalEntity;
import com.historialplus.historialplus.internal.hospital.projection.HospitalNameProjection;
import com.historialplus.historialplus.internal.state.entities.StateEntity;
import com.historialplus.historialplus.util.dataset.hospital.HospitalTestFixtures;
import com.historialplus.historialplus.util.dataset.state.StateTestFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class HospitalRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private HospitalRepository hospitalRepository;

	private HospitalEntity centralHospital;
	private HospitalEntity hospitalWithoutEmail;
	private HospitalEntity mediumInactiveHospital;
	private HospitalEntity smallDeletedHospital;
	private HospitalEntity newlyCreatedHospital;

	@BeforeEach
	void setUp() {
		StateEntity active = entityManager.persist(StateTestFixtures.active());
		StateEntity inactive = entityManager.persist(StateTestFixtures.inactive());
		StateEntity deleted = entityManager.persist(StateTestFixtures.deleted());

		centralHospital        = entityManager.persist(HospitalTestFixtures.centralHospital(active));
		hospitalWithoutEmail   = entityManager.persist(HospitalTestFixtures.hospitalWithoutEmail(active));
		mediumInactiveHospital = entityManager.persist(HospitalTestFixtures.mediumInactiveHospital(inactive));
		smallDeletedHospital   = entityManager.persist(HospitalTestFixtures.smallDeletedHospital(deleted));
		newlyCreatedHospital   = entityManager.persist(HospitalTestFixtures.newlyCreatedHospital(active));
	}

	@Test
	void existByEmailShouldReturnTrueWhenEmailExistsForCentralHospital() {
		assertTrue(hospitalRepository.existsByEmail(centralHospital.getEmail()));
	}

	@Test
	void existByEmailShouldReturnTrueWhenEmailExistsForHospitalWithoutEmail() {
		assertTrue(hospitalRepository.existsByEmail(hospitalWithoutEmail.getEmail()));
	}

	@Test
	void existByEmailShouldReturnFalseWhenEmailDoesNotExist() {
		assertFalse(hospitalRepository.existsByEmail("notfound@gmail.com"));
	}

	@Test
	void existByNameShouldReturnTrueWhenNameExists() {
		assertTrue(hospitalRepository.existsByName(smallDeletedHospital.getName()));
	}

	@Test
	void existByNameShouldReturnFalseWhenNameDoesNotExist() {
		assertFalse(hospitalRepository.existsByName("notfound"));
	}

	@Test
	void existByPhoneShouldReturnTrueWhenPhoneExists() {
		assertTrue(hospitalRepository.existsByPhone(newlyCreatedHospital.getPhone()));
	}

	@Test
	void existByPhoneShouldReturnFalseWhenPhoneDoesNotExist() {
		assertFalse(hospitalRepository.existsByPhone("notfound"));
	}

	@Test
	void existByRucShouldReturnTrueWhenRucExists() {
		assertTrue(hospitalRepository.existsByRuc(mediumInactiveHospital.getRuc()));
	}

	@Test
	void existByRucShouldReturnFalseWhenRucDoesNotExist() {
		assertFalse(hospitalRepository.existsByRuc("notfound"));
	}

	@Test
	void findByNameContainingIgnoreCase_WhenSearchTermIsLowercase_ShouldReturnMatchingResults() {
		String searchTerm = "hospital";
		Pageable pageable = PageRequest.of(0, 10);

		Page<HospitalNameProjection> result = hospitalRepository.findByNameContainingIgnoreCase(searchTerm, pageable);

		assertFalse(result.isEmpty());
		assertTrue(result.getContent().stream()
			.anyMatch(hospital -> hospital.getName().toLowerCase().contains(searchTerm.toLowerCase())));
		assertEquals(3, result.getTotalElements());
	}

	@Test
	void findByNameContainingIgnoreCase_WhenSearchTermIsUppercase_ShouldReturnMatchingResults() {
		String searchTerm = "HOSPITAL";
		Pageable pageable = PageRequest.of(0, 10);

		Page<HospitalNameProjection> result = hospitalRepository.findByNameContainingIgnoreCase(searchTerm, pageable);

		assertFalse(result.isEmpty());
		assertTrue(result.getContent().stream()
			.anyMatch(hospital -> hospital.getName().toLowerCase().contains(searchTerm.toLowerCase())));
		assertEquals(3, result.getTotalElements());
	}

}