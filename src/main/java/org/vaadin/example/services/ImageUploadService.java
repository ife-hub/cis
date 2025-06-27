package org.vaadin.example.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.UUID;

@Service
public class ImageUploadService {

    // Option 1: Cloudinary (Recommended)
    @Value("${cloudinary.cloud-name:}")
    private String cloudName;

    @Value("${cloudinary.api-key:}")
    private String apiKey;

    @Value("${cloudinary.api-secret:}")
    private String apiSecret;

    private Cloudinary cloudinary;

    @PostConstruct
    public void initCloudinary() {
        if (!cloudName.isEmpty() && !apiKey.isEmpty() && !apiSecret.isEmpty()) {
            cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", cloudName,
                    "api_key", apiKey,
                    "api_secret", apiSecret
            ));
        }
    }

    public String uploadToCloudinary(InputStream inputStream, String originalFilename) throws IOException {
        if (cloudinary == null) {
            throw new IllegalStateException("Cloudinary not configured");
        }

        Map uploadResult = cloudinary.uploader().upload(inputStream.readAllBytes(),
                ObjectUtils.asMap(
                        "folder", "blog-images",
                        "public_id", UUID.randomUUID().toString(),
                        "transformation", "c_limit,w_1200,h_1200,q_auto:good"
                ));

        return (String) uploadResult.get("secure_url");
    }

    // Option 2: Local File System Storage
    public String uploadToFileSystem(InputStream inputStream, String originalFilename) throws IOException {
        String uploadDir = "uploads/images/";
        String filename = UUID.randomUUID() + "_" + sanitizeFilename(originalFilename);
        String fullPath = uploadDir + filename;

        // Create directory if it doesn't exist
        Files.createDirectories(Paths.get(uploadDir));

        // Save file
        Files.copy(inputStream, Paths.get(fullPath), StandardCopyOption.REPLACE_EXISTING);

        // Return URL path that your application can serve
        return "/api/images/" + filename;
    }

    // Option 3: Firebase Storage (if you prefer Firebase)
    public String uploadToFirebase(InputStream inputStream, String originalFilename) throws IOException {
        // Firebase implementation would go here
        // This is a placeholder - you'd need Firebase SDK
        throw new UnsupportedOperationException("Firebase upload not implemented");
    }

    private String sanitizeFilename(String filename) {
        if (filename == null) return "unnamed";
        return filename.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
    }
}
