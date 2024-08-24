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
