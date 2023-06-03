package ru.skydiver.backend.skydiver.controller;

import org.openapitools.api.AdminApi;
import org.openapitools.model.AddProductRequest;
import org.openapitools.model.Order;
import org.openapitools.model.OrderList;
import org.openapitools.model.Product;
import org.openapitools.model.User;
import org.openapitools.model.UserList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.skydiver.backend.skydiver.model.OrderStatuses;
import ru.skydiver.backend.skydiver.model.UserEntity;
import ru.skydiver.backend.skydiver.services.CategoryService;
import ru.skydiver.backend.skydiver.services.OrderService;
import ru.skydiver.backend.skydiver.services.ProductService;
import ru.skydiver.backend.skydiver.services.UserService;

@RestController
public class AdminController implements AdminApi {
    private final UserService userService;
    private final OrderService orderService;
    private final CategoryService categoryService;
    private final ProductService productService;

    public AdminController(UserService userService,
                           OrderService orderService,
                           CategoryService categoryService,
                           ProductService productService) {
        this.userService = userService;
        this.orderService = orderService;
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @Override
    public ResponseEntity<Void> banUser(String userName) {
        userService.banUser(userName);
        return ResponseEntity.ok(null);
    }

    @Override
    public ResponseEntity<Void> addCategory(
            String categoryName, Boolean mainCategory, String url) {
        categoryService.addCategory(categoryName, mainCategory, url);
        return ResponseEntity.ok(null);
    }

    @Override
    public ResponseEntity<Void> removeCategory(Integer categoryId) {
        categoryService.removeCategory(categoryId);
        return ResponseEntity.ok(null);
    }

    @Override
    public ResponseEntity<Void> addProduct(AddProductRequest addProductRequest) {
        productService.addProduct(new Product()
                .productDescription(addProductRequest.getDescription())
                .productImage(addProductRequest.getPicture())
                .name(addProductRequest.getName())
                .price(addProductRequest.getPrice())
                .categoryId(addProductRequest.getCategoryId()));
        return ResponseEntity.ok(null);
    }

    @Override
    public ResponseEntity<Void> removeProduct(Integer productId) {
        productService.removeProduct(productId);
        return ResponseEntity.ok(null);
    }

    @Override
    public ResponseEntity<Void> updateCategory(Integer categoryId, String categoryName, Boolean mainCategory,
                                               String url) {
        categoryService.updateCategory(
                categoryId,
                categoryName,
                mainCategory,
                url
        );
        return ResponseEntity.ok(null);
    }

    @Override
    public ResponseEntity<Void> updateProduct(AddProductRequest addProductRequest) {
        productService.updateProduct(addProductRequest);
        return ResponseEntity.ok(null);
    }

    @Override
    public ResponseEntity<Void> changeOrder(Integer orderId, String newStatus) {
        orderService.changeOrderStatus(orderId, OrderStatuses.valueOf(newStatus));
        return ResponseEntity.ok(null);
    }

    @Override
    public ResponseEntity<OrderList> getOrders() {
        return ResponseEntity.ok(
                new OrderList().orders(
                orderService.getOrders().stream()
                        .map(o -> new Order()
                                .id(o.getId())
                                .status(o.getStatus().name())
                                .user(o.getUserName()))
                        .toList()));
    }

    @Override
    public ResponseEntity<UserList> getUserList() {
        return ResponseEntity.ok(
                new UserList().users(
                        userService.getUserList().stream().map(AdminController::toUser).toList()));
    }

    private static User toUser(UserEntity entity) {
        return new User()
                .id(entity.getId())
                .name(entity.getName())
                .enabled(entity.getEnabled());
    }
}
