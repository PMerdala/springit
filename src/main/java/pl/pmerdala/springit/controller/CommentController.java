package pl.pmerdala.springit.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.pmerdala.springit.domain.Comment;
import pl.pmerdala.springit.repositories.CommentRepository;
import pl.pmerdala.springit.repositories.LinkRepository;
import pl.pmerdala.springit.viewdata.CreateOrUpdateCommentData;

import javax.validation.Valid;
import java.util.concurrent.atomic.AtomicReference;

@Controller
public class CommentController {

    private final LinkRepository linkRepository;
    private final CommentRepository commentRepository;

    public CommentController(LinkRepository linkRepository, CommentRepository commentRepository) {
        this.linkRepository = linkRepository;
        this.commentRepository = commentRepository;
    }


    @Secured({"ROLE_USER"})
    @PostMapping("/link/{linkId}/comments")
    String addComment(@PathVariable Long linkId,
                      @Valid @ModelAttribute("commentData") CreateOrUpdateCommentData commentData,
                      BindingResult bindingResult,
                      Model model,
                      RedirectAttributes redirectAttributes) {
        AtomicReference<String> action = new AtomicReference<>("redirect:/");
        linkRepository.findById(linkId).ifPresent(link -> {
            if (!bindingResult.hasErrors()) {
                Comment comment = new Comment(commentData.getBody(), link);
                Comment savedComment = commentRepository.save(comment);
                link.addComment(savedComment);
                redirectAttributes.addFlashAttribute("success_comment", true);
            }
            redirectAttributes.addAttribute("id", link.getId());
            action.set("redirect:/link/{id}");
        });

        return action.get();
    }
}
