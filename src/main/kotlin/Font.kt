import java.io.BufferedReader
import java.io.InputStreamReader


class Font(name: String, private val spaceWidth: Int) {
    private val smallLetters: MutableList<Letter> = mutableListOf()
    private val capitalLetters: MutableList<Letter> = mutableListOf()
    private val lettersHeight: Int
    private val numberOfLetters: Int

    init {
        javaClass.getResourceAsStream("/fonts/$name")
            .use { `in` ->
                BufferedReader(InputStreamReader(`in`!!)).use { reader ->
                    val data = reader.readLines().toMutableList()

                    // first line:
                    // number of lines : number of letters in that font
                    // ex: 10 52
                    // each letter:
                    // letter : width of that letter
                    // ex: a 10
                    // then the letter

                    lettersHeight = data.first().split(' ').first().toInt() // number of lines
                    numberOfLetters = data.first().split(' ').last().toInt() // number of Letters
                    data.removeFirst() // delete read data
                    while (data.isNotEmpty()) {
                        val letter = data.first().split(' ').first().toString().first()
                        val width = data.first().split(' ').last().toInt()
                        val letterLines = mutableListOf<String>()
                        data.removeFirst() // delete read data

                        for (i in 1..lettersHeight) {
                            letterLines.add(data.removeFirst())
                        }

                        if (letter in 'a'..'z') {

                            smallLetters.add(Letter(letter, width, letterLines))
                        } else {
                            capitalLetters.add(Letter(letter, width, letterLines))
                        }


                    }
                }
            }

    }

    fun write(word: String): Word {
        var wordWidth = 0
        val lettersWithFont = mutableListOf<Letter>()
        word.forEach { char ->
            when (char) {
                in 'a'..'z' -> {
                    lettersWithFont.addAll(
                        smallLetters.filter {
                            it.value == char
                        }
                    )
                    wordWidth += lettersWithFont.last().width
                }

                in 'A'..'Z' -> {
                    lettersWithFont.addAll(
                        capitalLetters.filter {
                            it.value == char
                        }
                    )
                    wordWidth += lettersWithFont.last().width
                }

                else -> { // space
                    val spaceLetter = Letter(' ', spaceWidth, MutableList(lettersHeight) { " ".repeat(spaceWidth) })
                    lettersWithFont.add(spaceLetter)
                    wordWidth += spaceWidth
                }
            }
        }
        val line = mutableListOf<String>()

        for (i in 0 until lettersHeight) {
            var l = ""
            lettersWithFont.forEach { letter ->
                l += letter.lines[i]
            }
            line.add(l)
        }
        return Word(line, wordWidth)
    }

    class Letter(val value: Char, val width: Int, val lines: MutableList<String>)
    class Word(val value: MutableList<String>, val width: Int)

}


