package momosetkn.liquibase.kotlin.parser

/**
 * import on script-parser.
 * when exists `script-parser` module, It will be loaded with ServiceLoader in `script-parser`.
 */
interface KotlinScriptImports {
    fun imports(): List<String>
}
