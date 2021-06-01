package pl.pmerdala.springit.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.pmerdala.springit.service.LinkService;
import pl.pmerdala.springit.viewdata.CreateOrUpdateCommentData;
import pl.pmerdala.springit.viewdata.CreateOrUpdateLinkData;

import javax.validation.Valid;

@Controller
@Slf4j
public class LinkController {

    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping("/")
    String links(Model model) {
        model.addAttribute("links", linkService.viewLinkDataList());
        return "link/list";
    }

    @GetMapping("/link/{id}")
    String link(@PathVariable Long id, Model model) {
        return linkService.viewLinkDataByLinkId(id).map(viewLinkData -> {
            CreateOrUpdateCommentData commentData = new CreateOrUpdateCommentData();
            model.addAttribute("commentData", commentData);
            model.addAttribute("commentAddAction", String.format("/link/%d/comments", viewLinkData.getId()));
            model.addAttribute("link", viewLinkData);
            model.addAttribute("success",
                    model.containsAttribute("success")
                            || model.containsAttribute("success_comment"));
            if (model.containsAttribute("success")) {
                model.addAttribute("success_message", "link create success!");
            }
            if (model.containsAttribute("success_comment")) {
                model.addAttribute("success_message", "comment create success!");
            }
            return "link/view";
        }).orElse("redirect:/");
    }

    @PostMapping("/link/submit")
    String addLink(@Valid @ModelAttribute("link") CreateOrUpdateLinkData data,
                   BindingResult bindingResult,
                   Model model,
                   RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", "/link/submit");
            return "link/submit";
        }
        return linkService.createLinkAndReturnId(data).map(linkId -> {
            redirectAttributes
                    .addAttribute("id", linkId)
                    .addFlashAttribute("success", true);
            return "redirect:/link/{id}";
        }).orElse("link/submit");
    }

    @PostMapping("/link/submit/{id}")
    String updateLink(@PathVariable Long id,
                      @Valid @ModelAttribute("link") CreateOrUpdateLinkData data,
                      BindingResult bindingResult,
                      Model model,
                      RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", String.format("/link/submit/%d", id));
            return "link/submit";
        }
        return linkService.updateLinkAndReturnId(id, data)
                .map(linkId -> {
                    redirectAttributes
                            .addAttribute("id", linkId)
                            .addFlashAttribute("success", true);
                    return "redirect:/link/{id}";
                }).orElse("redirect:/");
    }

    @GetMapping("/link/submit")
    String newLinkForm(Model model) {
        return linkService.linkDataForCreating().map(createOrUpdateLinkData -> {
            model.addAttribute("link", createOrUpdateLinkData);
            model.addAttribute("action", "/link/submit");
            return "link/submit";
        }).orElse("redirect:/");
    }

    @GetMapping("/link/submit/{id}")
    String updateLinkForm(@PathVariable Long id, Model model) {
        return linkService.linkDataForUpdating(id).map(createOrUpdateLinkData -> {
            model.addAttribute("link", createOrUpdateLinkData);
            model.addAttribute("action", String.format("/link/submit/%d", id));
            return "link/submit";
        }).orElse("redirect:/");
    }

    @Secured({"ROLE_USER"})
    @PostMapping("/link/{linkId}/comments")
    String addComment(@PathVariable Long linkId,
                      @Valid @ModelAttribute("commentData") CreateOrUpdateCommentData commentData,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addAttribute("id", linkId);
            return "redirect:/link/{id}";
        }
        return linkService.addCommentAndReturnLinkId(linkId, commentData)
                .map(afterSaveLinkId -> {
                    redirectAttributes.addFlashAttribute("success_comment", true);
                    redirectAttributes.addAttribute("id", afterSaveLinkId);
                    return "redirect:/link/{id}";
                }).orElseGet(() -> {
                    redirectAttributes.addAttribute("id", linkId);
                    return "redirect:/link/{id}";
                });
    }

}
