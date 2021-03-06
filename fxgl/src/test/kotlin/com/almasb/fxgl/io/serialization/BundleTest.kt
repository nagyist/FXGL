/*
 * FXGL - JavaFX Game Library. The MIT License (MIT).
 * Copyright (c) AlmasB (almaslvl@gmail.com).
 * See LICENSE for details.
 */

package com.almasb.fxgl.io.serialization

import org.junit.Assert.assertTrue
import org.junit.Test

/**
 *
 *
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
class BundleTest {

    @Test
    fun `Test access`() {
        val bundle = Bundle("Test")

        val s: String? = bundle.get<String>("None")

        assertTrue(s == null)
    }
}