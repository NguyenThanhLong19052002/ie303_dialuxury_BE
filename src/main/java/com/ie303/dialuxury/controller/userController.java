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
    private static final String SECRET_KEY = "your-secret-key";
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
        try {
            // Xác thực thông tin đăng nhập
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );

            // Tạo thông tin người dùng
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

            // Tạo token JWT
            String token = Jwts.builder()
                    .setSubject(userDetails.getUsername())
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                    .compact();

            // Trả về token
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    // Các API và phương thức khác cho đăng nhập
}
