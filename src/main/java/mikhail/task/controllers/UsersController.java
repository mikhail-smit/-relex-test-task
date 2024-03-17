package mikhail.task.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mikhail.task.dto.DatePeriod;
import mikhail.task.dto.HarvestResultDTO;
import mikhail.task.dto.UserDTO;
import mikhail.task.dto.UserRegistrationDTO;
import mikhail.task.exceptions.IncorrectInputFieldException;
import mikhail.task.models.User;
import mikhail.task.services.HarvestResultService;
import mikhail.task.services.UserService;
import mikhail.task.utils.ErrorMessageUtils;
import mikhail.task.utils.HarvestResultUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Methods for users")
public class UsersController {

    private final UserService userService;
    private final ModelMapper mapper;
    private final HarvestResultService harvestResultService;
    private final HarvestResultUtils harvestResultUtils;
    private final ErrorMessageUtils messageUtils;

    @GetMapping
    @Operation(summary = "Returns all users")
    public List<UserDTO> getAll() {
        return userService.getAll().stream()
                .map(this::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Returns user by Id")
    public UserDTO getUser(@PathVariable(name = "id") int id) {
        return toDto(userService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Adds new user")
    public UserDTO create(@Valid @RequestBody UserRegistrationDTO user,
                          @Parameter(hidden = true) BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IncorrectInputFieldException(messageUtils.createMessage(bindingResult));
        }
        return toDto(userService.save(mapper.map(user, User.class)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletes user by Id")
    public void delete(@PathVariable(name = "id") int id) {
        userService.deleteById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates user data")
    public UserDTO update(@PathVariable(name = "id") int id,
                          @Valid @RequestBody UserRegistrationDTO user,
                          @Parameter(hidden = true) BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IncorrectInputFieldException(messageUtils.createMessage(bindingResult));
        }
        return toDto(userService.update(mapper.map(user, User.class), id));
    }

    /**
     * @param id user id which HarvestResults client want to find
     * @return HarvestResults of user
     */
    @GetMapping("/{id}/harvests")
    @Operation(summary = "Returns harvests results of user by Id")
    public List<HarvestResultDTO> getUserHarvestResults(@PathVariable(name = "id") int id) {
        return harvestResultService.getByUserId(id).stream()
                .map(harvestResultUtils::toDto)
                .toList();
    }

    /**
     * @param id     user id which HarvestResults client want to find
     * @param period between two dates
     * @return HarvestResults of user between dates
     */
    @GetMapping("/{id}/harvests/between")
    @Operation(summary = "Returns harvest results of user between dates by user Id")
    public List<HarvestResultDTO> getUserHarvestResultsForTime(@PathVariable(name = "id") int id,
                                                               @RequestBody DatePeriod period) {
        return harvestResultService.getByUserId(id, period.getFrom(), period.getTo()).stream()
                .map(harvestResultUtils::toDto)
                .toList();
    }

    private UserDTO toDto(User user) {
        return mapper.map(user, UserDTO.class);
    }

    private User fromDto(UserDTO dto) {
        return mapper.map(dto, User.class);
    }
}
