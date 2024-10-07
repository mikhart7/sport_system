package ru.emkn.kotlin.sms

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
//функция, возращающая пути к заявочным спискам по пути к папке со списками
fun allCSVFilesInDirections(filepath: String): MutableList<String> {
    val resourcesPath = Paths.get(filepath)
    val paths = Files.walk(resourcesPath, 1)
        .filter { item -> Files.isRegularFile(item) }
        .filter { item -> item.toString().endsWith(".csv") }
        .map { it.toString() }
        .toList()
    return paths
}
//функция для чтения таблиц
fun openCSV(filepath: String): List<List<String>> {
    return File(filepath).readLines().map { it.split(",") }
}