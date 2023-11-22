package com.example.zzt.buildsrc

import com.android.build.api.instrumentation.FramesComputationMode
import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.Component
import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author: zeting
 * @date: 2023/10/23
 */
class TrackingPluginV4 : Plugin<Project> {
    override fun apply(project: Project) {
        project.pluginManager.withPlugin("com.android.application") {
            val androidComponentsExtension =
                project.extensions.getByType(AndroidComponentsExtension::class.java)
            androidComponentsExtension.onVariants { variant ->
                variant.instrumentation.transformClassesWith(
                    classVisitorFactoryImplClass = TrackingFactoryV3::class.java,
                    scope = InstrumentationScope.ALL,
                ) {
                }

                variant.instrumentation.setAsmFramesComputationMode(FramesComputationMode.COPY_FRAMES)
            }
        }
    }

    companion object {
        private const val TAG = "ASM-buildSrc-Plugin"
    }
}