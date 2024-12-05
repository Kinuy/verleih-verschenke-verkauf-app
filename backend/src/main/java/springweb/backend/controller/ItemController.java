package springweb.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springweb.backend.model.Item;
import springweb.backend.model.ItemDto;
import springweb.backend.service.ImageService;
import springweb.backend.service.ItemService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ImageService imageService;


    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Item createItem(
        @RequestPart("itemDto") ItemDto itemDto,
        @RequestPart("image") MultipartFile image) throws IOException {

        String imgUrl = null;
        if (image != null && !image.isEmpty()) {
            imgUrl = imageService.uploadImage(image);
        }

        return itemService.createItem(itemDto,imgUrl);
    }

    @GetMapping("/{id}")
    public Item getItemById(@PathVariable String id){
        return itemService.getItemById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable String id) {
        itemService.deleteItem(id);
    }

    @PutMapping("/{id}")
    public Item updateItem(@PathVariable String id, @RequestPart("itemDTO") ItemDto itemDTO,
                           @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {

        Item item = itemService.getItemById(id);

        String newImg;
        if (image != null && !image.isEmpty()) {
            if (item.img() != null) {
                imageService.deleteImage(item.img());
            }
            newImg = imageService.uploadImage(image);
        } else {
            newImg = item.img();
        }



        Item newItem = new Item(
                id,
                itemDTO.name(),
                newImg,
                itemDTO.description(),
                itemDTO.category(),
                itemDTO.status());
        return itemService.updateItem(id,newItem);
    }
}
