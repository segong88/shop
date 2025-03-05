package com.example.shop.controller;

import com.example.shop.dto.OrderDTO;
import com.example.shop.dto.OrderHistDTO;
import com.example.shop.dto.RequestPageDTO;
import com.example.shop.dto.ResponesPageDTO;
import com.example.shop.exception.OutOfStockException;
import com.example.shop.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Log4j2
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity order(@RequestBody @Valid OrderDTO orderDTO,
                                BindingResult bindingResult, Principal principal){

        log.info("주문하기 post 진입");
        log.info("들어온 값 체크 : " + orderDTO);

        if(bindingResult.hasErrors()) {
            log.info("유효성 검사 count가 없다면");
            StringBuilder sb = new StringBuilder();
            //"홍길동" + "홍길동" append를 통해서 추가적으로 저장가능

            log.info(bindingResult.getAllErrors());

            List<FieldError> fieldErrorList =
                bindingResult.getFieldErrors();
            for(FieldError fieldError : fieldErrorList) {
                sb.append(fieldError.getDefaultMessage());
            }

            log.info(sb.toString());

            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        if(principal == null) {
            log.info("로그인 안되어있음");
            return new ResponseEntity<String>("", HttpStatus.UNAUTHORIZED);
        }
        //주문을 하려면 부모인 주문entity 필요, 주문 entity는 회원과 1:1, 이메일로 주문 찾아오기
        String email = principal.getName();

        Long orderId = null;
        //수량부족에 대한 예외처리
        try{
            orderId = orderService.order(orderDTO, email);

            //주문 아이템의 부모인 아이템 > 입력받은 itemid로 해결
        } catch (OutOfStockException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }

    @GetMapping({"/orders" ,"/orders/{page}"})
    public String orderHist(@PathVariable("page") Optional<Integer> page,
                            Principal principal, Model model, RequestPageDTO requestPageDTO){
        //페이지를 url로 받은 값이 없다면 1, 있다면 받는 값 할당
        requestPageDTO.setPage(page.isPresent() ? page.get() : 1);

        log.info("현재 페이지는? : " + ( page.isPresent() ? page.get() : 1));
        //파라미터가 Long이라면
        //if(page != null) {
        //    requestPageDTO.setPage(page.intValue());
        //}

        //만약에 로그인이 안되었다면 리다이렉트를 하던가 페이지 자체가 로그인이 되어야한다.
        if(principal == null) {
            return "redirect:/";
        }
        ResponesPageDTO<OrderHistDTO> responesPageDTO =
        orderService.getOrderList(principal.getName(), requestPageDTO);

        responesPageDTO.getDtoList().forEach(orderHistDTO -> log.info(orderHistDTO));

        model.addAttribute("responesPageDTO", responesPageDTO);

        return "order/orderHist";
    }

}
