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
package ro.fortsoft.dada.demo.dao;

import ro.fortsoft.dada.demo.entity.Company;
import ro.fortsoft.dada.iciql.IciqlDbFactory;

/**
 * @author Decebal Suiu
 */
public class IciqlCompanyDao extends IciqlDao<Company> implements CompanyDao {

    public IciqlCompanyDao() {
        super();
    }

    public IciqlCompanyDao(IciqlDbFactory dbFactory) {
        super(dbFactory);
    }

    @Override
    public Company findByFiscalCode(String fiscalCode) {
        Company alias = getAlias();

        return getDb().from(alias).where(alias.getFiscalCode()).is(fiscalCode).selectFirst();
    }

}
