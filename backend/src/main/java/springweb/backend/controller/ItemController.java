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

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable String id) {
        itemService.deleteItem(id);
    }

    @PutMapping("/{id}")
    public Item updateItem(@PathVariable String id, @RequestBody ItemDto itemDTO) {
        Item item = new Item(
                id,
                itemDTO.name(),
                itemDTO.img(),
                itemDTO.description(),
                itemDTO.category(),
                itemDTO.status());
        return itemService.updateItem(id,item);
    }
}
