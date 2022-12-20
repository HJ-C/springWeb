package com.example.springweb.repository;

import com.example.springweb.entitiy.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ArticleRepository extends CrudRepository<Article,Long> {

    //타입 ArrayList로 맞춤
    @Override
    ArrayList<Article> findAll();
}
