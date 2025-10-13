package se331.project.util;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import se331.project.dto.CommentDto;
import se331.project.dto.NewsDto;
import se331.project.dto.UserDto;
import se331.project.entity.Comment;
import se331.project.entity.News;
import se331.project.entity.User;

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
    UserDto getUserSimpleDto(User user);
    List<UserDto> getUserSimpleDto(List<User> users);
}