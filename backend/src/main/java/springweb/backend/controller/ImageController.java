package springweb.backend.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springweb.backend.service.ImageService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")

public class ImageController {

    private final ImageService imageService;

    @PostMapping
    public String uploadImage(@RequestPart(required = false) MultipartFile image, @RequestPart String json) throws IOException {

        // save this string in db!
        return  imageService.uploadImage(image);

    }
}