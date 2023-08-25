package ru.nightmare.gui

import javax.swing.JButton
import javax.swing.JFileChooser


class SettingChooser(private val field: SettingTextField, private val directories: Boolean = false) : JButton("Choose") {
    init {
        addActionListener {
            val fileChooser = JFileChooser(PropertyManager.get("fileLookingLocation"))
            fileChooser.dialogTitle = field.key;
            if (directories)
                fileChooser.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY;
            fileChooser.dialogType = JFileChooser.OPEN_DIALOG;
            val result = fileChooser.showOpenDialog(null)
            if (result == JFileChooser.APPROVE_OPTION) {
                val selectedFile = fileChooser.selectedFile
                val filePath = selectedFile.absolutePath
                field.text = filePath
                // Return the filePath or perform any other operations
            }
        }
    }
}