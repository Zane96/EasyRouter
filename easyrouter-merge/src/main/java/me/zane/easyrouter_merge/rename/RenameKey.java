package me.zane.easyrouter_merge.rename;

import org.gradle.api.Project;

/**
 * Created by Zane on 2018/5/17.
 * Email: zanebot96@gmail.com
 */
public class RenameKey {
    public static String KEY;

    public static void init(Project project){
        KEY = project.getGroup()+"_"+project.getName();
    }

    public static String getKEY(){
        return KEY;
    }
}
