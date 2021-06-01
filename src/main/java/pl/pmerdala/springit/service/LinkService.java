package pl.pmerdala.springit.service;

import org.springframework.stereotype.Service;
import pl.pmerdala.springit.domain.Comment;
import pl.pmerdala.springit.domain.Link;
import pl.pmerdala.springit.map.MapCommentViewCommentData;
import pl.pmerdala.springit.map.MapLinkViewLinkData;
import pl.pmerdala.springit.repositories.CommentRepository;
import pl.pmerdala.springit.repositories.LinkRepository;
import pl.pmerdala.springit.viewdata.CreateOrUpdateCommentData;
import pl.pmerdala.springit.viewdata.CreateOrUpdateLinkData;
import pl.pmerdala.springit.viewdata.ViewLinkData;

import java.util.List;
import java.util.Optional;

@Service
public class LinkService {
    private final LinkRepository linkRepository;
    private final MapLinkViewLinkData mapLinkViewLinkData;
    private final CommentRepository commentRepository;
    private final MapCommentViewCommentData mapCommentViewCommentData;

    public LinkService(LinkRepository linkRepository,
                       MapLinkViewLinkData mapLinkViewLinkData,
                       CommentRepository commentRepository, MapCommentViewCommentData mapCommentViewCommentData) {
        this.linkRepository = linkRepository;
        this.mapLinkViewLinkData = mapLinkViewLinkData;
        this.commentRepository = commentRepository;
        this.mapCommentViewCommentData = mapCommentViewCommentData;
    }

    public List<ViewLinkData> viewLinkDataList() {
        return mapLinkViewLinkData.viewLinkDataList(linkRepository.findAll());
    }

    public Optional<ViewLinkData> viewLinkDataByLinkId(Long linkId) {
        return linkRepository.findById(linkId).map(mapLinkViewLinkData::viewLinkData);
    }

    public Optional<Long> createLinkAndReturnId(CreateOrUpdateLinkData data) {
        Link link = mapLinkViewLinkData.link(data);
        Link savedLink = linkRepository.save(link);
        return Optional.of(savedLink.getId());
    }

    public Optional<Long> updateLinkAndReturnId(Long linkId, CreateOrUpdateLinkData data) {
        return linkRepository.findById(linkId).map(link -> {
            mapLinkViewLinkData.updateLink(data, link);
            Link savedLink = linkRepository.save(link);
            return savedLink.getId();
        });
    }

    public Optional<CreateOrUpdateLinkData> linkDataForUpdating(Long linkId) {
        return linkRepository.findById(linkId).map(mapLinkViewLinkData::createOrUpdateLinkData);
    }

    public Optional<CreateOrUpdateLinkData> linkDataForCreating() {
        return Optional.of(new CreateOrUpdateLinkData());
    }

    public Optional<CreateOrUpdateCommentData> commentDataForCreating() {
        return Optional.of(new CreateOrUpdateCommentData());
    }

    public Optional<Long> addCommentAndReturnLinkId(Long linkId, CreateOrUpdateCommentData commentData) {
        return linkRepository.findById(linkId).map(link -> {
            Comment comment = mapCommentViewCommentData.comment(commentData, link);
            Comment savedComment = commentRepository.save(comment);
            link.addComment(savedComment);
            return link.getId();
        });
    }
}