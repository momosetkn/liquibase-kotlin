package momosetkn.liquibase.client

import org.slf4j.LoggerFactory
import java.io.StringWriter
import java.io.Writer

class LiquibaseMultilineLogWriter : Writer() {
    private val writer: StringWriter = StringWriter()
    private val log = LoggerFactory.getLogger(LiquibaseMultilineLogWriter::class.java)

    override fun close() {
        flush()
        writer.close()
    }

    override fun flush() {
        writer.flush()
        val content = writer.toString()
        if (content.isNotBlank()) {
            log.info(content)
            writer.buffer.setLength(0)
        }
    }

    override fun write(cbuf: CharArray, off: Int, len: Int) {
        writer.write(cbuf, off, len)
    }
}
