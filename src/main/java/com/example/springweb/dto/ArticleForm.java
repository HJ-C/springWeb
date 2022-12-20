package com.example.springweb.dto;

import com.example.springweb.entitiy.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor //생성자 Lombok
@ToString //toStirng 롬복
public class ArticleForm {

    private Long id; // id 필드 추가!
    private String title;
    private String content;

    //toEntity 메소드
    public Article toEntity(){
        return new Article(id, title, content);
    }
}
