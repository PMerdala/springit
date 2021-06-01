package pl.pmerdala.springit.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.pmerdala.springit.domain.Link;
import pl.pmerdala.springit.map.MapLinkViewLinkData;
import pl.pmerdala.springit.repositories.LinkRepository;
import pl.pmerdala.springit.viewdata.CreateOrUpdateCommentData;
import pl.pmerdala.springit.viewdata.CreateOrUpdateLinkData;
import pl.pmerdala.springit.viewdata.ViewLinkData;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class LinkController {
    private final LinkRepository linkRepository;
    private final MapLinkViewLinkData mapLinkViewLinkData;

    public LinkController(LinkRepository linkRepository, MapLinkViewLinkData mapLinkViewLinkData) {
        this.linkRepository = linkRepository;
        this.mapLinkViewLinkData = mapLinkViewLinkData;
    }


    @GetMapping("/")
    String links(Model model) {
        List<ViewLinkData> viewLinkDataList = mapLinkViewLinkData.viewLinkDataList(linkRepository.findAll());
        model.addAttribute("links", viewLinkDataList);
        return "link/list";
    }

    @GetMapping("/link/{id}")
    String link(@PathVariable Long id, Model model) {
        Optional<Link> link = linkRepository.findById(id);
        if (link.isEmpty()) {
            return "redirect:/";
        }
        ViewLinkData viewLinkData = mapLinkViewLinkData.viewLinkData(link.get());
        CreateOrUpdateCommentData commentData = new CreateOrUpdateCommentData();
        model.addAttribute("commentData", commentData);
        model.addAttribute("commentAddAction", String.format("/link/%d/comments", link.get().getId()));
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
        Link link = mapLinkViewLinkData.link(data);
        Link savedLink = linkRepository.save(link);
        redirectAttributes
                .addAttribute("id", savedLink.getId())
                .addFlashAttribute("success", true);
        //noinspection SpringMVCViewInspection
        return "redirect:/link/{id}";
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
        Optional<Link> optionalLink = linkRepository.findById(id);
        if (optionalLink.isPresent()) {
            Link link = optionalLink.get();
            mapLinkViewLinkData.updateLink(data, link);
            Link savedLink = linkRepository.save(link);
            redirectAttributes
                    .addAttribute("id", savedLink.getId())
                    .addFlashAttribute("success", true);
            //noinspection SpringMVCViewInspection
            return "redirect:/link/{id}";
        }
        return "redirect:/";
    }

    @GetMapping("/link/submit")
    String newLinkForm(Model model) {
        model.addAttribute("link", new CreateOrUpdateLinkData());
        model.addAttribute("action", "/link/submit");
        return "link/submit";
    }

    @GetMapping("/link/submit/{id}")
    String updateLinkForm(@PathVariable Long id, Model model) {
        Optional<Link> link = linkRepository.findById(id);
        if (link.isEmpty()) {
            return "redirect:/";
        }
        CreateOrUpdateLinkData data = link.map(mapLinkViewLinkData::createOrUpdateLinkData).orElseThrow();
        model.addAttribute("link", data);
        model.addAttribute("action", String.format("/link/submit/%d", link.get().getId()));
        return "link/submit";
    }
}
