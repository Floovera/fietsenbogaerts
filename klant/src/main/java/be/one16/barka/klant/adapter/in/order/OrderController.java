package be.one16.barka.klant.adapter.in.order;

import be.one16.barka.klant.adapter.in.materiaal.MateriaalDto;
import be.one16.barka.klant.adapter.in.werkuur.WerkuurDto;
import be.one16.barka.klant.adapter.mapper.materiaal.MateriaalDtoMapper;
import be.one16.barka.klant.adapter.mapper.order.OrderDtoMapper;
import be.one16.barka.klant.adapter.mapper.werkuur.WerkuurDtoMapper;
import be.one16.barka.klant.common.exceptions.KlantNotFoundException;
import be.one16.barka.klant.domain.Werkuur;
import be.one16.barka.klant.ports.in.order.*;
import be.one16.barka.klant.ports.in.werkuur.*;
import be.one16.barka.klant.domain.Materiaal;
import be.one16.barka.klant.ports.in.materiaal.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderQuery orderQuery;
    private final CreateOrderUnitOfWork createOrderUnitOfWork;
    private final UpdateOrderUnitOfWork updateOrderUnitOfWork;
    private final DeleteOrderUnitOfWork deleteOrderUnitOfWork;
    private final OrderDtoMapper orderDtoMapper;

    private final WerkurenQuery werkurenQuery;
    private final WerkuurDtoMapper werkuurDtoMapper;
    private final CreateWerkuurUnitOfWork createWerkuurUnitOfWork;
    private final UpdateWerkuurUnitOfWork updateWerkuurUnitOfWork;
    private final DeleteWerkuurUnitOfWork deleteWerkuurUnitOfWork;

    private final MaterialenQuery materialenQuery;
    private final MateriaalDtoMapper materiaalDtoMapper;
    private final CreateMateriaalUnitOfWork createMateriaalUnitOfWork;
    private final UpdateMateriaalUnitOfWork updateMateriaalUnitOfWork;
    private final DeleteMateriaalUnitOfWork deleteMateriaalUnitOfWork;

    public OrderController(OrderQuery orderQuery, CreateOrderUnitOfWork createOrderUnitOfWork, UpdateOrderUnitOfWork updateOrderUnitOfWork, DeleteOrderUnitOfWork deleteOrderUnitOfWork, OrderDtoMapper orderDtoMapper, WerkurenQuery werkurenQuery, WerkuurDtoMapper werkuurDtoMapper, CreateWerkuurUnitOfWork createWerkuurUnitOfWork, UpdateWerkuurUnitOfWork updateWerkuurUnitOfWork, DeleteWerkuurUnitOfWork deleteWerkuurUnitOfWork, MaterialenQuery materialenQuery, MateriaalDtoMapper materiaalDtoMapper, CreateMateriaalUnitOfWork createMateriaalUnitOfWork, UpdateMateriaalUnitOfWork updateMateriaalUnitOfWork, DeleteMateriaalUnitOfWork deleteMateriaalUnitOfWork) {
        this.orderQuery = orderQuery;
        this.createOrderUnitOfWork = createOrderUnitOfWork;
        this.updateOrderUnitOfWork = updateOrderUnitOfWork;
        this.deleteOrderUnitOfWork = deleteOrderUnitOfWork;
        this.orderDtoMapper = orderDtoMapper;
        this.werkurenQuery = werkurenQuery;
        this.werkuurDtoMapper = werkuurDtoMapper;
        this.createWerkuurUnitOfWork = createWerkuurUnitOfWork;
        this.updateWerkuurUnitOfWork = updateWerkuurUnitOfWork;
        this.deleteWerkuurUnitOfWork = deleteWerkuurUnitOfWork;
        this.materialenQuery = materialenQuery;
        this.materiaalDtoMapper = materiaalDtoMapper;
        this.createMateriaalUnitOfWork = createMateriaalUnitOfWork;
        this.updateMateriaalUnitOfWork = updateMateriaalUnitOfWork;
        this.deleteMateriaalUnitOfWork = deleteMateriaalUnitOfWork;
    }

    @GetMapping("/{id}")
    OrderDto getOrderById(@PathVariable("id") UUID orderId) {
        return orderDtoMapper.mapOrderToDto(orderQuery.retrieveOrderById(orderId));
    }

    @GetMapping
    Page<OrderDto> getOrdersFiltered(@RequestParam(name = "naam", required = false) String naam, Pageable pageable) {
        return orderQuery.retrieveOrdersByFilterAndSort(new RetrieveOrderFilterAndSortCommand(naam, pageable))
                .map(orderDtoMapper::mapOrderToDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UUID createOrder(@RequestBody OrderDto order) throws KlantNotFoundException {
        CreateOrderCommand createOrderCommand = orderDtoMapper.mapDtoToCreateOrderCommand(order);
        return createOrderUnitOfWork.createOrder(createOrderCommand);
    }

    @PutMapping("/{id}")
    void updateOrder(@PathVariable("id") UUID orderId, @RequestBody OrderDto order) throws KlantNotFoundException {
        updateOrderUnitOfWork.updateOrder(orderDtoMapper.mapDtoToUpdateOrderCommand(order,orderId));
    }

    @DeleteMapping("/{id}")
    void deleteOrder(@PathVariable("id") UUID orderId) {
        deleteOrderUnitOfWork.deleteOrder(orderId);
    }

    @GetMapping("/{id}/werkuren")
    List<WerkuurDto> getWerkurenOfOrder(@PathVariable("id") UUID orderId) {
        return werkurenQuery.retrieveWerkurenOfOrder(orderId).stream().map(werkuurDtoMapper::mapWerkuurToDto).toList();
    }

    @GetMapping("/{id}/werkuren/{werkuurId}")
    WerkuurDto getWerkuurOfOrder(@PathVariable("id") UUID orderId, @PathVariable("werkuurId") UUID werkuurId) {
        Werkuur werkuur = werkurenQuery.retrieveWerkuurById(werkuurId, orderId);
        return werkuurDtoMapper.mapWerkuurToDto(werkuur);
    }

    @PostMapping("/{id}/werkuren")
    @ResponseStatus(HttpStatus.CREATED)
    UUID createWerkuurOfOrder(@PathVariable("id") UUID orderId, @RequestBody WerkuurDto werkuurDto) {
        return createWerkuurUnitOfWork.createWerkuur(werkuurDtoMapper.mapDtoToCreateWerkuurCommand(werkuurDto, orderId));
    }

    @PutMapping("/{id}/werkuren/{werkuurId}")
    void updateWerkuurOfOrder(@PathVariable("id") UUID orderId, @PathVariable("werkuurId") UUID werkuurId, @RequestBody WerkuurDto werkuurDto) {
        updateWerkuurUnitOfWork.updateWerkuur(werkuurDtoMapper.mapDtoToUpdateWerkuurCommand(werkuurDto, werkuurId, orderId));
    }

    @DeleteMapping("/{id}/werkuren/{werkuurId}")
    void deleteWerkuurOfOrder(@PathVariable("id") UUID orderId, @PathVariable("werkuurId") UUID werkuurId) {
        deleteWerkuurUnitOfWork.deleteWerkuur(new DeleteWerkuurCommand(werkuurId, orderId));
    }

    @GetMapping("/{id}/materialen")
    List<MateriaalDto> getMaterialenOfOrder(@PathVariable("id") UUID orderId) {
        return materialenQuery.retrieveMaterialenOfOrder(orderId).stream().map(materiaalDtoMapper::mapMateriaalToDto).toList();
    }

    @GetMapping("/{id}/materialen/{materiaalId}")
    MateriaalDto getMateriaalOfOrde(@PathVariable("id") UUID orderId, @PathVariable("materiaalId") UUID materiaalId) {
        Materiaal materiaal = materialenQuery.retrieveMateriaalById(materiaalId,orderId);
        return materiaalDtoMapper.mapMateriaalToDto(materiaal);

    }

    @PostMapping("/{id}/materialen")
    @ResponseStatus(HttpStatus.CREATED)
    UUID createMateriaalOfOrder(@PathVariable("id") UUID orderId, @RequestBody MateriaalDto materiaalDto) {
        return createMateriaalUnitOfWork.createMateriaal(materiaalDtoMapper.mapDtoToCreateMateriaalCommand(materiaalDto,orderId));
    }

    @PutMapping("/{id}/materialen/{materiaalId}")
    void updateMateriaalOfOrder(@PathVariable("id") UUID orderId, @PathVariable("materiaalId") UUID materiaalId, @RequestBody MateriaalDto materiaalDto) {
        updateMateriaalUnitOfWork.updateMateriaal(materiaalDtoMapper.mapDtoToUpdateMateriaalCommand(materiaalDto,materiaalId,orderId));
    }

    @DeleteMapping("/{id}/materialen/{materiaalId}")
    void deleteMateriaalOfOrder(@PathVariable("id") UUID orderId, @PathVariable("materiaalId") UUID materiaalId) {
        deleteMateriaalUnitOfWork.deleteMateriaal(new DeleteMateriaalCommand(materiaalId,orderId));
    }

}
