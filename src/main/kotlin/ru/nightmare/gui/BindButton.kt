package ru.nightmare.gui

import ru.nightmare.gui.PropertyManager.Companion.get
import ru.nightmare.gui.PropertyManager.Companion.set
import java.awt.event.ActionEvent
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.JButton


/**
 * Данный класс, это специализированная для задавания хоткеев версия
 * обычной кнопки, работа с данной кнопкой упрощена до предела.
 * Нужно задать статичный текст и настройку из списка предоставляемых PropertyManager.
 * Данное расширение самостоятельно: загружает, отслеживает и сохраняет изменения.
 * Все, что вы делаете, дает эффект незамедлительно.
 */
class BindButton(
    private val text: String, // Имя настройки
    private val key: String
) : JButton() {
    // Хранит состояние кнопки, при активном состоянии, слушает нажатия, в противном случае игнорирует
    private var active = false

    // Текст, появляющийся при  ожидании какого-либо ввода
    private val awaitingText = "..."

    /**
     * Стандартый конструктор. Готовит кнопку к использованию.
     * @param text статическая часть текста на кнопке, надпись.
     * @param key значение с которым кнопка будет работать
     * данный экземпляр кнопки, доступные смотри в PropertyManager
     */
    init {
        setNormalText()
        addKeyListener(KeyBindListener())
        addActionListener { ActionEvent: ActionEvent? ->
            active = true
            setAwaitingText()
        }
    }

    /**
     * Задает текст кнопки в рядовом состоянии.
     * Применяется, при первоначальном создании и обновлении хоткея.
     */
    private fun setNormalText() {
        setText(text + " " + get(key))
    }

    /**
     * Задает текст кнопки в активном состоянии.
     * Подчеркивает, ожидается ввод.
     */
    private fun setAwaitingText() {
        setText("$text $awaitingText")
    }

    /**
     * Слушает нажатия. Занимается обработкой
     * результата ввода, задает новый текст.
     * Вводит кнопку в неактивное состояние.
     * Реагирует, только в активном состоянии.
     */
    private inner class KeyBindListener : KeyAdapter() {
        override fun keyTyped(e: KeyEvent) {
            super.keyTyped(e)
            if (e != null && active) {
                set(key, e.keyChar.toString())
                setNormalText()
                active = false
            }
        }
    }
}