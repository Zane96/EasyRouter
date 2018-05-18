package me.zane.easyrouter_merge;

import com.android.build.gradle.BaseExtension;

import org.gradle.api.Project;
import org.gradle.api.Plugin;
import org.gradle.api.ProjectConfigurationException;

/**
 * Created by Zane on 2018/4/19.
 * Email: zanebot96@gmail.com
 */
public class RouterModulePlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        BaseExtension androidExtension = (BaseExtension) project.getExtensions().getByName("android");

        if (project.getPlugins().findPlugin("com.android.application") != null) {
            androidExtension.registerTransform(new ApplicationTransform());
        } else if (project.getPlugins().findPlugin("com.android.library") != null) {
            androidExtension.registerTransform(new LibraryTransform(project));
        } else {
            throw new ProjectConfigurationException("Need android application/library plugin to be applied first", null);
        }
    }
}
