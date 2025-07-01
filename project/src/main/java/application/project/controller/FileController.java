package application.project.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import application.project.domain.dto.response.ResUploadFile;
import application.project.domain.dto.response.ResultReturnedDTO;
import application.project.service.FileService;


@RestController
public class FileController {
    private final FileService fileService;
    
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ResultReturnedDTO> uploadFile(
        @RequestParam ("file") MultipartFile file, 
        @RequestParam ("folder") String folder) throws URISyntaxException, IOException {
        
        this.fileService.validateFile(file);
        this.fileService.createUploadFolder(folder);
        String fileName = this.fileService.store(file, folder);
        ResUploadFile result = new ResUploadFile(fileName, Instant.now());
        return ResponseEntity.ok().body(new ResultReturnedDTO(null,result));
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(
        @RequestParam ("fileName") String fileName, 
        @RequestParam ("folderName") String folder) throws URISyntaxException, IOException {
        
        this.fileService.validateDownloadFile(fileName, folder);
        InputStreamResource resource = this.fileService.getResource(fileName, folder);
        long contentLength = this.fileService.getFileLength(fileName, folder);
        
        return ResponseEntity
            .ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
            .contentLength(contentLength)
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(resource);
    }
    
}
