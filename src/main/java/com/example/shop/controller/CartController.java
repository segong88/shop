package com.example.shop.controller;

import com.example.shop.dto.CatrItemDTO;
import com.example.shop.service.CartService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart")
    public ResponseEntity order(@Valid CatrItemDTO catrItemDTO,
                                BindingResult bindingResult, Principal principal) {

        log.info(catrItemDTO);
        log.info(catrItemDTO);
        log.info(catrItemDTO);

        if(bindingResult.hasErrors()) {
            log.info("장바구니 유효성 검사 에러");
            log.info(bindingResult.getAllErrors());

            List<FieldError> fieldErrorList = bindingResult.getFieldErrors();

            StringBuilder stringBuilder = new StringBuilder();

            for (FieldError error : fieldErrorList) {
                //StringBuilder객체에 에러의 메시지를 담는다.
                stringBuilder.append(error.getDefaultMessage());
            }

            //입력된 에러를 다시 보여주기위해 반환값으로 에러내용을 반환해줍니다.
            return new ResponseEntity<String>
                    (stringBuilder.toString(), HttpStatus.BAD_REQUEST);
        }

        //로그아웃이되어서 principal가 null
        if(principal == null) {
            return new ResponseEntity
                    (HttpStatus.UNAUTHORIZED);
        }

        String email = principal.getName();
        Long cartItemId = null;

        try{
            cartItemId =
            cartService.addCart(catrItemDTO, email);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<String>
                    (e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>
                (cartItemId, HttpStatus.OK);
    }


}
