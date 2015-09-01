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

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Decebal Suiu
 */
public class InMemoryEntityDao<T extends Entity> extends InMemoryGenericDao<T, Long> implements EntityDao<T> {

    private AtomicLong nextId = new AtomicLong();

    public InMemoryEntityDao() {
    }

    public InMemoryEntityDao(List<T> entities) {
        addAll(entities);
    }

    @Override
    public Long getNextId() {
        return nextId.incrementAndGet();
    }

    public void setNextId(long nextId) {
        this.nextId.set(nextId);
    }

}
