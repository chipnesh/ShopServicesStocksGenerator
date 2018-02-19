import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.XmlAccessType.FIELD

annotation class NoArg

@NoArg
@XmlAccessorType(FIELD)
@XmlRootElement(name = "export")
data class XmlTemplate(
        @field:XmlTransient var shop: String,
        @field:XmlElement var header: Header,
        @field:XmlElementWrapper
        @field:XmlElement(name = "stock")
        var body: List<Stock> = listOf()
)

@NoArg
@XmlAccessorType(FIELD)
data class Header(
        @field:XmlElement var exportDate: String,
        @field:XmlElementWrapper
        @field:XmlElement(name = "param")
        var params: List<Param> = listOf()
)

@NoArg
@XmlAccessorType(FIELD)
data class Stock(
        @field:XmlElement var id: String,
        @field:XmlElement var objectId: String,

        @field:XmlElementWrapper
        @field:XmlElement(name= "stockParam")
        var stockParams: List<StockParam> = listOf(),

        @field:XmlElementWrapper
        @field:XmlElement(name = "stockLevel")
        var stockLevels: List<StockLevel> = listOf()
)

@NoArg
@XmlAccessorType(FIELD)
data class Param(
        @field:XmlElement var key: String,
        @field:XmlElement(nillable = true) var value: String
)

@NoArg
@XmlAccessorType(FIELD)
data class StockParam(
        @field:XmlElement var key: String,
        @field:XmlElement var value: String
)

@NoArg
@XmlAccessorType(FIELD)
data class StockLevel(
        @field:XmlElement var materialNumber: String,
        @field:XmlElement var stockDate: String,
        @field:XmlElement var qty: Int = 0
)
