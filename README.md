Tiny Generic Dao in Java
=====================
[![Travis CI Build Status](https://travis-ci.org/decebals/dada.png)](https://travis-ci.org/decebals/dada)
[![Maven Central](http://img.shields.io/maven-central/v/ro.fortsoft.dada/dada-core.svg)](http://search.maven.org/#search|ga|1|dada)

It's an open source (Apache License) tiny generic dao in Java, with minimal dependencies(only slf4j-api for dada-core) and a quick learning curve.
The goal of this project is to create a tiny generic dao in Java with a micro core and multiple independent implementations (inmemory, csv, hibernate, jpa, iciql).

This project could be in time a Spring Data without Spring. :smile:

The project is in early stage but it's functional. I will try to add more documentation, javadoc, unit tests.

Any contribution is very welcome. I hope to create a community around this project.

Modules
-------------------
Practically Dada is a microframework and the aim is to keep the core simple but extensible. I try to create a little ecosystem (extensions/modules) based on this core with the help of the community.
For now are available these modules:
- csv (very useful for tests and fast prototypes)
- [iciql](http://iciql.com)

Maybe the modules for JPA, Hibernate are the next implementations.

Components
-------------------
- **GenericDao** is the base interface for all Dao.
- **InMemoryGenericDao** is a bonus implementation. Can be used with success in tests and prototypes.
- **EntityDao** is a simple convenient wrapper over GenericDao that works with Entity classes.
- **GenericService, EntityService** are bonus implementations for people that are using three tier architecture (Dao <-> Service).

Using Maven
-------------------
In your pom.xml you must define the dependencies to Dada artifacts with:

```xml
<dependency>
    <groupId>ro.fortsoft.dada</groupId>
    <artifactId>dada-core</artifactId>
    <version>${dada.version}</version>
</dependency>
```

where ${dada.version} is the last dada version.

You may want to check for the latest released version using [Maven Search](http://search.maven.org/#search%7Cga%7C1%7Cdada)

How to use
-------------------
It's very simple to add dada in your application:

```java
public static void main(String[] args) {
    CompanyDao companyDao = new MyDaoFactory().createCompanyDao();

    System.out.println(">>> Find company with id " + 1L);
    Company company = companyDao.findById(1L);
    System.out.println("company = " + company);

    System.out.println(">>> Find all companies");
    List<Company> companies = companyDao.findAll();
    System.out.println("companies = " + companies);

    System.out.println(">>> Delete company with id " + 1L);
    companyDao.deleteById(1L);

    System.out.println(">>> Find all companies");
    companies = companyDao.findAll();
    System.out.println("companies = " + companies);
}
```

where `Company` is a simple entity:

```java
public class Company extends AbstractEntity {

    private String name;

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
```

I extended `AbstractEntity` because I want to bypass to this class some boring stuff (set/get id, hashCode, equals methods) but it's not mandatory to extend it.

The `CompanyDao` interface looks like:

```java
public interface CompanyDao extends EntityDao<Company> {

    // define bellow non crud (business specific) methods
    public Company findByFiscalCode(String fiscalCode);

}
```

A possible implementation (I am using Iciql implementation) for `CompanyDao` can be:

```java
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
```

Demo
-------------------
I have a tiny demo application. The demo application is in __dada-demo__ folder.

How to build
-------------------
Requirements:
- [Git](http://git-scm.com/)
- JDK 7 (test with `java -version`)
- [Apache Maven 3](http://maven.apache.org/) (test with `mvn -version`)

Steps:
- create a local clone of this repository (with `git clone https://github.com/decebals/dada.git`)
- go to project's folder (with `cd dada`)
- build the artifacts (with `mvn clean package` or `mvn clean install`)

After above steps a folder _dada/target_ is created and all goodies are in that folder.

Versioning
------------
PF4J will be maintained under the Semantic Versioning guidelines as much as possible.

Releases will be numbered with the follow format:

`<major>.<minor>.<patch>`

And constructed with the following guidelines:

* Breaking backward compatibility bumps the major
* New additions without breaking backward compatibility bumps the minor
* Bug fixes and misc changes bump the patch

For more information on SemVer, please visit http://semver.org.
