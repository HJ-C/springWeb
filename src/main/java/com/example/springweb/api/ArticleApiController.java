package com.example.springweb.api;

import com.example.springweb.dto.ArticleForm;
import com.example.springweb.entitiy.Article;
import com.example.springweb.repository.ArticleRepository;
import com.example.springweb.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j //log info
@RestController // RestAPI용 컨트롤러! 데이터(JSON)을 반환
public class ArticleApiController {
    @Autowired //DI (외부에서 가져오다), 생성 객체를 가져와 연결
    private ArticleService articleService;


    // GET
    // 전체 조회
    @GetMapping("api/articles")
    public List<Article> index(){
        return articleService.index();
    }

    // 단일 조회
    @GetMapping("api/articles/{id}")
    public Article show(@PathVariable Long id){
        return articleService.show(id);
    }

    // POST
    @PostMapping("api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto){
        Article created = articleService.create(dto);
        return (created != null)
                ? ResponseEntity.status(HttpStatus.OK).body(created)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // PATCH(PUT)
    @PatchMapping("api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto){
        Article updated = articleService.update(id,dto);

        return (updated != null)
                ? ResponseEntity.status(HttpStatus.OK).body(updated)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // PATCH(PUT) Service 전
//    @PatchMapping("api/articles/{id}")
//    //ResponseEntitiy에 Article이 담겨서 JSON으로 반환
//    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto){
//        // 1. 수정용 엔티티 생성
//        Article article = dto.toEntity();
//        log.info("id : {} article : {}", id,article.toString());
//
//        // 2. 대상 엔티티 조회
//        Article target = articleRepository.findById(id).orElse(null);
//
//        // 3. 잘못된 요청 처리(대상이 없거나 id가 다른 경우)
//        if(target == null || id != article.getId()){
//            // 잘못된 요청이다 404 처리
//            log.info("잘못된 요청! : {} article : {}", id,article.toString());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); //ResponseEntity를 통해서 데이터를 body로 실어서 보냄.
//        }
//
//        // 4. 업데이트 및 정상 응답(200)
//        target.patch(article); //patch는 값이 있을때 데이터를 주는 로직
//        Article updated = articleRepository.save(target);
//        return ResponseEntity.status(HttpStatus.OK).body(updated);
//    }
//


    // DELETE
    @DeleteMapping("api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){
        Article deleted = articleService.delete(id);

        return (deleted != null)
                ?  ResponseEntity.status(HttpStatus.OK).body(deleted)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


//    // DELETE service 구현 전
//    @DeleteMapping("api/articles/{id}")
//    public ResponseEntity<Article> delete(@PathVariable Long id){
//
//        // 대상 찾기
//        Article target = articleRepository.findById(id).orElse(null);
//
//        // 잘못된 요청 처리
//        if( target == null){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//        // 대상 삭제
//        articleRepository.delete(target);
//        // 데이터반환
//        return ResponseEntity.status(HttpStatus.OK).body(null);
//    }

    // 트랜잭션 -> 실패 -> 롤백
    @PostMapping("api/transaction-test")
    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> dtos){
        List<Article> createdList = articleService.createArticles(dtos);
        return (createdList != null)
                ? ResponseEntity.status(HttpStatus.OK).body(createdList)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }



}
