package ir.mhkapr.webtaxi.mapper;

import ir.mhkapr.webtaxi.DTOs.UserInfoDTO;
import ir.mhkapr.webtaxi.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserUserInfoDTOMapper {
    UserUserInfoDTOMapper INSTANCE = Mappers.getMapper(UserUserInfoDTOMapper.class);

    UserInfoDTO UserToUserInfoDTO(User user);

    User UserInfoDTOToUser(UserInfoDTO userInfo);
}
