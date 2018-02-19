import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import java.time.OffsetDateTime.now
import java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME

fun main(args: Array<String>) = runBlocking {
    val state = ApplicationState()

    val arguments = ArgumentsParser.parse(args)
    val shops = FileOperations.readShops(arguments.shop)
    val skuList = FileOperations.readSkuList(arguments.sku)
    val now = now().format(ISO_OFFSET_DATE_TIME)

    state.start(shops.size)

    shops.map { (shop, stocks) ->
        generateTemplate(now, shop, stocks, skuList, arguments, state)
    }.forEach { it.await() }

    state.complete()
}

private fun generateTemplate(
        date: String,
        shop: String,
        stocks: List<String>,
        skuList: List<String>,
        arguments: Arguments,
        state: ApplicationState
) = async {
    try {
        val xmlTemplate = TemplateGenerator.generateTemplate(date, shop, stocks, skuList)
        val templateFile = FileOperations.createTemplateFile(xmlTemplate, arguments.path)
        Marshaller.marshall(xmlTemplate, templateFile)
    } catch (e: IllegalStateException) {
        state.addError(e)
    }
}