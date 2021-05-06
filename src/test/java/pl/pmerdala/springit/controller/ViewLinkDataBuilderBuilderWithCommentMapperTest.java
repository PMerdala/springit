package pl.pmerdala.springit.controller;

import org.junit.jupiter.api.Test;
import org.ocpsoft.prettytime.PrettyTime;
import pl.pmerdala.springit.service.DateTimeFormatter;

class ViewLinkDataBuilderBuilderWithCommentMapperTest {

    PrettyTime prettyTime = new PrettyTime();
    DateTimeFormatter dateTimeFormatter = new DateTimeFormatter(prettyTime);
    MapCommentViewCommentData mapComment = new MapCommentViewCommentData(dateTimeFormatter);
    ViewLinkData.ViewLinkDataBuilder sut = ViewLinkData.builder(mapComment);

    @Test
    void buildWithoutDomainComments() {

    }
}