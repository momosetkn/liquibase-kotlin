# liquibase-kotlin
[![Maven Central](https://img.shields.io/maven-central/v/io.github.momosetkn/liquibase-kotlin-dsl)](https://search.maven.org/artifact/io.github.momosetkn/liquibase-kotlin-dsl)
[![Ask DeepWiki](https://deepwiki.com/badge.svg)](https://deepwiki.com/momosetkn/liquibase-kotlin)

[Liquibase-kotlin](https://momosetkn.github.io/liquibase-kotlin) was created with the aim of integrating [liquibase](https://github.com/liquibase/liquibase) with kotlin.
This module provides Kotlin-DSL, Wrapper-client, ORM-integration.

To ensure compatibility with the latest Liquibase versions, we also conduct tests with snapshot versions on every-day.

Liquibase-kotlin versions are named in correspondence with Liquibase versions, following the format `${liquibaseVersion}-${majar.minor.patch}`.
Examples, Liquibase-kotlin versions correspond to Liquibase4.29.2-0.8.0 or Liquibase4.29.2-0.8.1

Liquibase-kotlin documentation page 
- [liquibase-kotlin document](https://momosetkn.github.io/liquibase-kotlin-docs) 

## How to install

```kotlin
dependencies {
    // liquibase
    implementation("org.liquibase:liquibase-core:4.33.0")
    // BOM (Bill of Materials) to align versions of all liquibase-kotlin modules.
    // This ensures compatibility between liquibase-kotlin-starter, client, and other extensions.
    implementation("io.github.momosetkn:liquibase-kotlin-bom:4.33.0-0.10.1")
    // You can choose to install either kotlin-script or kotlin-compiled.
    // for kotlin-script
    implementation("io.github.momosetkn:liquibase-kotlin-starter-script")
    // for kotlin-compiled
    implementation("io.github.momosetkn:liquibase-kotlin-starter-compiled")
    // If you want to use call liquibase from kotlin, add the following code.
    implementation("io.github.momosetkn:liquibase-kotlin-client")
}
```

## Features

### Kotlin-DSL

You can choose between KotlinScript and Kotlin (not script).
Both have the same syntax for changeSet.

> [!NOTE]
> kotlin-script module is It is can integration with existing migration files.
> But when integrating the kotlin-compiled module, you need to load kotlin-compiled migration using include or includeAll.

| Feature | KotlinScript | KotlinCompiled |
| --- | --- | --- |
| File location | `.kts` files under `src/main/resources` | Kotlin classes implementing `KotlinCompiledDatabaseChangeLog` on the classpath |
| Loading method | scripts loaded directly by path | `include` with a `.class` path; `includeAll` with a `/`-separated classpath directory or dot-separated package name |

#### kotlin-script

Please place the kts files under `src/main/resources`.

kotlin-script example
https://github.com/momosetkn/liquibase-kotlin-example/blob/main/liquibase-kotlin-script-example/src/main/kotlin/Main.kt

#### kotlin-compiled

kotlin-compiled is read KotlinCompiledDatabaseChangeLog class in classpath with using `Class.forName`.
the changelog file is specifying the class name.

example
```kotlin
class DatabaseChangelogAll : KotlinCompiledDatabaseChangeLog({
    includeAll("changelogs.main") // specify package
})
```

```kotlin
class DatabaseChangelog0 : KotlinCompiledDatabaseChangeLog({
    changeSet(author = "momose", id = "100-0") {
        tagDatabase("started")
    }

    changeSet(author = "momose", id = "100-10") {
        createTable(tableName = "company") {
            column(name = "id", type = "UUID") {
                constraints(nullable = false, primaryKey = true)
            }
            column(name = "name", type = "VARCHAR(256)")
        }
    }
})
```

kotlin-compiled example
https://github.com/momosetkn/liquibase-kotlin-example/blob/main/liquibase-kotlin-compiled-example/src/main/kotlin/Main.kt

### Kotlin client

Client module can execute Liquibase commands programmatically.

example
```kotlin
configureLiquibase {
    global {
        general {
            showBanner = false
        }
    }
}
val database = LiquibaseDatabaseFactory.create(
    driver = "org.postgresql.Driver",
    url = "jdbc:postgresql://127.0.0.1:5432/test",
    username = "root",
    password = "test",
)
val liquibaseClient = LiquibaseClient(
    changelogFile = "db.changelog.all.kts",
    database = database,
)
liquibaseClient.update()
```
### Supported ORMs for customChange

Liquibase-kotlin can integrate with Komapper, jOOQ, Exposed, and Ktorm using optional modules.

For detailed information about ORM integration, please see:
- [ORM Integration Documentation](https://momosetkn.github.io/liquibase-kotlin-docs/orm-integration.html)


## Prerequisite

- JDK 17 or later

### Test passed liquibase version
- 4.26.0
- 4.27.0
- 4.28.0
- 4.29.2
- 4.30.0
- 4.31.0
- 4.31.1
- 4.32.0
- 4.33.0

# example project
https://github.com/momosetkn/liquibase-kotlin-example
