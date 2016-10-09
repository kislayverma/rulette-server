/*
 * Copyright 2016 kislay.verma.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.kislayverma.rulette.rest.storage;

import com.github.kislayverma.rulette.core.data.IDataProvider;
import com.github.kislayverma.rulette.mysql.MysqlDataProvider;
import com.github.kislayverma.rulette.rest.exception.StorageEngineException;
import java.io.IOException;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author kislay.verma
 */
public class DataProviderFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataProviderFactory.class);

    public static IDataProvider getDataProvider(String storageEngineType, String storageConfigFilePath) throws StorageEngineException {
        LOGGER.info("Loading storage engine for key : " + storageEngineType);

        StorageEngine engine;
        try {
            engine = StorageEngine.valueOf(storageEngineType);
        } catch (IllegalArgumentException ex) {
            throw new StorageEngineException("Invalid storage engine : " + storageEngineType);
        }

        LOGGER.info("Initializing " + engine.name() + " data provider...");
        switch (engine) {
            case MYSQL:
                try {
                    return new MysqlDataProvider(storageConfigFilePath);
                } catch (IOException | SQLException ex) {
                    throw new StorageEngineException("Error constructing data provider", ex);
                }
            default:
                throw new StorageEngineException("Don't know how to load storage engine : " + engine.name());
        }
    }
}
