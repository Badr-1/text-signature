import com.github.ajalt.mordant.rendering.TextColors.*
import com.github.ajalt.mordant.rendering.TextColors.Companion.rgb
import java.io.File
import java.nio.file.Paths
import kotlin.math.roundToInt

val path = Paths.get("").toAbsolutePath().toString()
val separator: String = File.separator
val pathToSrc =
    "${path}${separator}src${separator}main${separator}resources${separator}fonts${separator}"
val roman = Font("${pathToSrc}roman.txt", 10)
val medium = Font("${pathToSrc}medium.txt", 5)

const val WHITE = "#ffffff"
fun getFontOf(what: String): Font {
    val font: Font
    while (true) {
        print(white("$what Font ((r) roman/ (m) medium) (Optional): "))
        val choice = readln()
        if (choice.isEmpty()) {
            font = when (what) {
                "name" -> roman
                else -> medium
            }
            break
        }
        when (choice.first()) {
            'm' -> {
                font = medium
                break
            }

            'r' -> {
                font = roman
                break
            }

            else -> {
                println(red("Invalid choice!"))
            }
        }
    }
    return font
}

fun getColor(): String {
    var color: String
    while (true) {
        print(white("Color Hex (Optional): "))
        color = readln()
        if (color.isEmpty()) {
            return WHITE // default
        } else {
            if (color.length == 3 || color.length == 6) {
                color = color[0].toString().repeat(2) + color[1].toString().repeat(2) + color[2].toString().repeat(2)
                break
            } else {
                println(red("Invalid format!"))
            }
        }
    }
    return color
}

fun getBorder(): String {
    var border: String
    while (true) {
        print(white("Border one character (Optional): "))
        border = readln()
        if (border.isEmpty()) {
            return "8" // default
        } else {
            if (border.length == 1) {
                break
            } else {
                println(red("Invalid format!"))
            }
        }
    }
    return border
}



fun display(
    nameFont: Font,
    name: String,
    statusFont: Font,
    status: String,
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
        if (statusWithFont.width != 0) {
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

