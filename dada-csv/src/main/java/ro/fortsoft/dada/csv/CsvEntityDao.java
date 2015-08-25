/*
 * Copyright (C) 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ro.fortsoft.dada.csv;

import ro.fortsoft.dada.core.Entity;
import ro.fortsoft.dada.core.EntityDao;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Decebal Suiu
 */
public class CsvEntityDao<T extends Entity> extends CsvGenericDao<T, Long> implements EntityDao<T> {

    private AtomicLong nextId = new AtomicLong();

    public CsvEntityDao(String csvFile) {
        this(csvFile, Collections.EMPTY_LIST);
    }

    public CsvEntityDao(String csvFile, List<T> defaults) {
        this(csvFile, defaults, false);
    }

    public CsvEntityDao(String csvFile, List<T> defaults, boolean cleanOnStart) {
        super(csvFile);

        if (cleanOnStart) {
            writeToCsv(); // overwrite csvFile with empty values
        }

        long count = readFromCsv();

        if ((count == 0) && !defaults.isEmpty()) {
            addAll(defaults);
            writeToCsv();
        }

        nextId.set(calculateNextId());
    }

    @Override
    public Long nextId() {
        return nextId.incrementAndGet();
    }

    private long calculateNextId() {
        long nextId = 0;
        for (T entity : findAll()) {
            nextId = Math.max(nextId, entity.getId());
        }

        return nextId;
    }

}
