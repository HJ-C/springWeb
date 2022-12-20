package com.example.springweb.entitiy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@AllArgsConstructor //모든 필드 값을 파라미터로 받는 생성자 생성!
@NoArgsConstructor // 디폴트 생성자를 추가!
@Entity // DB가 해당 객체를 인식 가능! (해당 클래스로 테이블을 만든다)
@ToString
@Getter // 모든 Getter
public class Article {

    @Id // 대표값
    // @GeneratedValue // 1,2,3,4 .. 자동 생성 어노테이션
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 ID를 자동생성(ID값이 중복되지 않게하려고)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

}
