package mikhail.task.security;

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

@Component
public class HarvestResultAccessManager implements AuthorizationManager<RequestAuthorizationContext> {
    private final HarvestResultService harvestResultService;

    public HarvestResultAccessManager(HarvestResultService harvestResultService) {
        this.harvestResultService = harvestResultService;
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        UserDetails userWhoDoRequest = (UserDetails) authentication.get().getPrincipal();
        if (isOwnerOrAdmin(userWhoDoRequest)) {
            return new AuthorizationDecision(true);
        }

        int id = Integer.parseInt(context.getVariables().get("id"));
        HarvestResult harvestWhichDataWants = harvestResultService.getById(id);
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
