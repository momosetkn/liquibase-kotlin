# liquibase-kotlin-dsl

Kotlin DSL for Liquibase

# How to install

```kotlin
repositories {
    maven { url = URI("https://jitpack.io") }
}

dependencies {
    val liquibaseKotlinDslVersion = "0.0.25"
    implementation("com.github.momosetkn.liquibase-kotlin-dsl:liquibase-kotlin-dsl:$liquibaseKotlinDslVersion")
    implementation("com.github.momosetkn.liquibase-kotlin-dsl:liquibase-kotlin-parser:$liquibaseKotlinDslVersion")
    implementation("com.github.momosetkn.liquibase-kotlin-dsl:liquibase-kotlin-serializer:$liquibaseKotlinDslVersion")
}
```

Require register serializer and parser before using liquibase.
It will be registered by the following code.

```kotlin
momosetkn.liquibase.kotlin.serializer.KotlinChangeLogSerializer.register()
momosetkn.liquibase.kotlin.parser.KotlinLiquibaseChangeLogParser.register()
```

# example

https://github.com/momosetkn/liquibase-kotlin-dsl/blob/main/integration-test/src/main/resources/db.changelog/parser_input/db.changelog-0.kts
