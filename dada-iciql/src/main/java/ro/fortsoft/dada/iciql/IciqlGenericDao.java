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
package ro.fortsoft.dada.iciql;

import com.iciql.Db;
import com.iciql.QueryWhere;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.fortsoft.dada.core.AbstractGenericDao;
import ro.fortsoft.dada.core.Identifiable;
import ro.fortsoft.dada.core.util.BeanUtils;
import ro.fortsoft.dada.iciql.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Decebal Suiu
 */
public class IciqlGenericDao<T extends Identifiable<ID>, ID extends Serializable> extends AbstractGenericDao<T, ID> {

    private static final Logger log = LoggerFactory.getLogger(IciqlGenericDao.class);

    private IciqlDbFactory dbFactory;

    public IciqlGenericDao(IciqlDbFactory dbFactory) {
        this.dbFactory = dbFactory;
    }

    @Override
    public T findById(ID id) {
        T alias = getAlias();

        return getDb().from(alias).where(alias.getId()).is(id).selectFirst();
    }

    @Override
    public List<T> findByExample(T example) {
        List<T> entities = new ArrayList<>();

        try {
            Map<String, Method> getters = BeanUtils.findGetters(example);
            Map<String, Object> exampleProperties = BeanUtils.getProperties(example, getters);

            List<String> clauses = new ArrayList<>();
            List<Object> parameters = new ArrayList<>();
            for (Map.Entry<String, Object> property : exampleProperties.entrySet()) {
                String propertyName = property.getKey();
                clauses.add('(' + propertyName + " = ?)");
                parameters.add(property.getValue());
            }

//            String clause = String.join(" AND ", clauses); // need java 8
            String clause = StringUtils.join(" AND ", clauses);
            QueryWhere<T> query = getDb().from(getAlias()).where(clause, parameters);
            log.debug(query.toSQL());

            return query.select();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return entities;
    }

    @Override
    public List<T> findAll() {
        return getDb().from(getAlias()).select();
    }

    @Override
    public T save(T entity) {
        getDb().merge(entity);

        return entity;
    }

    @Override
    public boolean deleteById(ID id) {
        T entity = findById(id);

        return (entity != null) && getDb().delete(entity);
    }

    @Override
    public long count() {
        return getDb().from(getAlias()).selectCount();
    }

    protected Db getDb() {
        return dbFactory.getDb();
    }

    protected T getAlias() {
        try {
            return getPersistentClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Cannot create the alias", e);
        }
    }

}

