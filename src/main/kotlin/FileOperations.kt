import java.io.File

object FileOperations {

    fun createTemplateFile(template: XmlTemplate, targetPath: String): File {
        val fileName = "example_${template.shop.substring(1)}.xml"
        File(targetPath).mkdir()
        return File("$targetPath/$fileName")
    }

    fun readSkuList(skuFileName: String): List<String> {
        val skuList = File(skuFileName).readLines()
        return skuList.map { it.trim() }
    }

    fun readShops(shopFileName: String): Map<String, List<String>> {
        val shops = mutableMapOf<String, MutableList<String>>()
        File(shopFileName).forEachLine { stockToShop ->
            val (stock, shop) = stockToShop.split(";").map { it.trim() }
            if (shops.containsKey(shop)) {
                shops[shop]?.add(stock)
            } else {
                shops[shop] = mutableListOf(stock)
            }
        }
        return shops
    }
}