package com.example.springweb.controller;

import com.example.springweb.dto.ArticleForm;
import com.example.springweb.dto.CommentDto;
import com.example.springweb.entitiy.Article;
import com.example.springweb.repository.ArticleRepository;
import com.example.springweb.service.CommentService;
import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j //로깅을 위한 어노테이션
public class ArticleController {

    @Autowired //스프링부트가 미리 생성해놓은 객체를 가져다가 자동 연결!, 생성자를 따로 안만들어도 됨
    private ArticleRepository articleRepository;

    @Autowired
    private CommentService commentService; //comment 서비스

    //게시글 작성 url맵핑
    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){

        log.info(form.toString());
//       System.out.println(form.toString()); -> 로깅기능으로 대체!


        //1. Dto를 변환! Entity로
        Article article = form.toEntity();
        log.info(article.toString());
//        System.out.println();


        //2. Repository에게 Entity를 DB안에 저장하게 함!
        Article saved = articleRepository.save(article);
        log.info(saved.toString());
//        System.out.println(saved.toString());
        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){
        log.info("id=" + id);

        //1. id로 데이터를 가져옴
        Article articleEntitiy = articleRepository.findById(id).orElse(null);
        List<CommentDto> commentDtos = commentService.comments(id);

        //2. 가져온 데이터를 모델에 등록
        model.addAttribute("article", articleEntitiy);
        model.addAttribute("commentDtos", commentDtos);

        //3. 보여줄 페이지를 설정
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model){

        //1. 모든 Article 가져온다!
        List<Article> articleEntityList = articleRepository.findAll();

        //2. 가져온 Article 묶음을 뷰로 전달!
        model.addAttribute("articleList",articleEntityList);

        //3. 뷰 페이지를 설정!
        return "articles/index";
    }

//    수정
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){

        // 수정할 데이터를 가져오기!(DB에서 가져와야함, repository에서)
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 모델에 데이터 등록
        model.addAttribute("article",articleEntity);

        // 뷰 페이지 설정
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form){

        //1. DTO를 엔티티로 변환한다.
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());

        //2. 엔티티를 DB로 저장한다.
        //2-1. DB에서 기존 데이터를 가져온다.
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

        //2-2 기존 데이터에 값을 갱신한다.
        if(target != null){
            articleRepository.save(articleEntity); // 엔티티가 DB로 갱신
        }

        //3. 수정 결과 페이지로 리다이렉트 한다.
        return "redirect:/articles/" + articleEntity.getId();
    }

    //삭제
    @GetMapping("/articles/{id}/delete") //나중에 DeleteMapping 사용
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        log.info("삭제요청");

        //1. 삭제 대상을 가져온다
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());

        //2. 그 대상을 삭제한다
        if(target != null){
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg","삭제 됐습니다.");
        }

        //3. 결과 페이지로 리다이렉트.
        return "redirect:/articles";
    }
}
