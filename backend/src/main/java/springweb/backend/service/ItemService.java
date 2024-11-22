package springweb.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springweb.backend.model.Item;
import springweb.backend.repository.ItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor

public class ItemService {

    private final ItemRepository itemRepository;
    private final IdService idService;
/*    public ItemService(ItemRepository itemRepository,IdService idService) {
        this.itemRepository = itemRepository;
        this.idService = idService;
    }*/

    public Item createItem(Item itemDTO) {
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
}
