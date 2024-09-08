# liquibase-kotlin-dsl

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
    val liquibaseKotlinDslVersion = "0.2.0"
    // You can choose to install either kotlin-script or kotlin-typesafe.
    // for kotlin-script
    implementation("com.github.momosetkn.liquibase-kotlin-dsl:liquibase-kotlin-dsl:$liquibaseKotlinDslVersion")
    implementation("com.github.momosetkn.liquibase-kotlin-dsl:liquibase-kotlin-script-parser:$liquibaseKotlinDslVersion")
    implementation("com.github.momosetkn.liquibase-kotlin-dsl:liquibase-kotlin-script-serializer:$liquibaseKotlinDslVersion")
    // for kotlin-typesafe
    implementation("com.github.momosetkn.liquibase-kotlin-dsl:liquibase-kotlin-dsl:$liquibaseKotlinDslVersion")
    implementation("com.github.momosetkn.liquibase-kotlin-dsl:liquibase-kotlin-typesafe-parser:$liquibaseKotlinDslVersion")
    implementation("com.github.momosetkn.liquibase-kotlin-dsl:liquibase-kotlin-typesafe-serializer:$liquibaseKotlinDslVersion")
}
```

# example
## kotlin-script
https://github.com/momosetkn/liquibase-kotlin-dsl/blob/main/integration-test/src/main/resources/db.changelog/parser_input/db.changelog-0.kts
## kotlin-typesafe
https://github.com/momosetkn/liquibase-kotlin-dsl/blob/main/integration-test/src/main/kotlin/momosetkn/liquibase/changelogs/main/sub/TypesafeDatabaseChangelog1.kt
