package springweb.backend.service;
import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final Cloudinary cloudinary;



    public String uploadImage(MultipartFile image) throws IOException {

        File fileToUpload = File.createTempFile("item", "");
        image.transferTo(fileToUpload);


        Map response = cloudinary.uploader().upload(fileToUpload, Collections.emptyMap());
        return response.get("secure_url").toString();
    }

    public void deleteImage(String imageUrl) {
        String publicId = extractPublicIdFromUrl(imageUrl);

        try {
            cloudinary.uploader().destroy(publicId, Collections.emptyMap());
        } catch (IOException e) {
            throw new RuntimeException("Error, can not delete image.", e);
        }
    }
    public String extractPublicIdFromUrl(String url) {
        String[] parts = url.split("/");
        String publicId = parts[parts.length - 1].split("\\.")[0];
        return publicId;
    }
}

