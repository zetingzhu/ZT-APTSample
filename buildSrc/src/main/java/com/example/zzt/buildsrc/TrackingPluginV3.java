package com.example.zzt.buildsrc;

import com.android.build.api.instrumentation.FramesComputationMode;
import com.android.build.api.instrumentation.InstrumentationScope;
import com.android.build.api.variant.AndroidComponentsExtension;
import com.android.build.api.variant.Component;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * @author: zeting
 * @date: 2023/10/23
 */
public class TrackingPluginV3 implements Plugin<Project> {
    private static final String TAG = "ASM-buildSrc-Plugin";

    @Override
    public void apply(Project target) {
        System.out.println(TAG + ">>>>>> apply ");
        AndroidComponentsExtension comp = target.getExtensions().getByType(AndroidComponentsExtension.class);
        comp.onVariants(comp.selector().all(), new Action<Component>() {
            @Override
            public void execute(Component variant) {
                variant.transformClassesWith(TrackingFactoryV3.class, InstrumentationScope.ALL, v -> null);
                variant.setAsmFramesComputationMode(FramesComputationMode.COMPUTE_FRAMES_FOR_INSTRUMENTED_METHODS);
            }
        });
    }
}
