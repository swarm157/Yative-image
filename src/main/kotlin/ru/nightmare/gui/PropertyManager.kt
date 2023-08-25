package ru.nightmare.gui

import ru.nightmare.logic.Mode
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileReader
import java.io.FileWriter
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.util.Properties

/***
 * Манаджер настроек
 * Данный класс является базисом всех настроек в данном приложении.
 * Первым пунктом добавления новой настройки, является обьявление ее
 * в функции генерации стандартных настроек.
 * Примечание, здесь следует размещать, только то, над чем планируется
 * в определенный момент времени будет дан котроль пользователю.
 * Здесь НЕ следует размещать литералы, только если в будущем они не окажутся
 * в опциях.
 * Для рядового пользования, следует использовать подписку.
 */

class PropertyManager {
    // Все статичный поля расположены здесь
    companion object {
        // Загружает настройки при старте приложения

        // Строка содержащая имя файла с настройками вместе с путем
        private val path = "C:\\Users\\nightmare\\AppData\\Roaming\\yative-image.properties"
        // функция генерации стандартных настроек
        private fun standardSettings(): Properties {
            // Временный обьект настроек
            val properties = Properties()
            // Все строки внутри apply применяются к properties
            // Задавать новые настройки следует здесь в формате ключ - значение
            properties.apply {
                put("nativeImageBuilder", "YOUR GRAALVM native-image.(cmd/sh)")
                put("file", "YOUR FILE")
                put("workDir", "DIRECTORY WHERE IMAGE SHOULD BE CREATED")
                put("mainClass", "Main")
                put("noFallBack", "true")
                put("jvmOptions", "")
                put("mode", "jar")
                put("outMode", "defaultExecutable")
                put("imageName", "unnamed")
                put("console", "YOUR CONSOLE")
                put("execute", "false")
                put("fileLookingLocation", "C:\\")
            }
            return properties
        }
        private val autoUpdate = ArrayList<Object>()
        // Обычный файл java, используется для всех махинаций с самим файлом настроек
        private val file = File(path)
        // Ради этого обьекта существует весь класс, он нужен для работы с настройками
        private var properties = Properties()

        /** Пользовательская функция сохранения, удобная обертка, все делает сама
         *
         */
        fun save() {
            try {
                properties.store(FileOutputStream(file), "")
            } catch (e: Exception) {
                println(e)
                //println("ERROR: cannot save $path")
            }
        }

        /** Пользовательская функция загрузки, удобная обертка, все делает сама
         * Автоматически создает недостающие папки, удаляет пустые директории с именем
         * файла настроек.
         * В конце автоматически обновляет всех подписчиков.
         * При невозможности провести загрузку, по той или иной причине, загружает
         * стандартные настройки.
         */
        fun load() {
            try {
                if (file.isDirectory) {
                    if (!file.delete()) {
                        println("ERROR: $path  is a directory with files")
                        properties = standardSettings()
                    } else {
                        properties = standardSettings()
                        properties.store(FileOutputStream(file), "remembered settings")
                    }
                } else {
                    if (file.exists()) {
                        properties.load(FileInputStream(file))
                    } else {
                        properties = standardSettings()
                        //file.mkdirs()
                        properties.store(FileOutputStream(file), "")
                    }
                }
            } catch (e: Exception) {
                println("ERROR: cannot do anything with $path \n loading default settings in read only")
                properties = standardSettings()
            }
            //update()
        }

        /** Пользовательская функция, Получить настройку
         * не рекомендуется использовать, если задачу можно выполнить подпиской
         */
        fun get(key: String): String {
            return properties.getProperty(key)
        }

        /** Пользовательская функция, Установить значение настройки
         * Используется для любого изменения настроек, автоматически
         * обновляет все подписанные классы.
         */
        fun set(key: String, value: String) {
            properties[key] = value
            //update()
        }

/*        /** Обновить все классы с подпиской.
         * Данная функция не требует ручного вызова.
         */
        private fun update() {
            for (subscriber in autoUpdate) {
                update(subscriber)
            }
        }

        /** Проводит анализ класса.
         * Ищет поля, помеченные аннотацией Subscriber.
         * Найденным выставляет значение из списка настроек.
         * НЕ обрабатывает исключения, может выдавать ошибки в рантайме.
         */
        private fun update(subscriber: Object) {
            val clas = subscriber.`class`
            for(field in clas.fields) {
                if (field.isAnnotationPresent(Subscriber::class.java)) {
                    val annotation = field.getAnnotation(Subscriber::class.java)
                    val value = get(annotation.key)
                    field.set(field.type, value)
                }
            }
        }

        /**Добавить класс в список автоматически обновляемых
         * Для успешного функционирования следует использовать
         * над переменной аннотацию Subscriber
         */
        fun subscribe(subscriber: Object) {
            autoUpdate.add(subscriber)
            update(subscriber)
        }
*/
        /** Сбросить настройки на стандартные
         * Автоматически обновляет связанные классы
         */
        fun reset() {
            properties = standardSettings()
            //update()
        }
        init {
            load()
        }
    }

}