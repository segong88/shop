package com.example.shop.dto;

import com.example.shop.entity.Item;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ImgDTO {

    private Long id;

    private String imgName; //uuid가 포함된 파일이름

    private String oriImgName;  //짱구라는 이미지이름
    private String imgUrl;

    private String repimgYn;    //대표이미지 여부 Y일 경우 대표 이미지

    //참조대상
    private ItemDTO itemDTO;

}
