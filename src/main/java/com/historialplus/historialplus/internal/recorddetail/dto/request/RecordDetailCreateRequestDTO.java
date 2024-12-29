package com.historialplus.historialplus.internal.recorddetail.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class RecordDetailCreateRequestDTO {
    @NotNull(message = "El ID del registro médico es requerido")
    private UUID personId;

    @NotNull(message = "El estado es requerido")
    private Integer stateId;

    @NotNull(message = "La razón es requerida")
    @Min(value = 10, message = "La razón es requerida")
    @Max(value = 500, message = "La razón no puede tener más de 500 caracteres")
    private String reason;

    // este campo no es requerido, pero si es escrito que no supere el limite de 500
    @Max(value = 500, message = "El diagnóstico no puede tener más de 500 caracteres")
    private String diagnosis;

    // este campo no es requerido, pero si es escrito que no supere el limite de 500
    @Max(value = 500, message = "El tratamiento no puede tener más de 500 caracteres")
    private String treatment;

    @NotNull(message = "La fecha de visita es requerida")
    private LocalDateTime visitDate;

    @Valid
    private Set<FileDTO> files = new HashSet<>();

    @Getter
    @Setter
    public static class FileDTO {
        @NotNull(message = "El tipo de archivo es requerido")
        private Integer fileTypeId;

        private MultipartFile file;
    }
}
