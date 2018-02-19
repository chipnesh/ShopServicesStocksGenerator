import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicInteger

class ApplicationState {
    private var startTime: Long = 0
    private var totalCount: AtomicInteger = AtomicInteger(0)
    private val errors: CopyOnWriteArrayList<String> = CopyOnWriteArrayList()

    fun totalCount() = totalCount.get()
    fun successCount() = totalCount() - failedCount()
    fun failedCount() = errors.size

    val failed: Boolean
        get() = failedCount() > 0
    val success: Boolean
        get() = !failed

    fun addError(error: Throwable): Boolean {
        return errors.add(error.localizedMessage)
    }

    fun start(templateCount: Int) {
        startTime = System.currentTimeMillis()
        totalCount.set(templateCount)
    }

    fun complete() {
        val time = System.currentTimeMillis() - startTime
        val status = if (failed) "failed" else "success"

        println("Template generation $status in $time milliseconds.")
        println("Total count: ${totalCount()}")
        println("Success count: ${successCount()}")
        println("Failed count: ${failedCount()}")

        if (failed) {
            println("Error list:")
            errors.forEachIndexed { index, error -> println("${index + 1} - $error") }
        }
    }
}