package momosetkn.sql

class DatasourceProxy(
    private val connection: java.sql.Connection,
) : javax.sql.DataSource {
    override fun getLogWriter(): java.io.PrintWriter {
        throw UnsupportedOperationException()
    }

    override fun setLogWriter(out: java.io.PrintWriter?) {
        throw UnsupportedOperationException()
    }

    override fun setLoginTimeout(seconds: Int) {
        throw UnsupportedOperationException()
    }

    override fun getLoginTimeout(): Int {
        throw UnsupportedOperationException()
    }

    override fun getParentLogger(): java.util.logging.Logger {
        throw UnsupportedOperationException()
    }

    override fun <T : Any?> unwrap(iface: Class<T>?): T {
        throw UnsupportedOperationException()
    }

    override fun isWrapperFor(iface: Class<*>?): Boolean {
        throw UnsupportedOperationException()
    }

    @Throws(java.sql.SQLException::class)
    override fun getConnection(): java.sql.Connection {
        return connection
    }

    @Throws(java.sql.SQLException::class)
    override fun getConnection(username: String, password: String): java.sql.Connection {
        throw UnsupportedOperationException()
    }
}
