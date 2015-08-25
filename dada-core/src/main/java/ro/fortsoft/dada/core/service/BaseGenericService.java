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
package ro.fortsoft.dada.core.service;

import ro.fortsoft.dada.core.GenericDao;
import ro.fortsoft.dada.core.Identifiable;

import java.io.Serializable;
import java.util.List;

/**
 * @author Decebal Suiu
 */
public class BaseGenericService<T extends Identifiable<ID>, ID extends Serializable> implements GenericService<T, ID> {

    private GenericDao<T, ID> dao;

    public BaseGenericService(GenericDao<T, ID> dao) {
        this.dao = dao;
    }

    @Override
    public T findById(ID id) {
        return dao.findById(id);
    }

    @Override
    public List<T> findByExample(T example) {
        return dao.findByExample(example);
    }

    @Override
    public List<T> findAll() {
        return dao.findAll();
    }

    @Override
    public boolean deleteById(ID id) {
        return dao.deleteById(id);
    }

    @Override
    public T save(T entity) {
        return dao.save(entity);
    }

    @Override
    public long count() {
        return dao.count();
    }

    protected GenericDao<T, ID> getDao() {
        return dao;
    }

}
