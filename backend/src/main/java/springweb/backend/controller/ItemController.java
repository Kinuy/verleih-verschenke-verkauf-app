package springweb.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springweb.backend.model.Item;
import springweb.backend.model.ItemDto;
import springweb.backend.service.ItemService;

import java.util.List;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;


    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @PostMapping
    public Item createItem(@RequestBody ItemDto itemDTO) {
        return itemService.createItem(itemDTO);
    }

    @GetMapping("/{id}")
    public Item getItemById(@PathVariable String id){
        return itemService.getItemById(id);
    }
}
