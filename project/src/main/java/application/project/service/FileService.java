package application.project.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import application.project.domain.Exception.StorageException;
import application.project.domain.dto.response.ResultReturnedDTO;

@Service
public class FileService {
    @Value("${basepath.baseUri}")
    private String baseUploadPath;

    @Value("${file.allowedExtension}")
    private String allowedExtension;
    
    @Value("${file.allowedMimeTypes}")
    private String allowedMimeType;

    @Value("${file.spring.servlet.multipart.max-file-size}")
    private long allowedSize;
    
    public void createUploadFolder(String folder) throws URISyntaxException {
        folder = baseUploadPath.concat(folder);
        URI uri = new URI(folder);
        Path path = Paths.get(uri);
        File tmpDir = new File(path.toString());
        if (!tmpDir.isDirectory()) {
            try {
                Files.createDirectory(tmpDir.toPath());
                System.out.println(">>> CREATE NEW DIRECTORY SUCCESSFUL, PATH = " + folder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(">>> SKIP MAKING DIRECTORY, ALREADY EXISTS");
        }
    }

    public String store(MultipartFile file, String folder) throws URISyntaxException, IOException {
       // create unique filename
        String finalName = System.currentTimeMillis() + "-" + file.getOriginalFilename();

        URI uri = new URI(baseUploadPath.concat(folder).concat("/").concat(finalName));
        Path path = Paths.get(uri);
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, path,
                    StandardCopyOption.REPLACE_EXISTING);
        }
        return finalName;
    }

    public void validateFile(MultipartFile file){
        if (file == null || file.isEmpty()){
            throw new StorageException("This file is empty, please upload your file!");
        }

        String fileName = file.getOriginalFilename();
        List<String> allowedExtensions = Arrays.asList(allowedExtension.split(","));

        boolean isAllowed = allowedExtensions
            .stream()
            .anyMatch(item -> fileName.toLowerCase().endsWith(item));
        if (!isAllowed) {
            throw new StorageException("Invalid file format, please choose another type: " + allowedExtensions.toString());
        }


        List<String> allowedMimeTypes = Arrays.asList(allowedMimeType.split(","));
        String contentType = file.getContentType();

        if (!allowedMimeTypes.contains(contentType)) {
            throw new StorageException("Invalid file type based on MIME type.");
        }

        if (file.getSize() > allowedSize) {
            throw new StorageException("File too large!");
        }

    }

    public void validateDownloadFile (String fileName, String folder) throws URISyntaxException, FileNotFoundException{
        if (fileName == null || folder == null){
            throw new StorageException("Missing fileName or FolderName");
        }

        long fileLength = getFileLength(fileName, folder);
        if (fileLength == 0){
            throw new StorageException(fileName.concat(" or ".concat(folder).concat(" is not found!")));
        }

    }


    public InputStreamResource getResource(String fileName, String folder) throws FileNotFoundException, URISyntaxException{
        URI uri = new URI(baseUploadPath.concat(folder).concat("/").concat(fileName));
        Path path = Paths.get(uri);
        File tmpDir = new File(path.toString());
        return new InputStreamResource(new FileInputStream(tmpDir));
    }
    
    public long getFileLength(String fileName, String folder) throws URISyntaxException{
        URI uri = new URI(baseUploadPath.concat(folder).concat("/").concat(fileName));
        Path path = Paths.get(uri);
        File tmpDir = new File(path.toString());

        if (!tmpDir.exists() || tmpDir.isDirectory()){
            return 0;
        }

        return tmpDir.length();
    }

}
