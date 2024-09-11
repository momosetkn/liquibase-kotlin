# liquibase-kotlin

Modules for using Liquibase with Kotlin

## Features

### Kotlin DSL

You can choose between KotlinScript and Kotlin (not script).
Both have the same syntax for changeSet.

> [!NOTE]
> kotlin-script module is It is can integration with existing SQL, XML, YAML, and JSON.
> kotlin-typesafe module It is cannot integration with existing SQL, XML, YAML, and JSON.

kotlin-typesafe is read KotlinTypesafeDatabaseChangeLog class. don't use file-system. changelog file is specify the ".kt" extension.
include/includeAll change of kotlin-typesafe, it cannot use other than changeLog of ".kt" extension.

kotlin-script example
https://github.com/momosetkn/liquibase-kotlin-example/blob/main/liquibase-kotlin-script-example/src/main/kotlin/Main.kt

kotlin-typesafe example
https://github.com/momosetkn/liquibase-kotlin-example/blob/main/liquibase-kotlin-typesafe-example/src/main/kotlin/Main.kt

### Kotlin client

Client module can execute Liquibase commands programmatically.

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
}
```

# example project
https://github.com/momosetkn/liquibase-kotlin-example
