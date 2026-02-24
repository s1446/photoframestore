package com.photoframestore.controller;

import java.io.IOException;
import java.nio.file.*;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.photoframestore.entity.Frame;
import com.photoframestore.service.FrameService;
import com.photoframestore.service.OrderService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final FrameService frameService;
    private final OrderService orderService;

    public AdminController(FrameService frameService, OrderService orderService) {
        this.frameService = frameService;
        this.orderService = orderService;
    }

    // ✅ ADMIN DASHBOARD
    @GetMapping
    public String adminHome() {
        return "admin-dashboard";
    }

   /* // ✅ ADMIN VIEW FRAMES (IMPORTANT FIX)
    @GetMapping("/frames")
    public String adminFrames(Model model) {
        model.addAttribute("frames", frameService.getAllFrames(0, 0));
        return "frames"; // reuse same frames.html
    }*/

    // ADD FRAME PAGE
    @GetMapping("/add-frame")
    public String addFrameForm(Model model) {
        model.addAttribute("frame", new Frame());
        return "add-frame";
    }

    // ADD FRAME
    @PostMapping("/add-frame")
    public String addFrame(
            @RequestParam String name,
            @RequestParam String size,
            @RequestParam double price,
            @RequestParam int stock,
            @RequestParam String description,
            @RequestParam("image") MultipartFile image
    ) throws IOException {

        String uploadDir = new ClassPathResource("static/images").getFile().getAbsolutePath();
        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);

        Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        Frame frame = new Frame();
        frame.setName(name);
        frame.setSize(size);
        frame.setPrice(price);
        frame.setStock(stock);
        frame.setDescription(description);
        frame.setImageUrl("/images/" + fileName);

        frameService.save(frame);
        return "redirect:/admin";
    }

    // DELETE FRAME
    @GetMapping("/delete-frame/{id}")
    public String deleteFrame(@PathVariable Long id) {
        frameService.deleteById(id);
        return "redirect:/admin/frames";
    }

    // ADMIN ORDERS
    @GetMapping("/orders")
    public String viewOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "admin-orders";
    }
}
