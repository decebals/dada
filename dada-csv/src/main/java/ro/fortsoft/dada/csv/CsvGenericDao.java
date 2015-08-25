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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import ro.fortsoft.dada.core.Identifiable;
import ro.fortsoft.dada.core.InMemoryGenericDao;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Decebal Suiu
 */
public abstract class CsvGenericDao<T extends Identifiable<ID>, ID extends Serializable> extends InMemoryGenericDao<T, ID> {

    protected String csvFile;

    public CsvGenericDao(String csvFile) {
        this.csvFile = csvFile;
    }

    public long readFromCsv() {
        File file = new File(csvFile);
        if (!file.exists() || !file.isFile()) {
            return 0;
        }

        Class<T> persistentClass = getPersistentClass();

        // create mapper and schema
        CsvMapper mapper = new CsvMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        CsvSchema schema = mapper.schemaFor(persistentClass).withHeader();

        this.entities = new ArrayList<>();

        // read entities
        long count = 0;
        try {
            MappingIterator<T> it = mapper.reader(persistentClass).with(schema).readValues(file);
            while (it.hasNextValue()) {
                entities.add(it.nextValue());
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return count;
    }

    public long writeToCsv() {
        Class<T> persistentClass = getPersistentClass();

        // create mapper and schema
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(persistentClass).withHeader();

        // write entities
        long count = 0;
        try {
            mapper.writer().with(schema).writeValue(new File(csvFile), entities);
            count = entities.size();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return count;
    }

    @Override
    public T save(T entity) {
        T result = super.save(entity);
        writeToCsv();

        return result;
    }

    @Override
    public boolean deleteById(ID id) {
        boolean result = super.deleteById(id);
        if (result) {
            writeToCsv();
        }

        return result;
    }

}
