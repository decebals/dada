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

import ro.fortsoft.dada.core.Identifiable;

import java.io.Serializable;
import java.util.List;

/**
 * @author Decebal Suiu
 */
public interface GenericService<T extends Identifiable<ID>, ID extends Serializable> {

    T findById(ID id);

    List<T> findByExample(T example);

    List<T> findAll();

    boolean deleteById(ID id);

    T save(T entity);

    long count();

}
