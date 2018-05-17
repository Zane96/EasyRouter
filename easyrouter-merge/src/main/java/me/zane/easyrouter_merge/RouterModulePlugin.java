package me.zane.easyrouter_merge;

import com.android.build.gradle.BaseExtension;

import org.gradle.api.Project;
import org.gradle.api.Plugin;

/**
 * Created by Zane on 2018/4/19.
 * Email: zanebot96@gmail.com
 */
public class RouterModulePlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        BaseExtension androidExtension = (BaseExtension) project.getExtensions().getByName("android");
        if (project.getPlugins().getPlugin("com.android.library") != null) {
            androidExtension.registerTransform(new RenameTransform(project));
        }
//        if (project.getPlugins().getPlugin("com.android.application") != null) {
//            //androidExtension.registerTransform(new TestTransform());
//        } else if (project.getPlugins().getPlugin("com.android.library") != null) {
//            //androidExtension.registerTransform(new RenameTransform(project));
//        }
    }
}
