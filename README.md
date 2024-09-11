# liquibase-kotlin

Kotlin DSL for Liquibase

## Prerequisite

- JDK 17 or later

# How to install

```kotlin
repositories {
    // Add below to repositories. because this product is publish to jitpack.
    maven { url = URI("https://jitpack.io") }
}

dependencies {
    val liquibaseKotlinVersion = "0.3.1"
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
}
```

# example
## kotlin-script
https://github.com/momosetkn/liquibase-kotlin/blob/main/integration-test/src/main/resources/db.changelog/parser_input/db.changelog-0.kts
## kotlin-typesafe
https://github.com/momosetkn/liquibase-kotlin/blob/main/integration-test/src/main/kotlin/momosetkn/liquibase/changelogs/main/sub/TypesafeDatabaseChangelog1.kt
