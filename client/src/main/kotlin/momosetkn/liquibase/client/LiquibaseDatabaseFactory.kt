package momosetkn.liquibase.client

import liquibase.Scope
import liquibase.database.Database
import liquibase.database.DatabaseFactory
import liquibase.resource.ResourceAccessor
import kotlin.reflect.KClass

object LiquibaseDatabaseFactory {
    fun create(
        url: String,
        username: String,
        password: String,
        driver: String,
        databaseClass: KClass<out Database>? = null,
        driverPropertiesFile: String? = null,
        propertyProviderClass: String? = null,
        resourceAccessor: ResourceAccessor = Scope.getCurrentScope().resourceAccessor,
        originalDatabaseFactory: DatabaseFactory = DatabaseFactory.getInstance(),
    ): Database {
        val liquibaseConn = originalDatabaseFactory.openConnection(
            url,
            username,
            password,
            driver,
            databaseClass?.qualifiedName,
            driverPropertiesFile,
            propertyProviderClass,
            resourceAccessor,
        )
        return originalDatabaseFactory.findCorrectDatabaseImplementation(liquibaseConn)
    }
}
