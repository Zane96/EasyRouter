package me.zane.easyrouter_merge.framework;

import com.android.build.api.transform.QualifiedContent;

import java.io.IOException;

/**
 * Created by gengwanpeng on 17/4/28.
 *
 * Unzip QualifiedContent and provide single class for inout ClassHandler.
 * QualifiedContent may be one of {@link com.android.build.api.transform.JarInput} and {@link com.android.build.api.transform.DirectoryInput}.
 * So there are tow child of QualifiedContentProvider {@link JarContentProvider} and {@link DirectoryContentProvider}
 *
 */
public interface QualifiedContentProvider {

    /**
     * start accept the classes
     * @param content
     * @param processor
     * @throws IOException
     */
    void forEach(QualifiedContent content, ClassHandler processor) throws IOException;
}
