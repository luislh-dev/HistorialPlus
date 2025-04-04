package com.historialplus.historialplus.internal.file.mapper;

import com.historialplus.historialplus.common.constants.FileTypeEnum;
import com.historialplus.historialplus.internal.file.presenter.FileDetailPresenter;
import com.historialplus.historialplus.internal.file.projection.FileBasicProjection;

public class FilesPresenter {

    // Convertir de FileBasicProjection a FileDetailPresenter
    public static FileDetailPresenter toFileDetailPresenter(FileBasicProjection projection) {

        // Convertir el tipo de archivo a un nombre legible
        String typeName;
        try {
            typeName = FileTypeEnum.valueOf(projection.getTypeName()).getDisplayName();
        } catch (IllegalArgumentException e) {
            typeName = projection.getTypeName();
        }

        // Formatear el tamaño del archivo
        String sizeFormatted = (projection.getSizeInBytes() > 1024 ?
                (projection.getSizeInBytes() > 1024 * 1024 ?
                        String.format("%.2f MB", projection.getSizeInBytes() / (1024.0 * 1024)) :
                        String.format("%.2f KB", projection.getSizeInBytes() / 1024.0)) :
                projection.getSizeInBytes() + " B");

        return new FileDetailPresenter(
                projection.getName(),
                sizeFormatted,
                projection.getUrl(),
                typeName,
                projection.getMimeType(),
                projection.getObjectKey()
        );
    }
}
