package com.historialplus.historialplus.internal.file.presenter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class FileDetailPresenter {
    @JsonProperty("name")
    private String name;

    @JsonProperty("size")
    private String size;

    @JsonProperty("url")
    private String url;

    @JsonProperty("object_key")
    private String objectKey;

    @JsonProperty("type_name")
    private String typeName;

    @JsonProperty("mime_type")
    private String mimeType;

    public FileDetailPresenter(String name, String size, String url, String typeName, String mimeType, String objectKey) {
        this.name = name;
        this.size = size;
        this.url = url;
        this.typeName = typeName;
        this.mimeType = mimeType;
        this.objectKey = objectKey;
    }

}