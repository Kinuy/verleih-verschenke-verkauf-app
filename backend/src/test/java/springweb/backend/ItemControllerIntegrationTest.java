package springweb.backend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import springweb.backend.model.Item;
import springweb.backend.model.ItemCategory;
import springweb.backend.model.ItemStatus;
import springweb.backend.repository.ItemRepository;
import org.springframework.http.MediaType;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureMockRestServiceServer
class ItemControllerIntegrationTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ItemRepository repo;

    @Autowired
    MockRestServiceServer mockRestServiceServer;

    @Test
    void getAllItems()  throws Exception {
        //GIVEN
        repo.deleteAll();
        //WHEN & THEN
        mvc.perform(get("/api/item"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                            []
                                            """));
    }

    @Test
    void postItem() throws Exception {
        // GIVEN
        repo.deleteAll();

        // WHEN
        mvc.perform(post("/api/item")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                         {
                         "name": "testName",
                         "img": "testImg",
                         "description": "testDescription",
                         "category": "TOOL",
                         "status": "TO_LEND"
                         }
                        """)
        ).andExpect(status().isOk());

        // THEN
        List<Item> allItems = repo.findAll();
        Assertions.assertEquals(1, allItems.size());

        Item savedItem = allItems.getFirst();
        org.assertj.core.api.Assertions.assertThat(savedItem)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(new Item(
                        null,
                        "testName",
                        "testImg",
                        "testDescription",
                        ItemCategory.TOOL,
                        ItemStatus.TO_LEND


                ));
    }

    @Test
    void getItemById_getItemWithId1_whenItemWithId1isRequested() throws Exception {

        Item newItem = new Item("1","testName","testImg","testDescription",ItemCategory.TOOL,ItemStatus.TO_LEND);

        repo.deleteAll();
        repo.save(newItem);

        mvc.perform(get("/api/item/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                {
                "id":"1",
                "name":"testName",
                "img":"testImg",
                "description":"testDescription",
                "category":"TOOL",
                "status":"TO_LEND"
                }
                
                """));
    }
}
