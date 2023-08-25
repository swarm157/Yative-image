package ru.nightmare.gui

import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy


@Target(AnnotationTarget.FIELD, AnnotationTarget.LOCAL_VARIABLE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
/**
 * Используй данный класс для взаимодействия с настройками.
 * Загружает по ключу значение из списка настроек.
 * Автоматически обновляет переменную с изменением настройки.
 * Чтобы аннотация работала, следует подписать класс у PropertyManager.
 */
annotation class Subscriber(val key: String)