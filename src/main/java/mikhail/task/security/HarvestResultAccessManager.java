package mikhail.task.security;

import lombok.RequiredArgsConstructor;
import mikhail.task.models.HarvestResult;
import mikhail.task.models.User;
import mikhail.task.services.HarvestResultService;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * This AuthorizationManager controlls that User has accsess only to HarvestResults
 * which this User created
 */
@Component
@RequiredArgsConstructor
public class HarvestResultAccessManager implements AuthorizationManager<RequestAuthorizationContext> {
    private final HarvestResultService harvestResultService;

    /**
     * @param authentication contains userDeatails about authenticated user who doing request
     * @param context        contains path variables of request
     * @return AuthorizationDecision with true if user has access else with false
     */
    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        UserDetails userWhoDoRequest = (UserDetails) authentication.get().getPrincipal();
        // admin and owner can view every record
        if (isOwnerOrAdmin(userWhoDoRequest)) {
            return new AuthorizationDecision(true);
        }

        int id = Integer.parseInt(context.getVariables().get("id"));
        HarvestResult harvestWhichDataWants = harvestResultService.getById(id);
        // if userWhoDoRequest and user which did record about HarvestResult are one user then access is open
        return new AuthorizationDecision(isSameUser(userWhoDoRequest, harvestWhichDataWants.getUser()));
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
