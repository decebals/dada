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

import java.io.Serializable;
import java.util.List;

/**
 * A DAO interface for storing {@link Identifiable}s.
 *
 * @author Decebal Suiu
 */
public interface GenericDao<T extends Identifiable<ID>, ID extends Serializable> {

    /**
     * Returns the {@link Identifiable} entity with the specified identifier.
     *
     * @param id
     * @return
     */
    public T findById(ID id);

    public List<T> findByExample(T example);

    /**
     * Returns all the {@link Identifiable} entities.
     *
     * @return
     */
    public List<T> findAll();

    /**
     * Stores the {@link Identifiable} entity.
     *
     * @param entity
     * @return
     */
    public T save(T entity);

    /**
     * Removes the {@link Identifiable} entity.
     *
     * @param id
     */
    public boolean deleteById(ID id);

    /**
     * Returns the total number of entities.
     * @return
     */
    public long count();

}
