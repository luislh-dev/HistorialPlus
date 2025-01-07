package com.historialplus.historialplus.internal.file.projection;

import java.util.UUID;

public interface FileBasicProjection {
    UUID getRecordDetailId();

    String getName();

    Long getSizeInBytes();

    String getUrl();

    String getMimeType();

    String getTypeName();
}
