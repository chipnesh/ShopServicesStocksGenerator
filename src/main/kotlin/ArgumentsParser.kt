// default argument values
private const val defaultShopFile = "id2shop.csv"
private const val defaultSkuFile = "sku.csv"
private const val defaultPath = "./data/"

// application arguments
private const val shopArgumentName = "shop"
private const val skuArgumentName = "sku"
private const val pathArgumentName = "path"

object ArgumentsParser {

    fun parse(commandLineArguments: Array<String>): Arguments {
        val argumentMap = readArguments(commandLineArguments).withDefault { arg ->
            when (arg) {
                shopArgumentName -> defaultShopFile
                skuArgumentName -> defaultSkuFile
                pathArgumentName -> defaultPath
                else -> error("Argument $arg is not supported.")
            }
        }
        return Arguments(argumentMap)
    }

    private fun readArguments(args: Array<String>): Map<String, String> {
        val params = mutableMapOf<String, String>()
        args.forEachIndexed { index, param ->
            if (param.startsWith("-")) {
                if (args.size >= index + 1)
                    params[param.substring(1).toLowerCase()] = args[index + 1]
            }
        }
        return params
    }
}

data class Arguments(private val map: Map<String, String>) {
    val shop: String by map
    val sku: String by map
    val path: String by map
}