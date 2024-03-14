package mikhail.task.security;

import lombok.RequiredArgsConstructor;
import mikhail.task.models.User;
import mikhail.task.services.UserService;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class WorkerAccessManager implements AuthorizationManager<RequestAuthorizationContext> {
    private final UserService userService;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        UserDetails userWhoDoRequest = (UserDetails) authentication.get().getPrincipal();
        if (isOwnerOrAdmin(userWhoDoRequest)) {
            return new AuthorizationDecision(true);
        }

        int id = Integer.parseInt(context.getVariables().get("id"));
        User userWhichDataWants = userService.getById(id);
        return new AuthorizationDecision(isSameUser(userWhoDoRequest, userWhichDataWants));
    }

    private boolean isOwnerOrAdmin(UserDetails user) {
        return user.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_OWNER")
                        || role.getAuthority().equals("ROLE_ADMIN"));
    }

    private boolean isSameUser(UserDetails userDetails, User user) {
        return userDetails.getUsername().equals(user.getEmail());
    }
}
