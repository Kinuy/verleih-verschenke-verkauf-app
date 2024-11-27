package springweb.backend;

import org.junit.jupiter.api.Test;
import springweb.backend.model.Item;
import springweb.backend.model.ItemCategory;
import springweb.backend.model.ItemDto;
import springweb.backend.model.ItemStatus;
import springweb.backend.repository.ItemRepository;
import springweb.backend.service.IdService;
import springweb.backend.service.ItemService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ItemServiceTest {


    IdService idService = mock(IdService.class);
    ItemRepository itemRepository = mock(ItemRepository.class);
    ItemService itemService = new ItemService(itemRepository,idService);


    @Test
    void getAllItems(){
        //GIVEN
        Item i1 = new Item("1","Hammer1","url","mein Hammer", ItemCategory.TOOL, ItemStatus.TO_LEND);
        Item i2 = new Item("2","Hammer2","url","mein Hammer", ItemCategory.TOOL, ItemStatus.TO_LEND);
        Item i3 = new Item("3","Hammer3","url","mein Hammer", ItemCategory.TOOL, ItemStatus.TO_LEND);
        List<Item> expected = List.of(i1,i2,i3);

        when(itemRepository.findAll()).thenReturn(List.of(i1,i2,i3));

        //WHEN
        List<Item> actual = itemService.getAllItems();

        //THEN
        verify(itemRepository).findAll();
        assertEquals(expected, actual);


    }

    @Test
    void saveNewItem_shouldReturnItem_whenGivenDtoItem() {
        //GIVEN
        Item expected = new Item("1","test-name","test-img","test-description",ItemCategory.TOOL, ItemStatus.TO_LEND);
        ItemDto dto = new ItemDto("test-name","test-img","test-description",ItemCategory.TOOL, ItemStatus.TO_LEND);


        when(itemRepository.save(expected)).thenReturn(expected);
        when(idService.generateId()).thenReturn("1");

        //WHEN
        Item actual =itemService.createItem(dto);
        //THEN
        verify(itemRepository).save(expected);
        assertEquals(expected, actual);

        verify(idService).generateId();

    }

    @Test
    void getItemById_shouldReturnItem_whenGivenID1() {
        //GIVEN
        Item expected = new Item("1","Hammer1","url","mein Hammer", ItemCategory.TOOL, ItemStatus.TO_LEND);

        when(itemRepository.findById("1")).thenReturn(Optional.of(expected));
        //WHEN
        Item actual =itemService.getItemById("1");
        verify(itemRepository).findById("1");
        //THEN
        assertEquals(expected, actual);




    }

}


