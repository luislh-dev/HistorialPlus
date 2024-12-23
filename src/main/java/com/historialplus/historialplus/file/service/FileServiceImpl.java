package com.historialplus.historialplus.file.service;

import com.adobe.pdfservices.operation.pdfjobs.params.compresspdf.CompressionLevel;
import com.historialplus.historialplus.file.dto.response.FilesResponseDto;
import com.historialplus.historialplus.file.entites.FileEntity;
import com.historialplus.historialplus.file.mapper.FilesDtoMapper;
import com.historialplus.historialplus.file.repository.FileRepository;
import com.historialplus.historialplus.service.ImgCompressService.IMGCompressService;
import com.historialplus.historialplus.service.iLovePDFService.PDFCompressService;
import com.historialplus.historialplus.util.InMemoryMultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements IFileService {

    private final FileRepository fileRepository;
    private final PDFCompressService pdfCompressService;
    private final IMGCompressService imgCompressService;

    @Autowired
    public FileServiceImpl(FileRepository fileRepository, PDFCompressService pdfCompressService, IMGCompressService imgCompressService) {
        this.fileRepository = fileRepository;
        this.pdfCompressService = pdfCompressService;
        this.imgCompressService = imgCompressService;
    }

    @Override
    public List<FilesResponseDto> findAll() {
        return fileRepository.findAll().stream()
                .map(FilesDtoMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<FilesResponseDto> findById(UUID id) {
        return fileRepository.findById(id)
                .map(FilesDtoMapper::toResponseDto);
    }

    @Override
    public FileEntity save(FileEntity fileEntity) {
        compressFile(fileEntity);
        return fileRepository.save(fileEntity);
    }

    private void compressFile(FileEntity fileEntity) {
        int fileTypeId = fileEntity.getFileType().getId();
        try {
            if (fileTypeId == 1) {
                // PDF compression
                Path tempFile = Files.createTempFile("temp", ".pdf");
                Files.write(tempFile, fileEntity.getUrl().getBytes());
                String compressedUrl = pdfCompressService.compressAndUploadPDF(tempFile, CompressionLevel.LOW);
                fileEntity.setUrl(compressedUrl);
                Files.deleteIfExists(tempFile);
            } else if (fileTypeId >= 2 && fileTypeId <= 5) {
                // Image compression
                MultipartFile multipartFile = new InMemoryMultipartFile(
                        "file",
                        fileEntity.getName(),
                        "image/" + fileEntity.getFileType().getName(),
                        fileEntity.getUrl().getBytes()
                );
                String compressedUrl = imgCompressService.compressAndUploadImage(multipartFile, 80);
                fileEntity.setUrl(compressedUrl);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error during file compression", e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error during file compression", e);
        }
    }
}