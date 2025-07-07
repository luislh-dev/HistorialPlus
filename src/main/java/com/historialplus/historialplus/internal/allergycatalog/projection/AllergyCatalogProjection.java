package com.historialplus.historialplus.internal.allergycatalog.projection;

import com.historialplus.historialplus.common.constants.AllergyCategory;

import java.util.UUID;

public interface AllergyCatalogProjection {
	UUID getId();
	String getCode();
	String getName();
	AllergyCategory getCategory();
	Boolean getIsActive();
}
