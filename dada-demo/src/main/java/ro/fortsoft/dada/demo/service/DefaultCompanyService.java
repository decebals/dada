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
package ro.fortsoft.dada.demo.service;

import ro.fortsoft.dada.demo.dao.CompanyDao;
import ro.fortsoft.dada.demo.dao.IciqlCompanyDao;
import ro.fortsoft.dada.demo.entity.Company;
import ro.fortsoft.dada.core.service.BaseEntityService;

/**
 * @author Decebal Suiu
 */
public class DefaultCompanyService extends BaseEntityService<Company> implements CompanyService {

    public DefaultCompanyService() {
//        super(new CsvCompanyDao(defaults));
        super(new IciqlCompanyDao());
    }

    @Override
    public Company findByFiscalCode(String fiscalCode) {
        return getDao().findByFiscalCode(fiscalCode);
    }

    @Override
    protected CompanyDao getDao() {
        return (CompanyDao) super.getDao();
    }

}
