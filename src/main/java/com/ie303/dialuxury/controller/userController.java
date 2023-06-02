package com.ie303.dialuxury.controller;

import com.ie303.dialuxury.model.user;
import com.ie303.dialuxury.model.order;
import com.ie303.dialuxury.model.product;
import com.ie303.dialuxury.service.userServiceImpl;
//import com.ie303.dialuxury.service.userService;
import com.ie303.dialuxury.repository.userRepository;
import com.ie303.dialuxury.repository.orderRepository;
import com.ie303.dialuxury.model.AuthResponse;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import io.jsonwebtoken.Jwts;

import java.util.Date;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.http.HttpStatus;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

import org.springframework.security.access.prepost.PreAuthorize;
import com.ie303.dialuxury.config.Error;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class userController {
//    @Autowired
//    private userService userService;
//    @PostMapping("")
//    public String add(@RequestBody user user){
//        userService.saveuser(user);
//        return "New user is added";
//    }
//
//    @GetMapping("")
//    public List<user> list(){
//        return userService.getAllusers();
//    }

    //    Đăng nhập đăng ký
    // Các mã thông báo JWT và các hằng số khác
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 86400000; // 24 giờ

    @Autowired
    private userRepository userRepository;

    @Autowired
    private orderRepository orderRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<Error> signUp(@RequestBody user user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            Error error = new Error();
            error.setMessage("Email này đã được đăng ký!");
            error.setErrorCode(500); // Mã lỗi tùy chọn

            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }


    @Autowired
    private userServiceImpl userDetailsService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody user user) {
        // Kiểm tra xem email tồn tại trong cơ sở dữ liệu hay không
        user existingUser = userRepository.findByEmail(user.getEmail());
        String userId = existingUser.getUserId();
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email không tồn tại");
        }


        try {
            // Xác thực thông tin đăng nhập
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );

            // Tạo key từ chuỗi SECRET_KEY
            // SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

            // Tạo thông tin người dùng
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

            // Tạo token JWT
            String token = Jwts.builder()
                    .setSubject(userDetails.getUsername())
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                    .compact();

            AuthResponse response = new AuthResponse(token, existingUser.getUserId(), existingUser.getRole());

            // Trả về token
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email hoặc password không hợp lệ");
        }
    }

    // Các API và phương thức khác cho đăng nhập

    //    lấy thông tin 1 user
    @GetMapping("/{userId}")
    public ResponseEntity<user> getUser(@PathVariable String userId) {
        user user = userRepository.findByUserId(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //    sửa thông tin 1 user
    @PatchMapping("/{userId}")
    public ResponseEntity<user> updateUser(@PathVariable String userId, @RequestBody user updatedUser) {
        user user = userRepository.findByUserId(userId);
        if (user != null) {
            // Kiểm tra và cập nhật các trường không phải là email
            if (updatedUser.getName() != null) {
                user.setName(updatedUser.getName());
            }
            if (updatedUser.getGender() != null) {
                user.setGender(updatedUser.getGender());
            }
            if (updatedUser.getPhoneNumber() != null) {
                user.setPhoneNumber(updatedUser.getPhoneNumber());
            }
            if (updatedUser.getAddress() != null) {
                user.setAddress(updatedUser.getAddress());
            }
            if (updatedUser.getPassword() != null) {
                user.setPassword(updatedUser.getPassword());
            }
            if (updatedUser.getRole() != null) {
                user.setRole(updatedUser.getRole());
            }

            userRepository.save(user);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // API thêm hóa đơn
    @PostMapping("/{userId}/order")
    public String addOrder(@PathVariable String userId, @RequestBody order order) {
        try {
            order isExistsOrder = orderRepository.findByMahd(order.getMahd());
            if (isExistsOrder != null) {
                return "Mã hóa đơn này đã tồn tại";
            }
            Long tongtien = 0L;
            order.setTinhtrang("chưa xử lý");
            order.setNgaylap(new Date());
            order.setUserId(userId);
            for (product product : order.getSanphams()) {
                tongtien += product.getPrice().longValue();
            }
            order.setTongtien(tongtien);
            orderRepository.save(order);
            return "Thêm hóa đơn thành công";
        } catch (Exception ex) {
            return "Có lỗi xảy ra" + ex.getMessage();
        }

    }

    // API xóa hóa đơn
    @DeleteMapping("/order/{mahd}")
    public String deleteOrder(@PathVariable String mahd) {
        try {
            order order = orderRepository.findByMahdAndTinhtrang(mahd, "chưa xử lý");
            if (order != null) {
                orderRepository.delete(order);
                return "Xóa hóa đơn thành công";
            } else {
                return "Xóa hóa đơn thất bại";
            }
        } catch (Exception ex) {
            return "Có lỗi xảy ra" + ex.getMessage();
        }

    }

    //    API lấy ra tất cả các hóa đơn của 1 user
    @GetMapping("/{userId}/order")
    public ResponseEntity<order> getOrder(@PathVariable String userId) {
        order order = orderRepository.findByUserId(userId);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
