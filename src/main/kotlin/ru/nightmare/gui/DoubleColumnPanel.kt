package ru.nightmare.gui

import java.awt.Component
import java.awt.GridLayout
import javax.swing.JPanel

class DoubleColumnPanel(private vararg val component: Component) : JPanel() {
    init {
        layout = GridLayout(1, 2, 20, 15)
        for (c in component) {
            add(c)
        }
    }
}