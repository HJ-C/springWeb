package com.example.springweb.service;

import com.example.springweb.dto.CommentDto;
import com.example.springweb.entitiy.Article;
import com.example.springweb.entitiy.Comment;
import com.example.springweb.repository.ArticleRepository;
import com.example.springweb.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired // Repository와 협업하기 위해 연결
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository;

    //댓글 서비스
    public List<CommentDto> comments(Long articleId) {
//        // 조회 : 댓글 목록
//        List<Comment> comments = commentRepository.findByArticleId(articleId);
//        // 변환 : 엔티티 -> DTO
//        List<CommentDto> dtos = new ArrayList<CommentDto>();

//        //for문 예시
//        for( int i=0; i<comments.size(); i++){
//            Comment c = comments.get(i);
//            CommentDto dto = CommentDto.createCommentDto(c);
//            dtos.add(dto);
//        }


        // for문 사용시 return dtos 하면 된다.
        // 반환
        return commentRepository.findByArticleId(articleId)
                .stream()
                .map(comment -> CommentDto.createCommentDto(comment))
                .collect(Collectors.toList()); //리스트로 변환
    }

    // 댓글 생성
    @Transactional
    // CommentDto는 json을 받기위해서 쓰임
    public CommentDto create(Long articleId, CommentDto dto) {
        // 게시글 조회 및 예외 발생
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! 대상 게시글이 없습니."));
        // 댓글 엔티티 생성
        Comment comment = Comment.createComment(dto, article);
        // 댓글 엔티티를 DB로 저장
        Comment created = commentRepository.save(comment);
        // DTO로 변경하여 반환
        return CommentDto.createCommentDto(created);
    }

    //댓글 수정
    @Transactional
    public CommentDto update(Long id, CommentDto dto) {
        // 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패! 대상 댓글이 없습니다."));
        // 댓글 수정
        target.patch(dto);
        // DB로 갱신
        Comment updated = commentRepository.save(target);
        // 댓글 엔티티를 DTO로 변환 및 반환
        return CommentDto.createCommentDto(updated);
    }

    //댓글 삭제
    @Transactional
    public CommentDto delete(Long id) {
        // 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! 대상이 없습니다"));

        // 댓글 DB삭제
        commentRepository.delete(target);

        // 삭제 댓글을 DTO로 변환
        return CommentDto.createCommentDto(target);
    }
}
