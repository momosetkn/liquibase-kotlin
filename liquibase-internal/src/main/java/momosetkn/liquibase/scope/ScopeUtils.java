package momosetkn.liquibase.scope;

import liquibase.ChecksumVersion;
import liquibase.Scope;
import liquibase.SingletonScopeManager;
import liquibase.configuration.LiquibaseConfiguration;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.logging.LogService;
import liquibase.logging.core.JavaLogService;
import liquibase.logging.core.LogServiceFactory;
import liquibase.osgi.ContainerChecker;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.servicelocator.ServiceLocator;
import liquibase.ui.ConsoleUIService;
import liquibase.util.SmartMap;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class ScopeUtils {
    private static final Field valuesField;
    private static final Constructor<Scope> scopeConstructor;

    static {
        try {
            valuesField = Scope.class.getDeclaredField("values");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        valuesField.setAccessible(true);

        try {
            scopeConstructor = Scope.class.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        scopeConstructor.setAccessible(true);
    }

    /**
     * Set scope by specify customServiceLocator
     * <p>
     * alternative {@link liquibase.Scope#getCurrentScope}
     * for inject {@link CustomServiceLocator}
     *
     * @param customServiceLocator specify customServiceLocator
     */
    public static Scope createCustomRootScope(
            ServiceLocator customServiceLocator
    ) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        var scopeManager = new NonProtectedSingletonScopeManager();
        Scope.setScopeManager(scopeManager);
        if (scopeManager.getCurrentScope() == null) {
            Scope rootScope = createScope();
            scopeManager.setCurrentScopeNonProtected(rootScope);

            SmartMap values = getSmartMap(rootScope);

            values.put(Scope.Attr.logService.name(), new JavaLogService());
            values.put(Scope.Attr.serviceLocator.name(), customServiceLocator);
            values.put(Scope.Attr.resourceAccessor.name(), new ClassLoaderResourceAccessor());
            values.put(Scope.Attr.latestChecksumVersion.name(), ChecksumVersion.V9);
            values.put(Scope.Attr.checksumVersion.name(), ChecksumVersion.latest());

            values.put(Scope.Attr.ui.name(), new ConsoleUIService());
            rootScope.getSingleton(LiquibaseConfiguration.class).init(rootScope);

            LogService overrideLogService = rootScope.getSingleton(LogServiceFactory.class).getDefaultLogService();
            if (overrideLogService == null) {
                throw new UnexpectedLiquibaseException("Cannot find default log service");
            }
            values.put(Scope.Attr.logService.name(), overrideLogService);

            //check for higher-priority serviceLocator
            ServiceLocator serviceLocator = rootScope.getServiceLocator();
            for (ServiceLocator possibleLocator : serviceLocator.findInstances(ServiceLocator.class)) {
                if (possibleLocator.getPriority() > serviceLocator.getPriority()) {
                    serviceLocator = possibleLocator;
                }
            }

            values.put(Scope.Attr.serviceLocator.name(), serviceLocator);
            values.put(Scope.Attr.osgiPlatform.name(), ContainerChecker.isOsgiPlatform());
        }
        return scopeManager.getCurrentScope();
    }

    private static SmartMap getSmartMap(Scope rootScope) throws IllegalAccessException {
        var values = (SmartMap) valuesField.get(rootScope);
        return values;
    }

    private static Scope createScope() throws InstantiationException, IllegalAccessException, InvocationTargetException {
        var rootScope = scopeConstructor.newInstance();
        return rootScope;
    }

    private static class NonProtectedSingletonScopeManager extends SingletonScopeManager {
        protected synchronized void setCurrentScopeNonProtected(Scope scope) {
            this.setCurrentScope(scope);
        }
    }
}
