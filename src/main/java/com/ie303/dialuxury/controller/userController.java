package com.ie303.dialuxury.controller;
import com.ie303.dialuxury.model.user;
import com.ie303.dialuxury.service.userServiceImpl;
//import com.ie303.dialuxury.service.userService;
import com.ie303.dialuxury.repository.userRepository;
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
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;







import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin
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
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public String signUp(@RequestBody user user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return "Email này đã được đăng ký!";
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return "Đăng ký tài khoản thành công!";
    }

    @Autowired
    private userServiceImpl userDetailsService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody user user) {
        // Kiểm tra xem email tồn tại trong cơ sở dữ liệu hay không
        user existingUser = userRepository.findByEmail(user.getEmail());
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

            // Trả về token
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email hoặc password không hợp lệ");
        }
    }

    // Các API và phương thức khác cho đăng nhập
}
