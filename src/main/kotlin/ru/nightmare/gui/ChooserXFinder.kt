package ru.nightmare.gui

import ru.nightmare.Main
import java.io.IOException
import java.util.jar.Attributes

import java.util.jar.JarFile
import java.util.jar.Manifest
import javax.swing.JButton
import javax.swing.JFileChooser


class ChooserXFinder(private val field: SettingTextField, private val directories: Boolean = false) : JButton("Choose") {
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
                if (filePath.endsWith(".jar"))
                    Main.settings.mainClass.text = findMainClass(filePath)
                // Return the filePath or perform any other operations
            }
        }
    }
    private fun findMainClass(jarFilePath: String): String {
        return try {
            val jarFile = JarFile(jarFilePath)
            val manifest: Manifest = jarFile.manifest
            val attributes: Attributes = manifest.getMainAttributes()
            val mainClass: String = attributes.getValue("Main-Class")
            jarFile.close()
            if (mainClass=="")
                PropertyManager.get("mainClass")
            else
                mainClass
        } catch (e: IOException) {
            e.printStackTrace()
            ""
        }
    }
}