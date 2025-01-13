package com.historialplus.historialplus.internal.file.projection;

import java.util.UUID;

public interface FileBasicProjection {
    UUID getRecordDetailId();

    String getName();

    String getObjectKey();

    Long getSizeInBytes();

    String getUrl();

    String getMimeType();

    String getTypeName();
}
