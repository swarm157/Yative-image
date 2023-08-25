package ru.nightmare

import ru.nightmare.Main.Companion.settings
import ru.nightmare.gui.JTextAreaOutputStream
import ru.nightmare.gui.Settings
import java.awt.Point
import java.io.PrintStream
import javax.swing.JFrame

class Main {
    companion object {
        lateinit var settings: Settings
    }
}

fun main(args: Array<String>) {
    settings = Settings()
    System.setOut(PrintStream(JTextAreaOutputStream(settings.result)))
}