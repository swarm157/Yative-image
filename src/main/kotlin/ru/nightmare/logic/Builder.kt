package ru.nightmare.logic

class Builder {
    private lateinit var imageName: String
    private lateinit var nativeImageBuilder: String
    private lateinit var file: String
    private lateinit var workDir: String
    private lateinit var mainClass: String
    private lateinit var console: String
    private var noFallBack = false
    private var execute = false
    private var jvmOptions = ""
    private lateinit var mode: SourceType
    private lateinit var outMode: Mode
    fun withImageBuilder(nativeImageBuilder: String): Builder {
        this.nativeImageBuilder = nativeImageBuilder
        return this
    }
    fun toFile(file: String): Builder {
        this.file = file
        return this
    }
    fun outMode(outMode: Mode): Builder {
        this.outMode = outMode
        return this
    }
    fun fallBack(noFallBack: Boolean): Builder {
        this.noFallBack = noFallBack
        return this
    }
    fun execute(execute: Boolean): Builder {
        this.execute = execute
        return this
    }
    fun withMainClass(mainClass: String): Builder {
        this.mainClass = mainClass
        return this
    }
    fun inDirectory(workDir: String): Builder {
        this.workDir = workDir
        return this
    }
    fun withOptionsForJVM(jvmOptions: String): Builder {
        this.jvmOptions = jvmOptions
        return this
    }
    fun console(console: String): Builder {
        this.console = console
        return this
    }
    fun withSourceType(mode: SourceType): Builder {
        this.mode = mode
        return this
    }
    fun withImageName(imageName: String): Builder {
        this.imageName = imageName
        return this
    }
    fun build(): Wrapper {
        return Wrapper(nativeImageBuilder, file, workDir, mainClass, noFallBack, jvmOptions, mode, outMode,console, execute, imageName)
    }
}