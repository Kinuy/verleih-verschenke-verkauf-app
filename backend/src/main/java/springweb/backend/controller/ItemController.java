package springweb.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springweb.backend.model.Item;
import springweb.backend.service.ItemService;

import java.util.List;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

/*    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }*/

    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @PostMapping
    public Item createItem(@RequestBody Item itemDTO) {
        return itemService.createItem(itemDTO);
    }
}
