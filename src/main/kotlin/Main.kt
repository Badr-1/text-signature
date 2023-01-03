import com.github.kinquirer.KInquirer
import com.github.kinquirer.components.promptConfirm
import com.github.kinquirer.components.promptList

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        displayExample()
        while (true) {

            val format: String = KInquirer.promptList(
                message = "What size do you need?",
                choices = listOf(
                    "Both", "Name Only"
                ),
                hint = "press Enter to pick",
            )

            print(white("Enter name and surname: "))
            val name: String
            val nameFont: Font
            val nameColor: String
            "name".apply {
                name = getText(this)
                nameFont = getFontOf(this)
                nameColor = getColor(this)
            }

            when (format) {
                "Both" -> {
                    print(white("Enter status: "))
                    val status: String
                    val statusFont: Font
                    val statusColor: String
                    "status".apply {
                        status = getText(this)
                        statusFont = getFontOf(this)
                        statusColor = getColor(this)
                    }

                    val border = getBorder()
                    display(nameFont, name, statusFont, status, nameColor, statusColor, border)
                }

                else -> {
                    val border = getBorder()
                    display(nameFont, name, nameColor = nameColor, border = border)
                }
            }


            val quit: Boolean = KInquirer.promptConfirm(message = white("Quit ?"), default = false)
            if (quit) {
                break
            }
        }
    }
}