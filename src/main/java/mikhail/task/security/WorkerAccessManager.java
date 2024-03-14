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

/**
 * This AuthorizationManager controlls that User has accsess only to own harvest records
 */
@Component
@RequiredArgsConstructor
public class WorkerAccessManager implements AuthorizationManager<RequestAuthorizationContext> {
    private final UserService userService;

    /**
     *
     * @param authentication contains userDeatails about authenticated user who doing request
     * @param context contains path variables of request. For example '\\users\{id}\harvests'
     */
    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        UserDetails userWhoDoRequest = (UserDetails) authentication.get().getPrincipal();
        // if user is admin or owner he can get everything
        if (isOwnerOrAdmin(userWhoDoRequest)) {
            return new AuthorizationDecision(true);
        }

        int id = Integer.parseInt(context.getVariables().get("id"));
        User userWhichDataWants = userService.getById(id);
        // if user who do request and user which harvests we want get are same then access is open
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
