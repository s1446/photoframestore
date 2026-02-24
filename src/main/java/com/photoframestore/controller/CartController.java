package com.photoframestore.controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.photoframestore.entity.Frame;
import com.photoframestore.service.FrameService;

@Controller
public class CartController {

    private final FrameService frameService;

    public CartController(FrameService frameService) {
        this.frameService = frameService;
    }

    // 🛒 ADD TO CART
    @GetMapping("/cart/add/{id}")
    public String addToCart(@PathVariable Long id, HttpSession session) {

        @SuppressWarnings("unchecked")
        List<Frame> cart = (List<Frame>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
        }

        Frame frame = frameService.getFrameById(id);
        if (frame != null) {
            cart.add(frame);
        }

        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }

    // 🧾 VIEW CART
    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {

        @SuppressWarnings("unchecked")
        List<Frame> cart = (List<Frame>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
        }

        double subtotal = cart.stream()
                .mapToDouble(Frame::getPrice)
                .sum();

        double delivery = cart.isEmpty() ? 0 : 50;

        model.addAttribute("cartItems", cart);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("delivery", delivery);
        model.addAttribute("total", subtotal + delivery);

        return "cart";
    }

    // 🗑 REMOVE ITEM FROM CART (BY FRAME ID)
    @GetMapping("/cart/remove/{id}")
    public String removeFromCart(@PathVariable Long id, HttpSession session) {

        @SuppressWarnings("unchecked")
        List<Frame> cart = (List<Frame>) session.getAttribute("cart");

        if (cart != null) {
            cart.removeIf(frame -> frame.getId().equals(id));
            session.setAttribute("cart", cart);
        }

        return "redirect:/cart";
    }

    // 📦 ADDRESS PAGE
    @GetMapping("/address")
    public String addressPage() {
        return "address";
    }
}
