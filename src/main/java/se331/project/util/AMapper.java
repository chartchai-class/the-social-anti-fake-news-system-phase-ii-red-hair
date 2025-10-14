package se331.project.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import se331.project.dto.CommentDto;
import se331.project.dto.NewsDto;
import se331.project.dto.UserProfileAuthDto;
import se331.project.dto.UserProfileDto;
import se331.project.entity.Comment;
import se331.project.entity.News;
import se331.project.entity.UserProfile;

import java.util.List;

@Mapper
public interface AMapper {
    AMapper INSTANCE = Mappers.getMapper(AMapper.class);


    // mapper for news part
    NewsDto getNewsDto(News news);
    List<NewsDto> getNewsDto(List<News> news);


    // a mapper for comment
    CommentDto getCommentDto(Comment comment);
    List<CommentDto> getCommentDto(List<Comment> comments);


    //  mapper for user
    UserProfileDto getUserDto(UserProfile userProfile);
    List<UserProfileDto> getUserDto(List<UserProfile> userProfiles);

    @Mapping(target = "roles", source = "user.roles")
    UserProfileAuthDto getUserProfileAuthDto(UserProfile userProfile);
}