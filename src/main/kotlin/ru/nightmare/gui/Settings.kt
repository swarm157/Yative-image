package ru.nightmare.gui

import ru.nightmare.logic.Mode
import ru.nightmare.logic.SourceType
import sun.awt.WindowClosingListener
import java.awt.*
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.awt.event.WindowListener
import java.awt.event.WindowStateListener
import java.lang.RuntimeException
import java.nio.file.FileSystem
import javax.swing.JButton
import javax.swing.JComboBox
import javax.swing.JFileChooser
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTextArea
import javax.swing.ScrollPaneConstants
import javax.swing.plaf.FileChooserUI

// Класс ответственный за меню настроек
class Settings() : JFrame() {
    // Главная панель обернутая в объект ниже
    private val panel = JPanel()


    private val locations = JPanel()



    private val options = JPanel()
    private val extra = JPanel()
    private val fallBack = SettingBox("noFallBack", "Принудительно создавать image независимый от jvm")
    private val execute = SettingBox("execute", "Исполнить cгенерированную команду")
    private val length = 5
    private val file = SettingTextField(length, "file")
    private val nativeImageBuilder = SettingTextField(length, "nativeImageBuilder")
    private val workDir = SettingTextField(length, "workDir")
    val mainClass = SettingTextField(length, "mainClass")
    private val jvmOptions = SettingTextField(length, "jvmOptions")
    private val imageName = SettingTextField(length, "imageName")
    private val console = SettingTextField(length, "console")
    private val fileLookingLocation = SettingTextField(length, "fileLookingLocation")
    private val mode = SettingComboBox("mode", SettingComboBox.enumsToStrings(SourceType.values()))
    private val outMode = SettingComboBox("outMode",  SettingComboBox.enumsToStrings(Mode.values()))

    private val modes = JPanel()


    val result = JTextArea()
    private val runner = JPanel()
    private val runButton = RunButton()


    // Отвечает за прокрутку
    private val scroll = JScrollPane(panel)
    private val resultScroll = JScrollPane(result)
    // Статические обьекты
    companion object {
        // Размер окна настроек
        val WINDOW_SIZE = Dimension(400, 600)
        // Главный экземпляр класса PropertyManager
        val PM = PropertyManager()
    }
    init {
        // Временные переменные
        val tk = Toolkit.getDefaultToolkit()
        val sz = tk.screenSize
        title = "Yative-image"
        defaultCloseOperation = EXIT_ON_CLOSE
        size = WINDOW_SIZE
        // Расположение окна
        location = Point((sz.width- WINDOW_SIZE.width)/2, (sz.height- WINDOW_SIZE.height)/2)
        // Блокировка изменения размера окна
        isResizable = false
        isVisible = true
        add(scroll)
        scroll.apply {
            // Задается политика прокрутки
            horizontalScrollBarPolicy = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
            verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS
        }
        resultScroll.apply {
            // Задается политика прокрутки
            horizontalScrollBarPolicy = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS
            verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS
        }
        panel.apply {
            // Установка лэйоута, значения на текущий момент временные
            layout = GridLayout(5, 1, 20, 15)
            add(locations)
            add(options)
            add(modes)
            add(runner)
            add(extra)
            background = Color.LIGHT_GRAY
        }
        //JFileChooser().selectedFile
        locations.apply {
            layout = GridLayout(5, 2, 5, 5)
            add(JLabel("Файловая система"))
            add(JLabel(""))

            add(DoubleColumnPanel(nativeImageBuilder, SettingChooser(nativeImageBuilder)))
            add(JLabel("native-image builder"))
            add(imageName)
            add(JLabel("имя выходного файла"))
            add(DoubleColumnPanel(file, ChooserXFinder(file)))
            add(JLabel("исходный файл"))
            add(DoubleColumnPanel(workDir, SettingChooser(workDir, true)))
            add(JLabel("директория для image"))
        }

        options.apply {
            layout = GridLayout(8, 1, 5, 5)
            add(JLabel("Опции"))
            add(fallBack)
            add(JLabel("JVM опции для сборщика"))
            add(jvmOptions)
            add(JLabel("Главный класс"))
            add(mainClass)
            add(execute)
        }
        modes.apply {
            layout = GridLayout(5, 2, 5, 5)
            add(JLabel("Режимы"))
            add(JLabel(""))
            add(mode)
            add(JLabel("тип исходных данных"))
            add(outMode)
            add(JLabel("режим сборки image"))
        }
        runner.apply {
            layout = GridLayout(3, 1, 5, 5)
            //add(JLabel("Запуск/Генерировать"))
            //add(JLabel(""))
            add(DoubleColumnPanel(console, ChooserXFinder(console)))
            add(runButton)
            //add(JLabel("Generate command"))
            //add(JLabel("Result"))
            add(resultScroll)
        }
        extra.apply {
            layout = GridLayout(4, 2, 5, 5)
            add(JLabel("Где стартовать путевыбиралку"))
            add(DoubleColumnPanel(fileLookingLocation, SettingChooser(fileLookingLocation)))

        }

        /*bind.addActionListener {
            bind.text = "Бинд робота ..."
            bindListen = true
        }
        bind.addKeyListener(KeyBindListener(this))*/
        // Всегда поверх других окон
        addWindowListener(WS())
    }

    class WS : WindowAdapter() {
        override fun windowClosing(e: WindowEvent?) {
            PropertyManager.save()
        }
    }

}