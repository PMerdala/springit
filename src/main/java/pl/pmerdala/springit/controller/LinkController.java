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
import pl.pmerdala.springit.viewdata.ViewLinkData;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class LinkController {

    private final LinkService linkService;
    private final static String REDIRECT = "redirect:";

    private final static String LINK_FORM_ATTRIBUTE = "link";
    private final static String LINK_VIEW_ATTRIBUTE = "link";
    private final static String LINKS_VIEW_ATTRIBUTE = "links";
    private final static String LINK_ID_ATTRIBUTE = "id";

    private final static String LINK_LIST_URL = "/";
    private final static String ONE_LINK_URL = "/link/{id}";
    private final static String LINK_SUBMIT_URL = "/link/submit";
    private final static String ONE_LINK_SUBMIT_URL = "/link/submit/{id}";
    private final static String ONE_LINK_SUBMIT_URL_FOR_FORMATTER = "/link/submit/%d";
    private final static String ONE_LINK_SUBMIT_COMMENTS_URL = "/link/{linkId}/comments";
    private final static String ONE_LINK_SUBMIT_COMMENTS_URL_FOR_FORMATTER = "/link/%d/comments";

    private final static String LINK_LIST_TEMPLATE = "link/list";
    private final static String LINK_VIEW_TEMPLATE = "link/view";
    private final static String LINK_SUBMIT_TEMPLATE = "link/submit";

    private final static String COMMENT_FORM_ATTRIBUTE = "commentData";


    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping(LINK_LIST_URL)
    String links(Model model) {
        addLinksAttribute(model, linkService.viewLinkDataList());
        return LINK_LIST_TEMPLATE;
    }

    @GetMapping(ONE_LINK_URL)
    String link(@PathVariable Long id, Model model) {
        return linkService.viewLinkDataByLinkId(id).map(viewLinkData -> {
            addCommentAttribute(model, linkService.commentDataForCreating().orElseThrow());
            addCommentActionAttribute(model, viewLinkData.getId());
            addLinkAttribute(model, viewLinkData);
            addSuccessAndMessageModelAttribute(model);
            return LINK_VIEW_TEMPLATE;
        }).orElse(REDIRECT + LINK_LIST_URL);
    }

    @PostMapping(LINK_SUBMIT_URL)
    String addLink(@Valid @ModelAttribute(LINK_FORM_ATTRIBUTE) CreateOrUpdateLinkData data,
                   BindingResult bindingResult,
                   Model model,
                   RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            addSubmitActionAttribute(model, Optional.empty());
            return LINK_SUBMIT_TEMPLATE;
        }
        return linkService.createLinkAndReturnId(data).map(linkId -> {
            addRedirectLinkIdAndSuccessAttribute(redirectAttributes, linkId);
            return REDIRECT + ONE_LINK_URL;
        }).orElse(LINK_SUBMIT_TEMPLATE);
    }

    @PostMapping(ONE_LINK_SUBMIT_URL)
    String updateLink(@PathVariable Long id,
                      @Valid @ModelAttribute(LINK_FORM_ATTRIBUTE) CreateOrUpdateLinkData data,
                      BindingResult bindingResult,
                      Model model,
                      RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            addSubmitActionAttribute(model, Optional.ofNullable(id));
            return LINK_SUBMIT_TEMPLATE;
        }
        return linkService.updateLinkAndReturnId(id, data)
                .map(linkId -> {
                    addRedirectLinkIdAndSuccessAttribute(redirectAttributes, linkId);
                    return REDIRECT + ONE_LINK_URL;
                }).orElse(REDIRECT + LINK_LIST_URL);
    }

    @GetMapping(LINK_SUBMIT_URL)
    String newLinkForm(Model model) {
        return linkService.linkDataForCreating().map(createOrUpdateLinkData -> {
            addCreateOrUpdateLinkDataAttribute(model, createOrUpdateLinkData);
            addSubmitActionAttribute(model, Optional.empty());
            return LINK_SUBMIT_TEMPLATE;
        }).orElse(REDIRECT + LINK_LIST_URL);
    }

    @GetMapping(ONE_LINK_SUBMIT_URL)
    String updateLinkForm(@PathVariable Long id, Model model) {
        return linkService.linkDataForUpdating(id).map(createOrUpdateLinkData -> {
            addCreateOrUpdateLinkDataAttribute(model, createOrUpdateLinkData);
            addSubmitActionAttribute(model, Optional.ofNullable(id));
            return LINK_SUBMIT_TEMPLATE;
        }).orElse(REDIRECT + LINK_LIST_URL);
    }

    @Secured({"ROLE_USER"})
    @PostMapping(ONE_LINK_SUBMIT_COMMENTS_URL)
    String addComment(@PathVariable Long linkId,
                      @Valid @ModelAttribute(COMMENT_FORM_ATTRIBUTE) CreateOrUpdateCommentData commentData,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            addRedirectLinkIdAttribute(redirectAttributes, linkId);
            return REDIRECT + ONE_LINK_URL;
        }
        return linkService.addCommentAndReturnLinkId(linkId, commentData)
                .map(afterSaveLinkId -> {
                    addRedirectLinkIdAndSuccessCommentAttribute(redirectAttributes, afterSaveLinkId);
                    return REDIRECT + ONE_LINK_URL;
                }).orElseGet(() -> {
                    addRedirectLinkIdAttribute(redirectAttributes, linkId);
                    return REDIRECT + ONE_LINK_URL;
                });
    }

    private void addRedirectLinkIdAttribute(RedirectAttributes redirectAttributes, Long linkId) {
        redirectAttributes.addAttribute(LINK_ID_ATTRIBUTE, linkId);
    }

    private void addCommentAttribute(Model model, CreateOrUpdateCommentData commentData) {
        model.addAttribute(COMMENT_FORM_ATTRIBUTE, commentData);
    }

    private void addLinkAttribute(Model model, ViewLinkData viewLinkData) {
        model.addAttribute(LINK_VIEW_ATTRIBUTE, viewLinkData);
    }

    private void addLinksAttribute(Model model, List<ViewLinkData> viewLinkDatas) {
        model.addAttribute(LINKS_VIEW_ATTRIBUTE, viewLinkDatas);
    }

    private void addCommentActionAttribute(Model model, Long linkId) {
        model.addAttribute("commentAddAction",
                String.format(ONE_LINK_SUBMIT_COMMENTS_URL_FOR_FORMATTER, linkId));
    }

    private void addSubmitActionAttribute(Model model, Optional<Long> linkId) {
        model.addAttribute("action",
                linkId.map(id -> String.format(ONE_LINK_SUBMIT_URL_FOR_FORMATTER, id))
                        .orElse(LINK_SUBMIT_URL));
    }

    private void addSuccessAndMessageModelAttribute(Model model) {
        model.addAttribute("success",
                model.containsAttribute("success")
                        || model.containsAttribute("success_comment"));
        if (model.containsAttribute("success")) {
            model.addAttribute("success_message", "link create success!");
        }
        if (model.containsAttribute("success_comment")) {
            model.addAttribute("success_message", "comment create success!");
        }
    }

    private void addRedirectLinkIdAndSuccessAttribute(RedirectAttributes redirectAttributes, Long linkId) {
        addRedirectLinkIdAttribute(redirectAttributes, linkId);
        redirectAttributes.addFlashAttribute("success", true);
    }

    private void addRedirectLinkIdAndSuccessCommentAttribute(RedirectAttributes redirectAttributes, Long linkId) {
        addRedirectLinkIdAttribute(redirectAttributes, linkId);
        redirectAttributes.addFlashAttribute("success_comment", true);
    }

    private void addCreateOrUpdateLinkDataAttribute(Model model, CreateOrUpdateLinkData createOrUpdateLinkData) {
        model.addAttribute(LINK_FORM_ATTRIBUTE, createOrUpdateLinkData);
    }

}
