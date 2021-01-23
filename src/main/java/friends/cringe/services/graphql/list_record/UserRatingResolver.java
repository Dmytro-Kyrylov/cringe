package friends.cringe.services.graphql.list_record;

import friends.cringe.services.graphql.user.UserQL;
import friends.cringe.services.graphql.user.UserQLMapper;
import friends.cringe.services.user.api.UserService;
import graphql.kickstart.tools.GraphQLResolver;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRatingResolver implements GraphQLResolver<ListRecordQL.RatingByUserQL> {

  @Setter(onMethod_ = @Autowired)
  private UserService userService;

  @Setter(onMethod_ = @Autowired)
  private UserQLMapper userQLMapper;

  public UserQL user(ListRecordQL.RatingByUserQL ratingByUserQL) {
    return userQLMapper.toQl(userService.get(ratingByUserQL.getUserId()));
  }

}
