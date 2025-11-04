package com.school_mall.web;

import com.school_mall.entity.Product;
import com.school_mall.entity.ProductComment;
import com.school_mall.entity.ProductStyle;
import com.school_mall.entity.User;
import com.school_mall.repository.ProductCommentMapper;
import com.school_mall.repository.ProductStyleMapper;
import com.school_mall.repository.ProductMapper;
import com.school_mall.security.UserContext;
import com.school_mall.service.ProductCommentService;
import com.school_mall.service.ProductService;
import com.school_mall.service.UserService;
import com.school_mall.web.dto.CommentDtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductStyleMapper styleMapper;
    @Autowired
    private ProductCommentMapper commentMapper;
    @Autowired
    private ProductCommentService commentService;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private UserService userService;

    @GetMapping("/products")
    public ApiResponse<List<Product>> listProducts() {
        return ApiResponse.ok(productService.getAll());
    }

    @GetMapping("/products/paged")
    public ApiResponse<List<Product>> listProductsPaged(@RequestParam(required = false) String name,
                                                        @RequestParam(defaultValue = "id") String sort,
                                                        @RequestParam(defaultValue = "desc") String order,
                                                        @RequestParam(defaultValue = "10") int size,
                                                        @RequestParam(defaultValue = "0") int page) {
        int limit = Math.max(1, Math.min(size, 100));
        int offset = Math.max(0, page) * limit;
        String sortColumn = switch (sort) { // whitelist
            case "price", "discount_price" -> "discount_price";
            case "created", "create_time" -> "create_time";
            case "name", "product_name" -> "product_name";
            default -> "id";
        };
        String sortOrder = "asc".equalsIgnoreCase(order) ? "ASC" : "DESC";
        List<Product> data = productMapper.selectPaged(name, sortColumn, sortOrder, limit, offset);
        return ApiResponse.ok(data);
    }

    @GetMapping("/products/{productId}")
    public ApiResponse<Map<String, Object>> getProductDetail(@PathVariable Long productId) {
        Product product = productService.getById(productId);
        if (product == null) {
            return ApiResponse.fail("商品不存在");
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("product", product);
        result.put("styles", styleMapper.selectByProductId(productId));
        result.put("commentCount", commentService.getCommentCount(productId));
        
        return ApiResponse.ok(result);
    }

    @GetMapping("/products/{productId}/styles")
    public ApiResponse<List<ProductStyle>> listStyles(@PathVariable Long productId) {
        return ApiResponse.ok(styleMapper.selectByProductId(productId));
    }

    @GetMapping("/products/{productId}/comments")
    public ApiResponse<List<ProductComment>> listComments(@PathVariable Long productId) {
        return ApiResponse.ok(commentMapper.selectByProductId(productId));
    }

    @PostMapping("/products/{productId}/comments")
    public ApiResponse<ProductComment> addComment(@PathVariable Long productId,
                                                  @RequestBody CommentDtos.AddCommentReq req) {
        Long userId = UserContext.userId();
        User user = userService.getById(userId);
        
        ProductComment comment = new ProductComment();
        comment.setProductId(productId);
        comment.setUserId(userId);
        comment.setNickname(req.getIsAnonymous() == 1 ? "匿名用户" : user.getNickname());
        comment.setCommentContent(req.getCommentContent());
        comment.setCommentImg(req.getCommentImg());
        comment.setIsAnonymous(req.getIsAnonymous());
        
        return ApiResponse.ok(commentService.addComment(comment));
    }

    @DeleteMapping("/products/{productId}/comments/{commentId}")
    public ApiResponse<Boolean> deleteComment(@PathVariable Long productId,
                                              @PathVariable Long commentId) {
        Long userId = UserContext.userId();
        ProductComment comment = commentService.getById(commentId);
        
        if (comment == null || !comment.getProductId().equals(productId)) {
            return ApiResponse.fail("评论不存在");
        }
        
        if (!comment.getUserId().equals(userId)) {
            return ApiResponse.fail("无权限删除此评论");
        }
        
        return ApiResponse.ok(commentService.deleteById(commentId));
    }
} 
