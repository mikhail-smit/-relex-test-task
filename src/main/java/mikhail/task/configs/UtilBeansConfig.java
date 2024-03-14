package mikhail.task.configs;

import mikhail.task.utils.HarvestResultUtils;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilBeansConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public HarvestResultUtils harvestResultUtils() {
        return new HarvestResultUtils();
    }
}
