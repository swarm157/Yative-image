package ru.nightmare.gui

import ru.nightmare.gui.PropertyManager.Companion.get
import ru.nightmare.gui.PropertyManager.Companion.set
import java.lang.Boolean
import javax.swing.JCheckBox
import javax.swing.event.ChangeEvent
import kotlin.String


/**
 * Данный класс следует использовать в меню настроек.
 * Автоматически синхронизирует изменения по всему приложению.
 */
class SettingBox(key: String?, text: String?) : JCheckBox() {
    /**
     *
     * @param key это ключ настройки, существующие см в PropertiesManager.
     * Изменения будут синхронизироваться с указанным ключом
     * @param text Отображаемая надпись рядом с чек боксом, подпись, надпись.
     */
    init {
        isSelected = Boolean.parseBoolean(get(key!!))
        addChangeListener { changeEvent: ChangeEvent? ->
            set(
                key,
                isSelected.toString()
            )

        }
        setText(text)
    }
}