import com.github.ajalt.mordant.rendering.TextColors.*
import com.github.ajalt.mordant.rendering.TextColors.Companion.rgb
import com.github.kinquirer.KInquirer
import com.github.kinquirer.components.ListViewOptions
import com.github.kinquirer.components.promptInput
import com.github.kinquirer.components.promptList

import kotlin.math.roundToInt

val roman = Font("roman.txt", 10)
val medium = Font("medium.txt", 5)

const val WHITE = "#ffffff"

val fontsOptions = listOf("roman", "medium")

fun getText(what: String): String {
    do {
        val text: String = KInquirer.promptInput(message = "Enter $what: ")
        if (text.isNotEmpty()) {
            return text
        } else {
            println(red("Field Required!"))
        }

    } while (true)
}

fun getFontOf(what: String): Font {

    val fontName = KInquirer.promptList(
        message = "$what Font?",
        choices = if (what == "name") fontsOptions else fontsOptions.reversed(),
        hint = "press Enter to pick",
        viewOptions = ListViewOptions(
            questionMarkPrefix = "🎨",
            cursor = " 🖌 ",
            nonCursor = "    ",
        )
    )
    val font: Font = when (fontName) {
        "roman" -> roman
        else -> medium
    }

    return font
}

fun getColor(what: String): String {
    do {
        var color: String = KInquirer.promptInput(message = "$what Color Hex (Optional): ")
        if (color.isEmpty()) {
            return WHITE // default
        } else {
            color = color.removePrefix("#")
            if (color.matches(Regex("^#?([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})\$"))) {
                color = color[0].toString().repeat(2) + color[1].toString().repeat(2) + color[2].toString().repeat(2)
                return color
            } else {
                println(red("Invalid format!"))
            }
        }
    } while (true)
}

fun getBorder(): String {

    do {
        val border: String = KInquirer.promptInput(message = "Border one character (Optional): ")
        if (border.isEmpty()) {
            return "8" // default
        } else {
            if (border.length == 1) {
                return border
            } else {
                println(red("Invalid format!"))
            }
        }
    } while (true)
}


fun display(
    nameFont: Font,
    name: String,
    statusFont: Font = medium,
    status: String = "",
    nameColor: String = WHITE,
    statusColor: String = WHITE,
    border: String = "8",
    padding: Int = 4
) {
    val nameWithFont = nameFont.write(name)
    val statusWithFont = statusFont.write(status)


    // puts 2 spaces one the right and three on the left of the widest word
    val lineWidth: Int = if (nameWithFont.width > statusWithFont.width) {
        nameWithFont.width + padding
    } else {
        statusWithFont.width + padding
    }

    println(white(border.repeat(2) + border.repeat(lineWidth) + border.repeat(2)))
    if (nameWithFont.width > statusWithFont.width) {
        nameWithFont.value.forEach {
            println("${white(border.repeat(2))}  ${rgb(nameColor)(it)}  ${white(border.repeat(2))}")
        }
        if (statusWithFont.width != 0) {
            statusWithFont.value.forEach {
                println(
                    "${white(border.repeat(2))}${" ".repeat((lineWidth - statusWithFont.width) / 2)}${
                        rgb(statusColor)(
                            it
                        )
                    }${
                        " ".repeat(
                            ((lineWidth - statusWithFont.width) / 2.0).roundToInt()
                        )
                    }${
                        white(border.repeat(2))
                    }"
                )
            }
        }
    } else {
        nameWithFont.value.forEach {
            println(
                "${white(border.repeat(2))}${" ".repeat((lineWidth - nameWithFont.width) / 2)}${rgb(nameColor)(it)}${
                    " ".repeat(
                        ((lineWidth - nameWithFont.width) / 2.0).roundToInt()
                    )
                }${
                    white(border.repeat(2))
                }"
            )
        }
        if (status.isNotEmpty()) {
            statusWithFont.value.forEach {
                println("${white(border.repeat(2))}  ${rgb(statusColor)(it)}  ${white(border.repeat(2))}")
            }
        }
    }
    println(white(border.repeat(2) + border.repeat(lineWidth) + border.repeat(2)))
}

fun white(text: String) = rgb(WHITE)(text)
fun displayExample() {
    println(white("Default Display"))
    display(roman, "Default", medium, "Display")
}

