package ru.nightmare.gui

import ru.nightmare.logic.Mode
import java.util.*
import javax.swing.JComboBox


class SettingComboBox(val key: String, val enumValues: Array<String>) : JComboBox<String>() {


    companion object {
        fun <T : Enum<T>> enumsToStrings(inp: Array<T>): Array<String> {
            return inp.map { it.name }.toTypedArray()
        }
        /* old broken java sourced code
        fun <T> enumsToStrings(inp: Array<T>): Array<String> {
            val list = ArrayList<String>()
            val enumValues: List<Enum<*>> = ArrayList(
                EnumSet.allOf(
                    Mode::class.java
                )
            )
            EnumSet.allOf(Mode::class.java).stream().map(e -> e.name()).collect(Collectors.toList())
            for (e in inp) {
                e!!::class.java.enumConstants
            }

            val out: Array<String>
            return out
        }
         */
    }

    private var value = PropertyManager.get(key)

    init {
        //PropertyManager.subscribe(this as Object)
        for (str in enumValues) {
            addItem(str)
        }
        selectedItem = value
        addActionListener {
            PropertyManager.set(key, selectedItem as String)
        }
    }
}