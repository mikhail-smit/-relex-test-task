package mikhail.task.utils;

import mikhail.task.dto.HarvestResultDTO;
import mikhail.task.models.HarvestResult;
import mikhail.task.models.Product;
import mikhail.task.models.User;
import org.springframework.stereotype.Component;

/**
 * Harvest result DTO cant be mapped with ModelMapper so this class exists
 */
@Component
public class HarvestResultUtils {
    public HarvestResultDTO toDto(HarvestResult harvest) {
        HarvestResultDTO dto = new HarvestResultDTO();
        dto.setId(harvest.getId());
        dto.setProductId(harvest.getProduct().getId());
        dto.setUserId(harvest.getUser().getId());
        dto.setCount(harvest.getCount());
        dto.setAtMoment(harvest.getAtMoment());
        return dto;
    }

    public HarvestResult fromDto(HarvestResultDTO dto) {
        HarvestResult harvestResult = new HarvestResult();
        harvestResult.setId(dto.getId());
        harvestResult.setCount(dto.getCount());
        harvestResult.setAtMoment(dto.getAtMoment());

        User user = new User();
        user.setId(dto.getUserId());
        harvestResult.setUser(user);

        Product product = new Product();
        product.setId(dto.getProductId());
        harvestResult.setProduct(product);

        return harvestResult;
    }
}
