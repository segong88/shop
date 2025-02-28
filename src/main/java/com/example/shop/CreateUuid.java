package com.example.shop;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class CreateUuid {

    // public으로 메서드를 정의하여 다른 클래스에서 호출할 수 있도록 설정
    public String createShortUuid() {
        // UUID 생성
        String uuidString = UUID.randomUUID().toString();

        // UUID 문자열을 바이트 배열로 변환
        byte[] uuidStringBytes = uuidString.getBytes(StandardCharsets.UTF_8);
        byte[] hashBytes = null;

        try {
            // SHA-256 해시 알고리즘을 사용하여 해시 값 생성
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            hashBytes = messageDigest.digest(uuidStringBytes);
        } catch (NoSuchAlgorithmException e) {
            // 예외 처리: SHA-256 알고리즘을 찾을 수 없으면 오류 메시지 출력
            System.err.println("SHA-256 알고리즘을 찾을 수 없습니다.");
            return null; // 예외 발생 시 null 반환
        }

        // 해시 값의 앞 4바이트를 16진수 문자열로 변환
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < 4; j++) {
            sb.append(String.format("%02x", hashBytes[j]));
        }

        // 계산된 해시 값의 앞 4바이트 16진수 문자열을 반환
        return sb.toString();
    }

    /*todo 주문번호 생성하기*/
    public static void main(String[] args) {
        CreateUuid uuid = new CreateUuid();

        System.out.println("주문번호 : "+uuid.createShortUuid());
        System.out.println("주문번호 : "+uuid.createShortUuid());
        System.out.println("주문번호 : "+uuid.createShortUuid());
        System.out.println("주문번호 : "+uuid.createShortUuid());
    }
}