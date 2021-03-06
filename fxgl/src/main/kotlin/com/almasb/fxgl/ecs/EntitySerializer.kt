/*
 * FXGL - JavaFX Game Library. The MIT License (MIT).
 * Copyright (c) AlmasB (almaslvl@gmail.com).
 * See LICENSE for details.
 */

package com.almasb.fxgl.ecs

import com.almasb.fxgl.core.logging.Logger
import com.almasb.fxgl.ecs.serialization.SerializableComponent
import com.almasb.fxgl.ecs.serialization.SerializableControl
import com.almasb.fxgl.io.serialization.Bundle

/**
 *
 *
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
internal object EntitySerializer {

    private val log = Logger.get(javaClass)

    /**
     * Save entity state into bundle.
     * Only serializable components and controls will be written.
     *
     * @param bundle the bundle to write to
     */
    fun save(entity: Entity, bundle: Bundle) {
        val componentsBundle = Bundle("components")

        entity.components
                .filterIsInstance<SerializableComponent>()
                .forEach {
                    val b = Bundle(it.javaClass.canonicalName)
                    it.write(b)

                    componentsBundle.put(b.name, b)
                }


        val controlsBundle = Bundle("controls")

        entity.controls
                .filterIsInstance<SerializableControl>()
                .forEach {
                    val b = Bundle(it.javaClass.canonicalName)
                    it.write(b)

                    controlsBundle.put(b.name, b)
                }


        bundle.put("components", componentsBundle)
        bundle.put("controls", controlsBundle)
    }

    /**
     * Load entity state from a bundle.
     * Only serializable components and controls will be read.
     * If an entity has a serializable type that is not present in the bundle,
     * a warning will be logged but no exception thrown.
     *
     * @param bundle bundle to read from
     */
    fun load(entity: Entity, bundle: Bundle) {
        val componentsBundle = bundle.get<Bundle>("components")

        for (component in entity.components) {
            if (component is SerializableComponent) {

                val b = componentsBundle.get<Bundle>(component.javaClass.canonicalName)
                if (b != null)
                    component.read(b)
                else
                    log.warning("Bundle $componentsBundle does not have SerializableComponent: $component")
            }
        }

        val controlsBundle = bundle.get<Bundle>("controls")

        for (control in entity.controls) {
            if (control is SerializableControl) {

                val b = controlsBundle.get<Bundle>(control.javaClass.canonicalName)
                if (b != null)
                    control.read(b)
                else
                    log.warning("Bundle $componentsBundle does not have SerializableControl: $control")
            }
        }
    }
}