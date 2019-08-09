package techcourse.myblog.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import techcourse.myblog.application.CommentService;
import techcourse.myblog.application.dto.CommentRequest;
import techcourse.myblog.application.dto.CommentResponse;
import techcourse.myblog.application.dto.UserResponse;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/articles/{articleId}")
public class CommentController {

    private static final Logger log = LoggerFactory.getLogger(CommentController.class);

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comments")
    @ResponseBody
    public CommentResponse saveComment(@PathVariable("articleId") Long articleId,
                                       @RequestBody CommentRequest commentRequest, HttpSession httpSession) {
        log.info(commentRequest.getContents());

        UserResponse user = (UserResponse) httpSession.getAttribute("user");
        return commentService.save(commentRequest, articleId, user.getId());
    }

    @DeleteMapping("/comments/{commentId}")
    @ResponseBody
    public void deleteComment(@PathVariable("commentId") Long commentId, HttpSession httpSession) {
        UserResponse userResponse = (UserResponse) httpSession.getAttribute("user");
        commentService.deleteComment(commentId, userResponse);
    }

    @PutMapping("/comments/{commentId}")
    @ResponseBody
    public CommentResponse updateComment(@PathVariable("commentId") Long commentId,
                                         @RequestBody CommentRequest commentRequest, HttpSession httpSession) {
        UserResponse userResponse = (UserResponse) httpSession.getAttribute("user");
        return commentService.updateComment(commentId, userResponse, commentRequest);
    }
}
