package mikhail.task.controllers;

import jakarta.validation.Valid;
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
public class UsersController {

    private final UserService userService;
    private final ModelMapper mapper;
    private final HarvestResultService harvestResultService;
    private final HarvestResultUtils harvestResultUtils;
    private final ErrorMessageUtils messageUtils;

    public UsersController(UserService userService, ModelMapper mapper, HarvestResultService harvestResultService, HarvestResultUtils harvestResultUtils, ErrorMessageUtils messageUtils) {
        this.userService = userService;
        this.mapper = mapper;
        this.harvestResultService = harvestResultService;
        this.harvestResultUtils = harvestResultUtils;
        this.messageUtils = messageUtils;
    }

    @GetMapping
    public List<UserDTO> getAll() {
        return userService.getAll().stream()
                .map(this::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable(name = "id") int id) {
        return toDto(userService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@Valid @RequestBody UserRegistrationDTO user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IncorrectInputFieldException(messageUtils.createMessage(bindingResult));
        }
        return toDto(userService.save(mapper.map(user, User.class)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "id") int id) {
        userService.deleteById(id);
    }

    @PutMapping("/{id}")
    public UserDTO update(@PathVariable(name = "id") int id,
                          @Valid @RequestBody UserDTO userDTO,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IncorrectInputFieldException(messageUtils.createMessage(bindingResult));
        }
        return toDto(userService.update(fromDto(userDTO), id));
    }

    @GetMapping("/{id}/harvests")
    public List<HarvestResultDTO> getUserHarvestResults(@PathVariable(name = "id") int id) {
        return harvestResultService.getByUserId(id).stream()
                .map(harvestResultUtils::toDto)
                .toList();
    }

    @GetMapping("/{id}/harvests/between")
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
