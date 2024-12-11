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
        Item i1 = new Item("1","Hammer1","url","mein Hammer", ItemCategory.TOOL, ItemStatus.TO_LEND,new double[]{48.8566, 2.3522}, "me");
        Item i2 = new Item("2","Hammer2","url","mein Hammer", ItemCategory.TOOL, ItemStatus.TO_LEND,new double[]{48.8566, 2.3522}, "me");
        Item i3 = new Item("3","Hammer3","url","mein Hammer", ItemCategory.TOOL, ItemStatus.TO_LEND,new double[]{48.8566, 2.3522}, "me");
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
        double [] geocode = {48.8566, 2.3522};
        Item expected = new Item("1","test-name","test-url","test-description",ItemCategory.TOOL, ItemStatus.TO_LEND,geocode, "me");
        ItemDto dto = new ItemDto("test-name","test-img","test-description",ItemCategory.TOOL, ItemStatus.TO_LEND,geocode, "me");
        String url = "test-url";

        when(itemRepository.save(expected)).thenReturn(expected);
        when(idService.generateId()).thenReturn("1");

        //WHEN
        Item actual =itemService.createItem(dto,url);
        //THEN
        verify(itemRepository).save(expected);
        assertEquals(expected, actual);

        verify(idService).generateId();

    }

    @Test
    void getItemById_shouldReturnItem_whenGivenID1() {
        //GIVEN
        Item expected = new Item("1","Hammer1","url","mein Hammer", ItemCategory.TOOL, ItemStatus.TO_LEND,new double[]{48.8566, 2.3522}, "me");

        when(itemRepository.findById("1")).thenReturn(Optional.of(expected));
        //WHEN
        Item actual =itemService.getItemById("1");
        verify(itemRepository).findById("1");
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void deleteItem(){
        //GIVEN
        when(itemRepository.existsById("1")).thenReturn(Boolean.TRUE);
        //WHEN
        itemService.deleteItem("1");
        //THEN
        verify(itemRepository).deleteById("1");
        verify(itemRepository).existsById("1");
    }

    @Test
    void updateItem(){
        //GIVEN
        Item modItem = new Item("1","test-name","test-img","test-description",ItemCategory.TOOL, ItemStatus.TO_SELL,new double[]{48.8566, 2.3522}, "me");

        when(itemRepository.existsById("1")).thenReturn(true);
        when(itemRepository.save(any(Item.class))).thenReturn(modItem);

        //WHEN
        Item expected = itemService.updateItem("1",modItem);

        //THEN
        assertEquals(modItem,expected);
        verify(itemRepository,times(1)).existsById("1");
        verify(itemRepository,times(1)).save(modItem);

    }

}


