package com.github.kislayverma.rulette.rest.api.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/provider")
public class DataProviderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataProviderController.class);

    @Autowired
    private DataProviderService dataProviderService;

    @GetMapping("/")
    public List<DataProviderConfig> getAllProviders(@RequestParam(defaultValue = "1") Integer pageNum,
                                                    @RequestParam(defaultValue = "50") Integer pageSize) {
        return dataProviderService.getAllProviderConfigs();
    }

    @GetMapping("/{providerName}")
    public DataProviderConfig getProviderByName(@PathVariable String providerName) {
        return dataProviderService.getProviderConfig(providerName);
    }
}
