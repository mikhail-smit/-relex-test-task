package mikhail.task.services;

import lombok.RequiredArgsConstructor;
import mikhail.task.exceptions.HarvestResultNotFoundException;
import mikhail.task.models.HarvestResult;
import mikhail.task.models.Product;
import mikhail.task.repositories.HarvestResultRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HarvestResultService {
    private final HarvestResultRepository harvestResultRepository;
    private final ProductService productService;

    public HarvestResult getById(int id) {
        return harvestResultRepository.findById(id)
                .orElseThrow(() -> new HarvestResultNotFoundException("Harvest result not found, id: " + id));
    }

    public List<HarvestResult> getAll() {
        return harvestResultRepository.findAll();
    }

    @Transactional
    public HarvestResult save(HarvestResult harvestResult) {
        updateProductCount(harvestResult.getProduct().getId(), harvestResult.getCount());
        return harvestResultRepository.save(harvestResult);
    }

    @Transactional
    public HarvestResult update(HarvestResult harvestResult, int id) {
        harvestResult.setId(id);
        updateProductCount(harvestResult.getProduct().getId(),
                calculateNewCount(harvestResult.getCount(), id));
        return harvestResultRepository.save(harvestResult);
    }

    @Transactional
    public void deleteById(int id) {
        HarvestResult harvestResult = getById(id);
        updateProductCount(harvestResult.getProduct().getId(),
                calculateNewCount(0, harvestResult));
        harvestResultRepository.deleteById(id);
    }

    public List<HarvestResult> getByUserId(int id) {
        return harvestResultRepository.findAllByUserId(id);
    }

    /**
     * @param id user which harvests we want to get
     *           between dates from and to
     */
    public List<HarvestResult> getByUserId(int id, Date from, Date to) {
        return harvestResultRepository.findAllByAtMomentBetweenAndUserId(from, to, id);
    }

    /**
     * @return all HarvestResults between dates from and to
     */
    public List<HarvestResult> getBetweenDates(Date from, Date to) {
        return harvestResultRepository.findAllByAtMomentBetween(from, to);
    }

    private int calculateNewCount(int newCount, int id) {
        HarvestResult oldHarvestResult = getById(id);
        return calculateNewCount(newCount, oldHarvestResult);
    }

    private int calculateNewCount(int newCount, HarvestResult oldResult) {
        return newCount - oldResult.getCount();
    }

    private void updateProductCount(int productId, int count) {
        Product changedProduct = productService.getById(productId);
        changedProduct.setCount(changedProduct.getCount() + count);
        productService.update(changedProduct, productId);
    }

}
