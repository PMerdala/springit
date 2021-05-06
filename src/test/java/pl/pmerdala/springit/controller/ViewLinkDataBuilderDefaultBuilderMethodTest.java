package pl.pmerdala.springit.controller;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.ocpsoft.prettytime.PrettyTime;
import pl.pmerdala.springit.domain.Comment;
import pl.pmerdala.springit.service.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Unit")
class ViewLinkDataBuilderDefaultBuilderMethodTest {

    ViewLinkData.ViewLinkDataBuilder sut=ViewLinkData.builder();

    @Test
    void buildInvokeDomainCommentsThrowException() {
        List<Comment> comments = Lists.list(new Comment(),new Comment());
        assertThrows(RuntimeException.class,() -> sut.domainComments(comments) );
    }

    @Test
    void buildEmpty() {
        ViewLinkData view = sut.build();
        assertNull(view.getId());
        assertNull(view.getComments());
        assertNull(view.getCreatedBy());
        assertNull(view.getCreationDate());
        assertNull(view.getDomainName());
        assertNull(view.getFormatCreationDate());
        assertNull(view.getNumberOfComments());
        assertNull(view.getTitle());
        assertNull(view.getUrl());
    }
}