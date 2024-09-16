# liquibase-kotlin

Modules for using Liquibase with Kotlin

## Features

### Kotlin DSL

You can choose between KotlinScript and Kotlin (not script).
Both have the same syntax for changeSet.

> [!NOTE]
> kotlin-script module is It is can integration with existing migration files.
> But when integrating the kotlin-typesafe module, you need to load kotlin-typesafe migration using include or includeAll.

#### kotlin-script

Please place the kts files under `src/main/resources`.

kotlin-script example
https://github.com/momosetkn/liquibase-kotlin-example/blob/main/liquibase-kotlin-script-example/src/main/kotlin/Main.kt

#### kotlin-typesafe

kotlin-typesafe is read KotlinTypesafeDatabaseChangeLog class in classpath with using `Class.forName`.
the changelog file is specifying the class name.

example
```kotlin
class DatabaseChangelogAll : KotlinTypesafeDatabaseChangeLog({
    includeAll("changelogs.main") // specify package
})
```

```kotlin
class DatabaseChangelog0 : KotlinTypesafeDatabaseChangeLog({
    changeSet(author = "momose (generated)", id = "1715520327312-0") {
        tagDatabase("started")
    }

    changeSet(author = "momose (generated)", id = "1715520327312-10") {
        createTable(tableName = "company") {
            column(name = "id", type = "UUID") {
                constraints(nullable = false, primaryKey = true)
            }
            column(name = "name", type = "VARCHAR(256)")
        }
    }
})
```

kotlin-typesafe example
https://github.com/momosetkn/liquibase-kotlin-example/blob/main/liquibase-kotlin-typesafe-example/src/main/kotlin/Main.kt

### Kotlin client

Client module can execute Liquibase commands programmatically.

example
```kotlin
 val client = LiquibaseClient {
    globalArgs {
        general {
            showBanner = false
        }
    }
}
client.update(
    driver = "org.postgresql.Driver",
    url = "jdbc:postgresql://127.0.0.1:5432/test",
    username = "root",
    password = "test",
    changelogFile = "db.changelog.all.kts",
)
```

### Use Komapper on customChange

`liquibase-kotlin-custom-komapper-jdbc-change` module can use [Komapper](https://www.komapper.org/) on customChange

> [!NOTE]
> can use both the `kotlin-script` and `kotlin-typesafe`.

add bellow dependencies.

```kotlin
    implementation("com.github.momosetkn.liquibase-kotlin:liquibase-kotlin-custom-komapper-jdbc-change:$liquibaseKotlinVersion")
```

changeSet example
```kotlin
changeSet(author = "momose (generated)", id = "1715520327312-40") {
    customKomapperJdbcChange(
        execute = {
            val query = QueryDsl.executeScript(
                """
                CREATE TABLE created_by_komapper (
                    id uuid NOT NULL,
                    name character varying(256)
                );
                """.trimIndent()
            )
            db.runQuery(query)
        },
        rollback = {
            val query = QueryDsl.executeScript("DROP TABLE created_by_komapper")
            db.runQuery(query)
        },
    )
}
```

## Prerequisite

- JDK 17 or later

## How to install

```kotlin
repositories {
    // Add below to repositories. because this product is publish to jitpack.
    maven { url = URI("https://jitpack.io") }
}

dependencies {
    // liquibase
    implementation("org.liquibase:liquibase-core:4.29.2")
    val liquibaseKotlinVersion = "0.3.3"
    // You can choose to install either kotlin-script or kotlin-typesafe.
    // for kotlin-script
    implementation("com.github.momosetkn.liquibase-kotlin:liquibase-kotlin-dsl:$liquibaseKotlinVersion")
    implementation("com.github.momosetkn.liquibase-kotlin:liquibase-kotlin-script-parser:$liquibaseKotlinVersion")
    implementation("com.github.momosetkn.liquibase-kotlin:liquibase-kotlin-script-serializer:$liquibaseKotlinVersion")
    // for kotlin-typesafe
    implementation("com.github.momosetkn.liquibase-kotlin:liquibase-kotlin-dsl:$liquibaseKotlinVersion")
    implementation("com.github.momosetkn.liquibase-kotlin:liquibase-kotlin-typesafe-parser:$liquibaseKotlinVersion")
    implementation("com.github.momosetkn.liquibase-kotlin:liquibase-kotlin-typesafe-serializer:$liquibaseKotlinVersion")
    // If you want to use call liquibase-command by kotlin, add the following code.
    implementation("com.github.momosetkn.liquibase-kotlin:liquibase-kotlin-client:$liquibaseKotlinVersion")
    // If you want to use komapper on customChange, add the following code.
    implementation("com.github.momosetkn.liquibase-kotlin:liquibase-kotlin-custom-komapper-jdbc-change:$liquibaseKotlinVersion")
}
```

# example project
https://github.com/momosetkn/liquibase-kotlin-example
