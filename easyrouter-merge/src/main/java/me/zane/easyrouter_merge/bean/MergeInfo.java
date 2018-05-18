package me.zane.easyrouter_merge.bean;

import org.objectweb.asm.tree.MethodNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Zane on 2018/5/17.
 * Email: zanebot96@gmail.com
 */
public class MergeInfo {
    public List<MethodNode> mMapMethods = Collections.synchronizedList(new ArrayList<>());

    public List<MethodNode> getMapMethods() {
        return mMapMethods;
    }

    public void addMapMethods(MethodNode mMapMethod) {
        this.mMapMethods.add(mMapMethod);
    }

    public void combine(MergeInfo info) {
        mMapMethods.addAll(info.mMapMethods);
    }

    @Override
    public String toString() {
        return "mMapMethods" + mMapMethods.size() + "\n";
    }
}
