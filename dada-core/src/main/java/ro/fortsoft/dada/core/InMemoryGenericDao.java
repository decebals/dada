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
package ro.fortsoft.dada.core;

import ro.fortsoft.dada.core.util.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Decebal Suiu
 */
public abstract class InMemoryGenericDao<T extends Identifiable<ID>, ID extends Serializable> extends AbstractGenericDao<T, ID> {

    protected List<T> entities = new CopyOnWriteArrayList<>();

    public InMemoryGenericDao() {
    }

    public InMemoryGenericDao(List<T> entities) {
        addAll(entities);
    }

    public abstract ID nextId();

    @Override
    public T findById(ID id) {
        if (id != null) {
            for (T entity : entities) {
                if (Objects.equals(id, entity.getId())) {
                    return entity;
                }
            }
        }

        return null;
    }

    @Override
    public List<T> findByExample(T example) {
        List<T> foundEntities = new ArrayList<>();

        if (!entities.isEmpty()) {
            try {
                Map<String, Method> getters = BeanUtils.findGetters(example);
                Map<String, Object> exampleProperties = BeanUtils.getProperties(example, getters);

                for (T entity : entities) {
                    Map<String, Object> entityProperties = BeanUtils.getProperties(entity, getters);
                    boolean found = true;
                    for (Map.Entry<String, Object> exampleProperty : exampleProperties.entrySet()) {
                        if (!Objects.equals(exampleProperty.getValue(), entityProperties.get(exampleProperty.getKey()))) {
                            found = false;
                            break;
                        }
                    }

                    if (found) {
                        foundEntities.add(entity);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return foundEntities;
    }

    @Override
    public List<T> findAll() {
        return Collections.unmodifiableList(entities);
    }

    @Override
    public T save(T entity) {
        if (entity.getId() != null) {
            // update
            for (int i = 0; i < entities.size(); i++) {
                if (Objects.equals(entity.getId(), entities.get(i).getId())) {
                    entities.set(i, entity);
                    break;
                }
            }
        } else {
            // new
            entity.setId(nextId());
            entities.add(entity);
        }

        return entity;
    }

    @Override
    public boolean deleteById(ID id) {
        if (id != null) {
            Iterator<T> iterator = entities.iterator();
            while (iterator.hasNext()) {
                if (Objects.equals(id, iterator.next().getId())) {
                    iterator.remove();
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Used by the service layer.
     *
     * @param entities
     */
    public void addAll(List<T> entities) {
        for (T entity : entities) {
            if (entity.getId() == null) {
                entity.setId(nextId());
            }
            this.entities.add(entity);
        }
    }

    @Override
    public long count() {
        return entities.size();
    }

}
