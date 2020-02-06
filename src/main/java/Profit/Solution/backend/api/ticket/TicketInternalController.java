package Profit.Solution.backend.api.ticket;

import Profit.Solution.backend.api.ticket.dto.in.TicketCreateDto;
import Profit.Solution.backend.api.ticket.dto.in.TicketUpdateDto;
import Profit.Solution.backend.api.ticket.dto.out.TicketDto;
import Profit.Solution.backend.api.ticket.mapper.TicketMapper;
import Profit.Solution.backend.service.ticket.TicketService;
import Profit.Solution.backend.service.ticket.argument.TicketCreateArgument;
import Profit.Solution.backend.service.ticket.argument.TicketUpdateArgument;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(value = "api/tickets")
@Api("Внутренний контроллер заявок")
public class TicketInternalController {

    private final TicketService ticketService;
    private final TicketMapper ticketMapper;

    @Autowired
    public TicketInternalController(TicketService ticketService, TicketMapper ticketMapper) {
        this.ticketService = ticketService;
        this.ticketMapper = ticketMapper;
    }

    @ApiOperation("Получить список заявок c пагинацией")
    @GetMapping(value = "/all/pagination")
    public List<TicketDto> getAll(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                  @RequestParam(value = "pageSize", defaultValue = Integer.MAX_VALUE + "") int pageSize) {
        return ticketMapper.toDtoListFromDB(ticketService.getAll(PageRequest.of(pageNo, pageSize)).getContent());
        // return ticketMapper.toDtoListFromDB(ticketService.getAll(PageRequest.of(pageNo, pageSize)).getContent());
    }

    @ApiOperation("Получить личные заявки")
    @GetMapping("/my-tickets/{id}/pagination")
    public List<TicketDto> findAllBySender(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                           @RequestParam(value = "pageSize", defaultValue = Integer.MAX_VALUE + "") int pageSize, @PathVariable UUID id) {
        return ticketMapper.toDtoListFromDB(ticketService.findMyTicketById(pageNo, pageSize, id).getContent());
    }

    @ApiOperation("Получить личные заявки")
    @GetMapping("/my-tickets/{id}")
    public List<TicketDto> findAllBySenderWithoutPages(@PathVariable UUID id) {
        return ticketMapper.toDtoListFromDB(ticketService.findMyTicketById(null, null, id).getContent());
    }

    @ApiOperation("Получить список заявок без пагинации")
    @GetMapping(value = "/all")
    public List<TicketDto> getAllWithoutPages() {
        return ticketMapper.toDtoListFromDB(ticketService.getAllWithoutPages());
    }

    @ApiOperation("Создать заявку")
    @PostMapping(value = "/create")
    @ResponseStatus(CREATED)
    public TicketDto create(@RequestBody TicketCreateDto dto) {
        return ticketMapper.toDto(ticketService.create(
                TicketCreateArgument.builder()
                        .comment(dto.getComment())
                        .description(dto.getDescription())
                        .fromAds(dto.getFromAds())
                        .title(dto.getTitle())
                        .sender_id(dto.getSender_id())
                        .recipient_id(dto.getRecipient_id())
                        .manager_id(dto.getManager_id())
                        .operator_id(dto.getOperator_id())

//                                                                          .userId(dto.getUserId())
                        .build()));
    }

    @ApiOperation("Получить заявку по идентификатору")
    @GetMapping("/{id}")
    public TicketDto get(@PathVariable UUID id) {
        return ticketMapper.toDto(ticketService.getExisting(id));
    }

    @ApiOperation("Удалить заявку по идентификатору")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        ticketService.deleteTicket(id); //Add DTO
    }

    @ApiOperation("Подтвердить заявку")
    @PutMapping("/{id}/accepted")
    public TicketDto accepted(@PathVariable UUID id) {
        return ticketMapper.toDto(ticketService.accepted(id)); //DTO ???
    }

    @ApiOperation("Обновить заявку")
    @PutMapping("/{id}/update")
    public TicketDto update(@PathVariable UUID id,
                            @RequestBody TicketUpdateDto updateDto) {
        return ticketMapper.toDto(ticketService.update(id, TicketUpdateArgument.builder()
//                                                                              .userId(updateDto.getUserId())
                .comment(updateDto.getComment())
                .sender_id(updateDto.getSender_id())
                .recipient_id(updateDto.getRecipient_id())
                .manager_id(updateDto.getManager_id())
                .operator_id(updateDto.getOperator_id())
                .accepted(updateDto.getAccepted())
                .fromAds(updateDto.getFromAds())
                .description(updateDto.getDescription())
                .title(updateDto.getTitle())
                .status(updateDto.getStatus())
                .build()));
    }


    @ApiOperation("Получить заявки по параметрам с сортировкой и пейджинацией и поиском")
    @GetMapping("/search/{string}")
    public List<TicketDto> getAllSearch(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo, @RequestParam(value = "pageSize", defaultValue = Integer.MAX_VALUE + "") int pageSize, @PathVariable String string) {
        return ticketMapper.toDtoListFromDB(ticketService.getAllByAttr(pageNo, pageSize, string).getContent());
    }


//    @ApiOperation("Получить заявки по параметрам с сортировкой и пейджинацией")
//    @GetMapping("/search")
//    public List<TicketDto> getAllByParam(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
//                                         @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
//                                         @RequestParam(value = "orderBy", required = false, defaultValue = "creationDate") String orderBy,
//                                         @RequestParam(value = "orderByDirection", required = false, defaultValue = "DESC") Sort.Direction orderByDirection,
//                                         TicketSearchDto searchDto) {
//        return ticketMapper.toDtoListFromDB(ticketService.getAllByParam(TicketSearchArgument.builder()
//                                                                                   .comment(searchDto.getComment())
//                                                                                   .description(searchDto.getDescription())
//                                                                                   .title(searchDto.getTitle())
////                                                                                   .userId(searchDto.getUserId())
//                                                                                   .fromAds(searchDto.getFromAds())
////                                                                                   .completionDateFrom(searchDto.getCompletionDateFrom())
//                                                                                   .creationDateFrom(searchDto.getCreationDateFrom())
//                                                                                   .creationDateTo(searchDto.getCreationDateTo())
////                                                                                   .completionDateTo(searchDto.getCompletionDateTo())
//                                                                                   .number(searchDto.getNumber())
//                                                                                   .status(searchDto.getStatus())
//                                                                                   .build(),
//                                                               PageRequest.of(pageNo, pageSize, Sort.by(orderByDirection, orderBy)))
//                                                .getContent());
//    }
}
