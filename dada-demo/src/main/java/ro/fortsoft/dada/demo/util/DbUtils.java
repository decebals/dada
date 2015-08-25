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
package ro.fortsoft.dada.demo.util;

import com.iciql.Db;
import org.h2.jdbcx.JdbcConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

/**
 * @author Decebal Suiu
 */
public class DbUtils {

    private static final Logger log = LoggerFactory.getLogger(DbUtils.class);

    private static final String url = "jdbc:h2:mem:dada";
//    private static final String url = "jdbc:h2:./data/dada";
    private static final String username = "";
    private static final String password = "";

    private static DataSource dataSource;

    static {
        dataSource = JdbcConnectionPool.create(url, username, password);
    }

    public static Db getDb() {
        return Db.open(dataSource);
    }

}
