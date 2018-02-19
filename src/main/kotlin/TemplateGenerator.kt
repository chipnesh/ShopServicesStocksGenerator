object TemplateGenerator {
    private val shopRegex = "S\\d\\d\\d".toRegex()

    fun generateTemplate(
            date: String,
            shop: String,
            stocks: List<String>,
            skuList: List<String>
    ): XmlTemplate {
        if (!shop.matches(shopRegex)) error("Wrong shop number '$shop'. Must match S\\d\\d\\d regex.")
        if (stocks.size != 3) error("Stock count for shop '$shop' should be 3 but ${stocks.size}")
        if (skuList.isEmpty()) error("Sku list is empty")

        return XmlTemplate(
                shop,
                Header(
                        date,
                        listOf(
                                Param("objectIds", shop),
                                Param("threshold", "0"),
                                Param("stockParams", "")
                        )
                ),
                stocks.mapIndexed { stockIndex, stockId ->
                    Stock(
                            stockId,
                            shop,
                            listOf(
                                    StockParam("stockCondition", "inStorage"),
                                    StockParam("stockLocation", calculateLocation(shop, stockIndex))
                            ),
                            skuList.map { sku ->
                                StockLevel(
                                        sku,
                                        "%date%",
                                        calculateStockQty(shop, stockIndex)
                                )
                            }
                    )
                }
        )
    }

    private fun calculateLocation(shop: String, stockIndex: Int) = when (stockIndex) {
        0 -> "Warehouse"
        1 -> "TradeZone"
        2 -> "all"
        else -> error("Stock count for $shop is greater then 3")
    }

    private fun calculateStockQty(shop: String, stockIndex: Int) = when (stockIndex) {
        in 0..1 -> 1
        2 -> 2
        else -> error("Stock count for $shop is greater then 3")
    }
}