package ru.nightmare.logic

import ru.nightmare.Main
import ru.nightmare.logic.Mode.*
import ru.nightmare.logic.SourceType.*
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.lang.Runtime

open class Wrapper( private val nativeImageBuilder: String
                   ,private val file: String
                   ,private val workDir: String
                   ,private val mainClass: String
                   ,private val noFallBack: Boolean
                   ,private val jvmOptions: String
                   ,private val mode: SourceType
                   ,private val outMode: Mode
                   ,private val console: String
                   ,private val execute: Boolean
                   ,private val imageName: String) {
    private lateinit var process: Process

    fun wrap() {
        //val builder = ProcessBuilder()
        //if (workDir!="")
        //builder.directory(File(workDir))

        var command = ""
        if (workDir!="") {
            if (!File(workDir).exists())
                if (execute)
                    println("mkdir $workDir\n")
                else
                    command+="mkdir $workDir\n"
            if (execute)
                println("cd $workDir\n")
            else
                command+="cd $workDir\n"
        }
        command += nativeImageBuilder
        if (mainClass!=""&&mode!=clazz)
            command+= " -cp $mainClass"
        command+=" ${
            when(mode) {
                jar -> {
                    "-jar"
                }
                clazz -> {
                    "class"
                }
                module -> {
                    "-m"
                }
            }+" $file "+when(outMode) {
                static -> {
                    "--static"
                }
                defaultExecutable -> {
                    ""
                }
                library -> {
                    "--shared"
                }
            }+" "
        }"
        if(imageName!="") {
            command += if (workDir != "")
                " -o $workDir\\$imageName"
            else
                " -o $imageName"
        }
        if (noFallBack)
            command+=" --no-fallback "
        if (jvmOptions != "")
            command+=" -j $jvmOptions"
        val runtime = Runtime.getRuntime()
        //Main.settings.result.text=command

                /*builder.command(command)
        process = builder.start()*/
        if (execute) {
            process = runtime.exec(command)
            Thread {
                val inp = getIn()
                val b = BufferedReader(InputStreamReader(inp))
                //BufferedReader(InputStreamReader(getIn()))
                while (process.isAlive) {
                    println(b.readLine())
                }
            }.start()
            Thread {
                val inp = getErr()
                val b = BufferedReader(InputStreamReader(inp))
                //BufferedReader(InputStreamReader(getIn()))
                while (process.isAlive) {
                    println(b.readLine())
                }
            }.start()
        } else
            println(command)

    }
    fun getOut(): OutputStream {
        return process.outputStream
    }
    fun getIn(): InputStream {
        return process.inputStream
    }
    fun getErr(): InputStream {
        return process.errorStream
    }
}