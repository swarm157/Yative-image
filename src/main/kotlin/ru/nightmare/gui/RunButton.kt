package ru.nightmare.gui

import javax.swing.JButton
import ru.nightmare.logic.*
import java.lang.Boolean

import ru.nightmare.gui.PropertyManager.Companion.get

class RunButton : JButton("Generate") {




    init {
        //PropertyManager.subscribe(this as Object)
        addActionListener {
            val builder = Builder()
            val x = get("noFallBack")
            var xm = false
            if (x=="false")
                xm = false
            if (x=="true")
                xm = true
            val wrapper = builder
                .inDirectory(get("workDir"))
                .toFile(get("file"))
                .withImageBuilder(get("nativeImageBuilder"))
                .withImageName(get("imageName"))
                .withSourceType(SourceType.valueOf(get("mode")))
                .withMainClass(get("mainClass"))
                .withOptionsForJVM(get("jvmOptions"))
                .outMode(Mode.valueOf(get("outMode")))
                .fallBack(xm)
                .console(get("console"))
                .execute(get("execute")=="true")
                .build()
            wrapper.wrap()
        }
    }
}