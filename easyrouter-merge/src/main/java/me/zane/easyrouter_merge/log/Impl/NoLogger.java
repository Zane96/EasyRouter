package me.zane.easyrouter_merge.log.Impl;

import org.gradle.api.logging.LogLevel;

/**
 * Created by Jude on 2017/12/8.
 */

public class NoLogger extends BaseLogger {
    @Override
    protected void write(LogLevel level, String prefix, String msg, Throwable t) {

    }
}
