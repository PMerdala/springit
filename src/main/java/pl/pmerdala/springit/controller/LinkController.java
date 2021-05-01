package pl.pmerdala.springit.controller;

import org.springframework.web.bind.annotation.*;
import pl.pmerdala.springit.domain.Link;
import pl.pmerdala.springit.repositories.LinkRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/links")
public class LinkController {
    private final LinkRepository linkRepository;

    public LinkController(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @GetMapping(path = {"/", ""})
    List<Link> links() {
        return linkRepository.findAll();
    }

    @PostMapping(path = {"/", ""})
    Link create(@ModelAttribute CreateOrUpdateLinkData linkData) {
        Link link = new Link(linkData.getTitle(), linkData.getUrl());
        return linkRepository.save(link);
    }

    @GetMapping("/{linkId}")
    Optional<Link> get(@PathVariable Long linkId) {
        return linkRepository.findById(linkId);
    }

    @PutMapping("/{linkId}")
    Link update(@PathVariable Long linkId, @ModelAttribute CreateOrUpdateLinkData linkData) {
        Optional<Link> link = linkRepository.findById(linkId);
        return link.map(linkToSave -> {
            linkToSave.setTitle(linkData.getTitle());
            linkToSave.setUrl(linkData.getUrl());
            return linkToSave;
        }).orElse(null);
    }

    @DeleteMapping("/{linkId}")
    void delete(@PathVariable Long linkId) {
        linkRepository.deleteById(linkId);
    }
}
