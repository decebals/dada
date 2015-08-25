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
package ro.fortsoft.dada.demo.entity;

import com.iciql.Iciql;
import ro.fortsoft.dada.iciql.IciqlEntity;

/**
 * @author Decebal Suiu
 */
@Iciql.IQTable(inheritColumns = true)
public class Company extends IciqlEntity {

    @Iciql.IQColumn
    private String name;

    @Iciql.IQColumn
    private String fiscalCode; // CUI or CIF

    public Company() {
    }

    public Company(Long id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public Company setName(String name) {
        this.name = name;

        return this;
    }

    public String getFiscalCode() {
        return fiscalCode;
    }

    public Company setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;

        return this;
    }

    @Override
    public String toString() {
        return "Company{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", fiscalCode='" + fiscalCode + '\'' +
            '}';
    }

}
