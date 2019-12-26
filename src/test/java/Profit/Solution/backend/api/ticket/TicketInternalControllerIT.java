package Profit.Solution.backend.api.ticket;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import Profit.Solution.backend.api.ticket.dto.in.TicketCreateDto;
import Profit.Solution.backend.api.ticket.dto.in.TicketSearchDto;
import Profit.Solution.backend.api.ticket.dto.in.TicketUpdateDto;
import Profit.Solution.backend.api.ticket.dto.out.TicketDto;
import Profit.Solution.backend.config.PostgresTestcontainersExtension;
import Profit.Solution.backend.model.ticket.TicketStatus;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@DBRider
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(PostgresTestcontainersExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TicketInternalControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private final UUID ticketId = UUID.fromString("00000000-0000-0000-0000-000000000000");

    private final String comment = "comment";

    private final String description = "description";

    private final String title = "title";

    private final UUID sender  =  UUID.fromString("00000000-0000-0000-0000-000000000000");
    private final UUID recipient  =  UUID.fromString("00000000-0000-0000-0000-000000000000");
    private final UUID manager  =  UUID.fromString("00000000-0000-0000-0000-000000000000");

    @Test
    @DataSet(value = "/datasets/ticket-controller-it/all.json", cleanBefore = true, cleanAfter = true)
    void getAll() throws Exception {
        // Actual
        List<TicketDto> actualResult = mapper.readValue(mockMvc.perform(MockMvcRequestBuilders.get("/tickets/all"))
                                                               .andExpect(MockMvcResultMatchers.status()
                                                                                               .isOk())
                                                               .andDo(print())
                                                               .andReturn()
                                                               .getResponse().getContentAsByteArray(), new TypeReference<List<TicketDto>>() {});

        // Assertion
        Assertions.assertThat(actualResult)
                  .extracting(TicketDto::getId,
                              TicketDto::getTitle,
                              TicketDto::getSender_id,
                              TicketDto::getRecipient_id,
                              TicketDto::getManager_id,
                              TicketDto::getComment,
                              TicketDto::getDescription,
                              TicketDto::getNumber,
                              TicketDto::getStatus)
//                              TicketDto::getUserId)
                  .containsExactlyInAnyOrder(Tuple.tuple(UUID.fromString("00000000-0000-0000-0000-000000000000"),
                                                         "title",
                                                          UUID.fromString("00000000-0000-0000-0000-000000000000"),
                                                          UUID.fromString("00000000-0000-0000-0000-000000000000"),
                                                          UUID.fromString("00000000-0000-0000-0000-000000000000"),
                                                         "comment",
                                                         "description", 1L,
                                                         TicketStatus.OPEN
//                                                         UUID.fromString("00000000-0000-0000-0000-000000000000")
                                                        ),
                                             Tuple.tuple(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                                                         "title2",
                                                         UUID.fromString("00000000-0000-0000-0000-000000000000"),
                                                         UUID.fromString("00000000-0000-0000-0000-000000000000"),
                                                         UUID.fromString("00000000-0000-0000-0000-000000000000"),
                                                         "comment2",
                                                         "description2", 2L,
                                                         TicketStatus.IN_PROGRESS
//                                                         UUID.fromString("00000000-0000-0000-0000-000000000001"
                                                         ));

    }

    @Test
    @DataSet(value = "/datasets/ticket-controller-it/create.json", cleanAfter = true, cleanBefore = true)
    @ExpectedDataSet(value = "/datasets/ticket-controller-it/create__expected.json")
    void create() throws Exception {
        // Arrange
        TicketCreateDto ticketCreateDto = TicketCreateDto.builder()
                                                         .comment(comment)
                                                         .description(description)
                                                         .fromAds(true)
                                                         .title(title)
                                                         .sender_id(sender)
                                                         .recipient_id(recipient)
                                                         .manager_id(manager)
//                                                         .userId(userId)
                                                         .build();

        // Actual
        mapper.readValue(mockMvc.perform(MockMvcRequestBuilders.post("/tickets/create")
                                                               .content(mapper.writeValueAsBytes(ticketCreateDto))
                                                               .contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(MockMvcResultMatchers.status()
                                                                .isCreated())
                                .andDo(print())
                                .andReturn()
                                .getResponse().getContentAsByteArray(), TicketDto.class);
    }

    @Test
    @DataSet(value = "/datasets/ticket-controller-it/get.json", cleanBefore = true, cleanAfter = true)
    void get() throws Exception {
        // Actual
        TicketDto actualResult = mapper.readValue(mockMvc.perform(MockMvcRequestBuilders.get("/tickets/{ticketId}", ticketId))
                                                         .andExpect(MockMvcResultMatchers.status()
                                                                                         .isOk())
                                                         .andDo(print())
                                                         .andReturn()
                                                         .getResponse().getContentAsByteArray(), TicketDto.class);

        // Assertion
        Assertions.assertThat(actualResult).isNotNull();
        Assertions.assertThat(actualResult.getComment()).isEqualTo(comment);
        Assertions.assertThat(actualResult.getDescription()).isEqualTo(description);
        Assertions.assertThat(actualResult.getTitle()).isEqualTo(title);
        Assertions.assertThat(actualResult.getSender_id()).isEqualTo(sender);
        Assertions.assertThat(actualResult.getManager_id()).isEqualTo(manager);
        Assertions.assertThat(actualResult.getRecipient_id()).isEqualTo(recipient);
        Assertions.assertThat(actualResult.getId()).isEqualTo(ticketId);
        Assertions.assertThat(actualResult.getNumber()).isEqualTo(1L);
//        Assertions.assertThat(actualResult.getUserId()).isEqualTo(userId);
        Assertions.assertThat(actualResult.getStatus()).isEqualTo(TicketStatus.OPEN);
        Assertions.assertThat(actualResult.getCompletionDate()).isNull();
        Assertions.assertThat(actualResult.getCreationDate()).isNotNull();
    }

    @Test
    @DataSet(value = "/datasets/ticket-controller-it/update.json", cleanBefore = true, cleanAfter = true)
//    @ExpectedDataSet(value = "/datasets/ticket-controller-it/update__expected.json")
    void update() throws Exception {
        // Actual
        TicketUpdateDto ticketUpdateDto = TicketUpdateDto.builder()
                                                         .title("title2")
                                                        .sender_id(sender)
                                                        .recipient_id(recipient)
                                                        .manager_id(manager)
                                                         .status(TicketStatus.COMPLETED)
                                                         .comment("comment2")
                                                         .description("description2")
                                                         .fromAds(false)
//                                                         .userId(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                                                         .build();

        TicketDto actualResult = mapper.readValue(mockMvc.perform(MockMvcRequestBuilders.post("/tickets/{ticketId}", ticketId)
                                                                                        .content(mapper.writeValueAsBytes(ticketUpdateDto))
                                                                                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                                                         .andExpect(MockMvcResultMatchers.status()
                                                                                         .isOk())
                                                         .andDo(print())
                                                         .andReturn()
                                                         .getResponse().getContentAsByteArray(), TicketDto.class);

        // Assertion
        Assertions.assertThat(actualResult).isNotNull();
        Assertions.assertThat(actualResult.getComment()).isEqualTo("comment2");
        Assertions.assertThat(actualResult.getDescription()).isEqualTo("description2");
        Assertions.assertThat(actualResult.getTitle()).isEqualTo("title2");
        Assertions.assertThat(actualResult.getId()).isEqualTo(ticketId);
        Assertions.assertThat(actualResult.getNumber()).isEqualTo(1L);
        Assertions.assertThat(actualResult.getRecipient_id()).isEqualTo(recipient);
        Assertions.assertThat(actualResult.getSender_id()).isEqualTo(sender);
        Assertions.assertThat(actualResult.getManager_id()).isEqualTo(manager);
//        Assertions.assertThat(actualResult.getUserId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        Assertions.assertThat(actualResult.getStatus()).isEqualTo(TicketStatus.COMPLETED);
        Assertions.assertThat(actualResult.getCompletionDate()).isNull();
        Assertions.assertThat(actualResult.getCreationDate()).isNotNull();
    }

    @Test
    @DataSet(value = "/datasets/ticket-controller-it/all-with-param.json", cleanBefore = true, cleanAfter = true)
    void getAllByParam() throws Exception {
        // Arrange
        LocalDateTime dateFrom = LocalDateTime.of(2019, Month.APRIL, 6, 0, 0, 0);
        LocalDateTime dateTo = LocalDateTime.of(2019, Month.APRIL, 8, 23, 59, 59);
        TicketSearchDto ticketSearchDto = TicketSearchDto.builder()
                                                         .sender_id(sender)
                                                         .recipient_id(recipient)
                                                         .manager_id(manager)
                                                         .comment(comment)
                                                         .description(description)
                                                         .title(title)
                                                         .fromAds(true)
                                                         .status(TicketStatus.OPEN)
//                                                         .userId(userId)
                                                         .number(1L)
                                                         .completionDateFrom(dateFrom)
                                                         .completionDateTo(dateTo)
                                                         .build();

        // Actual
        List<TicketDto> actualResult = mapper.readValue(mockMvc.perform(MockMvcRequestBuilders.get("/tickets/search")
                                                                                                .param("sender_id", "00000000-0000-0000-0000-000000000000")
                                                                                                .param("recipient_id", "00000000-0000-0000-0000-000000000000")
                                                                                                .param("manager_id", "00000000-0000-0000-0000-000000000000")
                                                                                             .param("comment", comment)
                                                                                              .param("description", description)
                                                                                              .param("title", title)
                                                                                              .param("fromAds", "true")
                                                                                              .param("status", "OPEN")
//                                                                                              .param("userId", "00000000-0000-0000-0000-000000000000")
                                                                                              .param("creationDateFrom", dateFrom.format(DateTimeFormatter.ISO_DATE_TIME))
                                                                                              .param("creationDateTo", dateTo.format(DateTimeFormatter.ISO_DATE_TIME))
                                                                       )
                                                               .andExpect(MockMvcResultMatchers.status()
                                                                                               .isOk())
                                                               .andDo(print())
                                                               .andReturn()
                                                               .getResponse().getContentAsByteArray(), new TypeReference<List<TicketDto>>() {});

        // Assertion
        Assertions.assertThat(actualResult)
                  .extracting(
                            TicketDto::getId,
                              TicketDto::getTitle,
                              TicketDto::getComment,
                              TicketDto::getDescription,
                              TicketDto::getNumber,
                              TicketDto::getStatus)
//                              TicketDto::getUserId)
                  .containsExactly(Tuple.tuple(
                          UUID.fromString("00000000-0000-0000-0000-000000000000"),
                                                         "title",
                          UUID.fromString("00000000-0000-0000-0000-000000000000"),
                          UUID.fromString("00000000-0000-0000-0000-000000000000"),
                          UUID.fromString("00000000-0000-0000-0000-000000000000"),
                                                         "comment",
                                                         "description", 1L,
                                                         TicketStatus.OPEN
//                                                         UUID.fromString("00000000-0000-0000-0000-000000000000")
                                                        ),
                                             Tuple.tuple(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                                                         "title",
                                                     UUID.fromString("00000000-0000-0000-0000-000000000000"),
                                                     UUID.fromString("00000000-0000-0000-0000-000000000000"),
                                                     UUID.fromString("00000000-0000-0000-0000-000000000000"),
                                                         "comment",
                                                         "description", 2L,
                                                         TicketStatus.OPEN));
//                                                         UUID.fromString("00000000-0000-0000-0000-000000000000")

    }

}