package springweb.backend;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import springweb.backend.model.*;
import springweb.backend.repository.AppUserRepository;
import springweb.backend.repository.ItemRepository;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureMockRestServiceServer
class ItemControllerIntegrationTest {

    @MockBean
    private Cloudinary cloudinary;

    @Autowired
    MockMvc mvc;

    @Autowired
    ItemRepository repo;

    @Autowired
    AppUserRepository userRepo;

    @Autowired
    MockRestServiceServer mockRestServiceServer;

    @BeforeEach
    void setUp() {
        repo.deleteAll();
        userRepo.deleteAll();

        List<Double> geoList = new ArrayList<>(Arrays.asList(48.8566, 2.3522));

        Item newItem = new Item("1",
                "testName",
                "testImg",
                "testDescription",
                ItemCategory.TOOL,
                ItemStatus.TO_LEND,
                geoList,
                "me"
        );

        repo.save(newItem);

        AppUser user = new AppUser(
                "testId",
                "testUserName",
                "testPwd",
                AppUserRole.USER,
                List.of("testItem")
        );
        userRepo.save(user);

    }


    @Test
    void getAllItems() throws Exception {
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
    @WithMockUser(username = "testUserName")
    void postItem_whenItemPost_ThenReturnItemCreated() throws Exception {
        // GIVEN

        List<Double> geoList = new ArrayList<>(Arrays.asList(48.8566, 2.3522));

        repo.deleteAll();
        Uploader mockUploader = mock(Uploader.class);
        when(mockUploader.upload(any(), anyMap())).thenReturn(Map.of("secure_url", "testImgUrl"));
        when(cloudinary.uploader()).thenReturn(mockUploader);

        // WHEN
        mvc.perform(MockMvcRequestBuilders.multipart("/api/item")
                .file(new MockMultipartFile("image", "test.jpg", "image/jpeg", "test image".getBytes()))
                .file(new MockMultipartFile("itemDto", "", "application/json", """
                         {
                         "name": "testName",
                         "img": "testImgUrl",
                         "description": "testDescription",
                         "category": "TOOL",
                         "status": "TO_LEND",
                         "geocode":[48.8566, 2.3522],
                         "owner":"me"
                         }
                        """.getBytes())))
                .andExpect(status().isCreated());

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
                        "testImgUrl",
                        "testDescription",
                        ItemCategory.TOOL,
                        ItemStatus.TO_LEND,
                        geoList,
                        "me"

                ));
    }

    @Test
    void getItemById_getItemWithId1_whenItemWithId1isRequested() throws Exception {

        List<Double> geoList = new ArrayList<>(Arrays.asList(48.8566, 2.3522));

        Item newItem = new Item("1",
                "testName",
                "testImg",
                "testDescription",
                ItemCategory.TOOL,
                ItemStatus.TO_LEND,
                geoList,
                "me"
                );

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
                        "status":"TO_LEND",
                        "geocode":[48.8566, 2.3522],
                        "owner":"me"
                        }
                        
                        """));
    }

    @Test
    void deleteItem() throws Exception {
        //GIVEN
        repo.deleteAll();
        List<Double> geoList = new ArrayList<>(Arrays.asList(48.8566, 2.3522));
        Item newItem = new Item("1",
                "testname",
                "testImg",
                "testDescription",
                ItemCategory.TOOL,
                ItemStatus.TO_LEND,
                geoList,
                "me"
        );
        repo.save(newItem);


        //WHEN
        mvc.perform(delete("/api/item/1"))
                //THEN
                .andExpect(status().isOk());
        Assertions.assertFalse(repo.existsById("1"));
    }

    @Test
    void updateItem_updateTool() throws Exception {
        //GIVEN
        List<Double> geoList = new ArrayList<>(Arrays.asList(48.8566, 2.3522));

        Item newItem = new Item("1",
                "testName",
                "testImgUrl",
                "testDescription",
                ItemCategory.TOOL,
                ItemStatus.TO_LEND,
                geoList,
                "me"
        );
        repo.deleteAll();
        repo.save(newItem);

        Uploader mockUploader = mock(Uploader.class);
        when(mockUploader.upload(any(), anyMap())).thenReturn(Map.of("secure_url", "testImgUrl"));
        when(cloudinary.uploader()).thenReturn(mockUploader);

        //WHEN
/*        mvc.perform(put("/api/item/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""*/

        mvc.perform(MockMvcRequestBuilders.multipart("/api/item/1")
                        .file(new MockMultipartFile("image", "test.jpg", "image/jpeg", "test image".getBytes()))
                        .file(new MockMultipartFile("itemDTO", "", "application/json", """                                                         
                                                     {
                                                     "id": "1",
                                                     "name": "testName",
                                                     "img": "testImgUrl",
                                                     "description": "testDescription",
                                                     "category": "TOOL",
                                                     "status": "TO_SELL",
                                                     "geocode": [48.8566, 2.3522],
                                                     "owner": "me"
                                                    }
                        """.getBytes()))
                        .contentType("multipart/form-data")
                        .with(request -> { request.setMethod("PUT"); return request; }))
                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                                         {
                                                         "id": "1",
                                                         "name": "testName",
                                                         "img": "testImgUrl",
                                                         "description": "testDescription",
                                                         "category": "TOOL",
                                                         "status": "TO_SELL",
                                                         "geocode": [48.8566, 2.3522],
                                                         "owner": "me"
                                                         }
                                """

                        )
                );
        Item savedItem = repo.findById("1").orElseThrow();
        Assertions.assertEquals(ItemStatus.TO_SELL ,savedItem.status());
        Assertions.assertEquals("testImgUrl", savedItem.img());
    }
}
