package org.example.controller;

import org.example.dao.UserDao;
import org.example.service.CookieService;
import org.example.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/admin/order")
public class OrderController {
   OrderService orderService;
    private CookieService cookieService;

    public OrderController(OrderService orderService, CookieService cookieService) {
        this.orderService = orderService;
        this.cookieService=cookieService;
    }

    public OrderController() {
    }

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/list")
    public String getOrderList(Model model,
                               HttpServletRequest httpServletRequest,
                               UserDao userDao){
     //   HttpSession session = httpServletRequest.getSession();
   //     Integer userId = (Integer)session.getAttribute("user_id");
 //       model.addAttribute("userRole",userDao.getById(userId).getUserRole().name());
        model.addAttribute("orderList", orderService.sortOrders(0));
        return "admin/order";
    }

    @GetMapping("/order-item/{id}")
    public String orderItemList(
            Model model,
            @PathVariable("id") int id
    ) {
        model.addAttribute("orderItemList",orderService.getOrderItemList(id));
        return "admin/order-item";
    }
    @GetMapping("/edit-status/{id}")
    public String edit(Model model,
        @PathVariable("id") int id
    ){
        System.out.println("Hallo" + id);

        return "index";
    }

//@GetMapping("/order-item")
//public String getOrderItemList(Model model) {
//    model.addAttribute("orderItemList",orderService.getOrderItemList(1));
//    return "admin/order";
//}


    @GetMapping(value = "/pay")
    public String buy(Model model, HttpServletRequest request) {
        List<Integer> productIdFromCookie = cookieService.getProductIdFromCookie(request);
        if (orderService.addOrder(productIdFromCookie, request)) {
            cookieService.deleteCookie(request);
            model.addAttribute("massage", "Your order successfully completed." +
                    "You can see orders from your account");
        } else {
            model.addAttribute("massage", "Something wrong ");
        }
        return "basket/basket";
    }
}
