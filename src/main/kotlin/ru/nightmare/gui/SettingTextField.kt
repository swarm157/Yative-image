package ru.nightmare.gui

import java.awt.event.FocusEvent
import java.awt.event.FocusListener
import java.util.EventListener
import javax.swing.JTextField
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

class SettingTextField(length: Int, val key: String) : JTextField(length) {
    init {
        text = PropertyManager.get(key)
        this.document.addDocumentListener(Listener(this))
    }
}

class Listener(var s: SettingTextField) : DocumentListener {
    override fun insertUpdate(e: DocumentEvent?) {
        PropertyManager.set(s.key, s.text)
    }

    override fun removeUpdate(e: DocumentEvent?) {
        PropertyManager.set(s.key, s.text)
    }

    override fun changedUpdate(e: DocumentEvent?) {
        PropertyManager.set(s.key, s.text)
    }
}