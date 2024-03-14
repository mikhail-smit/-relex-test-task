package mikhail.task.controllers;

import mikhail.task.dto.DatePeriod;
import mikhail.task.dto.HarvestResultDTO;
import mikhail.task.services.HarvestResultService;
import mikhail.task.utils.HarvestResultUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/harvests")
public class HarvestResultsController {
    private final HarvestResultService harvestResultService;
    private final HarvestResultUtils harvestResultUtils;

    public HarvestResultsController(HarvestResultService harvestResultService, HarvestResultUtils harvestResultUtils) {
        this.harvestResultService = harvestResultService;
        this.harvestResultUtils = harvestResultUtils;
    }

    @GetMapping
    public List<HarvestResultDTO> getAll() {
        return harvestResultService.getAll().stream()
                .map(harvestResultUtils::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public HarvestResultDTO getHarvestResult(@PathVariable("id") int id) {
        return harvestResultUtils.toDto(harvestResultService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HarvestResultDTO create(@RequestBody HarvestResultDTO harvestResult) {
        return harvestResultUtils.toDto(
                harvestResultService.save(harvestResultUtils.fromDto(harvestResult))
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "id") int id) {
        harvestResultService.deleteById(id);
    }

    @PutMapping("/{id}")
    public HarvestResultDTO update(@PathVariable(name = "id") int id,
                                   @RequestBody HarvestResultDTO harvestResult) {
        return harvestResultUtils.toDto(
                harvestResultService.update(harvestResultUtils.fromDto(harvestResult), id)
        );
    }

    @GetMapping("/between")
    public List<HarvestResultDTO> getBetweenDates(@RequestBody DatePeriod datePeriod) {
        return harvestResultService.getBetweenDates(datePeriod.getFrom(), datePeriod.getTo()).stream()
                .map(harvestResultUtils::toDto)
                .toList();
    }
}
