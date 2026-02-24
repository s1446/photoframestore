package com.photoframestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.photoframestore.entity.Frame;
import com.photoframestore.entity.Order;
import com.photoframestore.entity.User;
import com.photoframestore.repository.UserRepository;
import com.photoframestore.service.FrameService;
import com.photoframestore.service.OrderService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private FrameService frameService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderService orderService;
    // HOME
    @GetMapping("/")
    public String home() {
        return "index";
    }

    // CONTACT
    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    // LOGIN
    @GetMapping("/login")
    public String login(HttpSession session) {
        session.invalidate();
        return "login";
    }
    @PostMapping("/place-order")
    public String placeOrder(
            @RequestParam String address,
            HttpSession session) {

        User user = (User) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/login";
        }

        Order order = new Order();
        order.setUsername(user.getName());
        order.setAddress(address);
        order.setTotalAmount(2499); // later calculate from cart
        order.setOrderDate(java.time.LocalDateTime.now());
        order.setStatus("PLACED");

        orderService.saveOrder(order);

        return "redirect:/order-success";
    }
    // REGISTER PAGE
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    // REGISTER FORM SUBMIT
    @PostMapping("/register")
    public String registerUser(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password) {

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole("USER");

        userRepository.save(user);

        return "redirect:/login?registered";
    }

    // FRAME DETAILS
    @GetMapping("/frames/{id}")
    public String frameDetails(@PathVariable Long id, Model model) {

        Frame frame = frameService.getById(id)
                .orElseThrow(() -> new RuntimeException("Frame not found"));

        model.addAttribute("frame", frame);
        return "frame-details";
    }

    // FRAMES WITH PAGINATION
    @GetMapping("/frames")
    public String showFrames(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        Page<Frame> framePage = frameService.getAllFrames(page, size);

        model.addAttribute("frames", framePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", framePage.getTotalPages());

        return "frames";
    }
}
