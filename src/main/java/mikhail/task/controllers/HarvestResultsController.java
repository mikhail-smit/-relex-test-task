package mikhail.task.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mikhail.task.dto.DatePeriod;
import mikhail.task.dto.HarvestResultDTO;
import mikhail.task.exceptions.IncorrectInputFieldException;
import mikhail.task.services.HarvestResultService;
import mikhail.task.utils.ErrorMessageUtils;
import mikhail.task.utils.HarvestResultUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest controller for harvest entity.
 */
@RestController
@RequestMapping("/v1/harvests")
@RequiredArgsConstructor
@Tag(name = "Hervest Results", description = "Methods for results of employee work")
public class HarvestResultsController {
    private final HarvestResultService harvestResultService;
    private final HarvestResultUtils harvestResultUtils;
    private final ErrorMessageUtils messageUtils;

    @GetMapping
    @Operation(summary = "Returns all results")
    public List<HarvestResultDTO> getAll() {
        return harvestResultService.getAll().stream()
                .map(harvestResultUtils::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Returns harvest result by Id")
    public HarvestResultDTO getHarvestResult(@PathVariable("id") int id) {
        return harvestResultUtils.toDto(harvestResultService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Adds new harvest result")
    public HarvestResultDTO create(@Valid @RequestBody HarvestResultDTO harvestResult,
                                   @Parameter(hidden = true) BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IncorrectInputFieldException(messageUtils.createMessage(bindingResult));
        }
        return harvestResultUtils.toDto(
                harvestResultService.save(harvestResultUtils.fromDto(harvestResult))
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletes harvest result by Id")
    public void delete(@PathVariable(name = "id") int id) {
        harvestResultService.deleteById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates harvest result data")
    public HarvestResultDTO update(@PathVariable(name = "id") int id,
                                   @Valid @RequestBody HarvestResultDTO harvestResult,
                                   @Parameter(hidden = true) BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IncorrectInputFieldException(messageUtils.createMessage(bindingResult));
        }
        return harvestResultUtils.toDto(
                harvestResultService.update(harvestResultUtils.fromDto(harvestResult), id)
        );
    }

    /**
     * @param datePeriod two dates in DTO
     * @return HarvestResults which created between two dates
     */
    @GetMapping("/between")
    @Operation(summary = "Returns all harvest results between dates")
    public List<HarvestResultDTO> getBetweenDates(@RequestBody DatePeriod datePeriod) {
        return harvestResultService.getBetweenDates(datePeriod.getFrom(), datePeriod.getTo()).stream()
                .map(harvestResultUtils::toDto)
                .toList();
    }
}
