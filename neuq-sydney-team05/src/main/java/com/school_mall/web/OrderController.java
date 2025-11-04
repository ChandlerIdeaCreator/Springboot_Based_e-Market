package com.school_mall.web;

import com.school_mall.entity.Order;
import com.school_mall.entity.OrderItem;
import com.school_mall.entity.Product;
import com.school_mall.entity.ProductStyle;
import com.school_mall.entity.UserAddress;
import com.school_mall.repository.OrderItemMapper;
import com.school_mall.repository.OrderMapper;
import com.school_mall.repository.ProductMapper;
import com.school_mall.repository.ProductStyleMapper;
import com.school_mall.security.UserContext;
import com.school_mall.service.OrderService;
import com.school_mall.service.UserAddressService;
import com.school_mall.web.dto.OrderDtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductStyleMapper productStyleMapper;
    @Autowired
    private UserAddressService addressService;

    @GetMapping("/orders")
    public ApiResponse<List<Order>> listOrders(@RequestParam(defaultValue = "id") String sort,
                                               @RequestParam(defaultValue = "desc") String order,
                                               @RequestParam(defaultValue = "10") int size,
                                               @RequestParam(defaultValue = "0") int page) {
        Long userId = UserContext.userId();
        int limit = Math.max(1, Math.min(size, 100));
        int offset = Math.max(0, page) * limit;
        String sortColumn = switch (sort) {
            case "created", "create_time" -> "create_time";
            case "amount", "total_amount" -> "total_amount";
            default -> "id";
        };
        String sortOrder = "asc".equalsIgnoreCase(order) ? "ASC" : "DESC";
        List<Order> data = orderMapper.selectByUserPaged(userId, sortColumn, sortOrder, limit, offset);
        return ApiResponse.ok(data);
    }

    @GetMapping("/orders/{orderId}")
    public ApiResponse<Map<String, Object>> getOrder(@PathVariable Long orderId) {
        Order o = orderService.getById(orderId);
        if (o == null || !Objects.equals(o.getUserId(), UserContext.userId())) return ApiResponse.fail("订单不存在或无权限");
        Map<String, Object> r = new HashMap<>();
        r.put("order", o);
        r.put("items", orderItemMapper.selectByOrderId(orderId));
        return ApiResponse.ok(r);
    }

    @PostMapping("/orders")
    public ApiResponse<Order> createOrder(@RequestBody OrderDtos.CreateOrderReq req) {
        Long userId = UserContext.userId();
        UserAddress addr = addressService.getById(req.getAddressId());
        if (addr == null || !addr.getUserId().equals(userId)) return ApiResponse.fail("地址非法");

        Order order = new Order();
        order.setOrderNo(UUID.randomUUID().toString().replace("-", "").substring(0, 20));
        order.setUserId(userId);
        order.setOrderStatus(0);
        order.setReceiver(addr.getReceiver());
        order.setReceiverPhone(addr.getPhone());
        order.setRegion(addr.getRegion());
        order.setDetailAddress(addr.getDetailAddress());
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());

        List<OrderItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        for (OrderDtos.OrderItemReq ir : req.getItems()) {
            Product p = productMapper.selectById(ir.getProductId());
            if (p == null) return ApiResponse.fail("商品不存在: " + ir.getProductId());
            ProductStyle style = null;
            String styleName = null;
            if (ir.getStyleId() != null) {
                style = productStyleMapper.selectById(ir.getStyleId());
                styleName = style != null ? style.getStyleName() : null;
            }
            BigDecimal unit = p.getDiscountPrice();
            BigDecimal line = unit.multiply(BigDecimal.valueOf(ir.getCount()));
            total = total.add(line);

            OrderItem oi = new OrderItem();
            oi.setProductId(p.getId());
            oi.setStyleId(ir.getStyleId());
            oi.setProductName(p.getProductName());
            oi.setProductImg(p.getProductImg());
            oi.setStyleName(styleName);
            oi.setProductCount(ir.getCount());
            oi.setUnitPrice(unit);
            oi.setTotalAmount(line);
            oi.setCreateTime(LocalDateTime.now());
            oi.setUpdateTime(LocalDateTime.now());
            items.add(oi);
        }
        order.setTotalAmount(total);

        orderMapper.insertSelective(order);
        for (OrderItem oi : items) {
            oi.setOrderId(order.getId());
        }
        if (!items.isEmpty()) {
            orderItemMapper.batchInsert(items);
        }
        return ApiResponse.ok(orderService.getById(order.getId()));
    }

    @PostMapping("/orders/{orderId}/pay")
    public ApiResponse<Boolean> pay(@PathVariable Long orderId) {
        Order order = orderService.getById(orderId);
        if (order == null || !Objects.equals(order.getUserId(), UserContext.userId())) return ApiResponse.fail("订单不存在或无权限");
        if (order.getOrderStatus() != null && order.getOrderStatus() != 0) return ApiResponse.fail("订单状态不允许支付");
        order.setOrderStatus(1);
        order.setPayTime(LocalDateTime.now());
        orderService.update(order);
        return ApiResponse.ok(true);
    }
} 