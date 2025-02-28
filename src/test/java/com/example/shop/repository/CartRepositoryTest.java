package com.example.shop.repository;

import com.example.shop.entity.Cart;
import com.example.shop.entity.Members;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class CartRepositoryTest {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    MembersRepository membersRepository;

    @Test
    public void insertTest(){

        //부모인 회원테이블에서 특정회원을 가져와서 set해준다
        //그걸 통해서 부모를 가지고 있게 된다.
//        Members members =
//                membersRepository.findByEmail("1@1.1");

        Members members =
                membersRepository.findById(1L).get();

        //참조값을 넣으면 참조하는거고 없으면 null 들어간다.

        Cart cart = new Cart();
        cart.setMembers(members);
        cartRepository.save(cart);
    }

    @Test
    public void findByMembersEmailTest(){

        //부모인 회원테이블에서 특정회원을 가져와서 set해준다
        //그걸 통해서 부모를 가지고 있게 된다.

        Cart cart =
        cartRepository.findByMembersEmail("1@1.1");

        if(cart == null) {
            log.info("장바구니 만들 수 있음");
        } else {
            log.info("장바구니 못 만들어");
        }

    }

    @Test
    @Transactional
    public void findByIdTest(){

        Cart cart =
        cartRepository.findById(1L).get();

        log.info(cart.getMembers());

    }


}