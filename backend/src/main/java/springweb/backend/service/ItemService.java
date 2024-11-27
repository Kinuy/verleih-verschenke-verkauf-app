package springweb.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
}
