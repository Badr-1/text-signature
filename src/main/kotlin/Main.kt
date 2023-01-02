object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        displayExample()
        while (true) {
            println(white("Enter Empty Name To Exit."))
            print(white("Enter name and surname: "))
            val name = readln()
            if (name.isEmpty()) {
                break
            }
            print(white("Enter status: "))
            val status = readln()

            val nameFont = getFontOf("name")
            val nameColor = getColor()
            var statusFont: Font = medium
            var statusColor: String = WHITE
            if (status.isNotEmpty()) {
                statusFont = getFontOf("status")
                statusColor = getColor()
            }
            val border = getBorder()

            display(nameFont, name, statusFont, status, nameColor, statusColor, border)
        }
    }
}