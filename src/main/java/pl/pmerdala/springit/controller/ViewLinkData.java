package pl.pmerdala.springit.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pmerdala.springit.domain.Comment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(buildMethodName = "buildWithoutDomainComments")
public class ViewLinkData {
    private Long id;
    private String title;
    private String url;
    private String domainName;
    private String formatCreationDate;
    private LocalDateTime creationDate;
    private String createdBy;
    private Integer numberOfComments;
    private List<ViewCommentData> comments;

    public static ViewLinkDataBuilder builder(AbstractMap<Comment, ViewCommentData> mapCommentViewCommentData) {
        return new ViewLinkDataBuilder(mapCommentViewCommentData);
    }

    public static ViewLinkDataBuilder builder() {
        return new ViewLinkDataBuilder();
    }

    public static class ViewLinkDataBuilder {

        private List<Comment> domainComments;
        private final AbstractMap<Comment, ViewCommentData> mapCommentViewCommentData;

        private ViewLinkDataBuilder(AbstractMap<Comment, ViewCommentData> mapCommentViewCommentData) {
            this.mapCommentViewCommentData = mapCommentViewCommentData;
        }

        private ViewLinkDataBuilder() {
            this(null);
        }

        public ViewLinkDataBuilder domainComments(List<Comment> comments) {
            if (mapCommentViewCommentData == null && comments != null && comments.size() != 0) {
                throw new RuntimeException("używając funkcji comments musisz użyć ViewLinkData.builder z parametrem AbstractMap<Comment,ViewCommentData>");
            }
            this.domainComments = comments;
            return this;
        }

        public ViewLinkData build() {
            ViewLinkData viewLinkData = this.buildWithoutDomainComments();
            if (domainComments != null && domainComments.size() != 0) {
                if (mapCommentViewCommentData == null) {
                    throw new RuntimeException("Używając funkcji build musisz użyć ViewLinkData.builder z parametrem AbstractMap<Comment,ViewCommentData>");
                }
                Stream<ViewCommentData> streamViewCommentData = mapCommentViewCommentData.map(domainComments.stream(), viewLinkData);
                List<ViewCommentData> allViewCommentData;
                if (comments != null && comments.size() != 0) {
                    allViewCommentData = Stream.concat(comments.stream(), streamViewCommentData).collect(Collectors.toUnmodifiableList());
                } else {
                    allViewCommentData = streamViewCommentData.collect(Collectors.toUnmodifiableList());
                }
                viewLinkData.setComments(allViewCommentData);
            }
            return viewLinkData;
        }

    }
}
