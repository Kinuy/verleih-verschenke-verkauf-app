package springweb.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springweb.backend.exception.ItemException;
import springweb.backend.model.Item;
import springweb.backend.model.ItemDto;
import springweb.backend.repository.ItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor

public class ItemService {

    private final ItemRepository itemRepository;
    private final IdService idService;


    public Item createItem(ItemDto itemDTO) {
        String id = idService.generateId();

        Item itemToSave = new Item(
                id,
                itemDTO.name(),
                itemDTO.img(),
                itemDTO.description(),
                itemDTO.category(),
                itemDTO.status()
                );

        return itemRepository.save(itemToSave);
    }


    public List<Item> getAllItems() {

        return itemRepository.findAll();
    }

    public Item getItemById(String id) {
        return itemRepository.findById(id).orElse(null);
    }

    public void deleteItem(String id) {
        if(!itemRepository.existsById(id)) {
            throw new ItemException("Item with id " + id + " not found");
        }
        itemRepository.deleteById(id);
    }

    public Item updateItem(String id,Item item) {
        if(!itemRepository.existsById(id)){
            throw new ItemException("Item with id " + id + " not found");
        }
        Item itemToUpdate = new Item(
                id,
                item.name(),
                item.img(),
                item.description(),
                item.category(),
                item.status());
        return itemRepository.save(itemToUpdate);
    }
}
