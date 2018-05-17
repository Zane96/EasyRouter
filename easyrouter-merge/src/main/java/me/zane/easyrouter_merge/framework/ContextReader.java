package me.zane.easyrouter_merge.framework;

import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.QualifiedContent;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import me.zane.easyrouter_merge.log.Log;

/**
 * Created by gengwanpeng on 17/5/2.
 *
 * This class will unzip all jars,and accept all class with input ClassHandler in thread pool.
 * Used in pre-analysis and formal analysis.
 *
 *
 */
public class ContextReader {
    private TransformContext context;
    private TargetedQualifiedContentProvider provider;
    private ExecutorService service = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors(),
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(), (r, executor) -> {
        Log.i("partial parse failed, executor has been shutdown");
    });

    public ContextReader(TransformContext context,TargetedQualifiedContentProvider provider) {
        this.context = context;
        this.provider = provider;
    }

    /**
     * read the classes in thread pool and send class to fetcher.
     * @param fetcher the fetcher to visit classes
     * @throws IOException
     * @throws InterruptedException
     */
    public void accept(ClassHandler fetcher) throws IOException, InterruptedException {
        // get all jars
        Collection<JarInput> jars = context.getAllJars();
        // accept the jar in thread pool
        List<Future<Void>> tasks = Stream.concat(jars.stream(), context.getAllDirs().stream())
                                           .filter(t -> provider.accepted(t))
                                           .map(q -> new QualifiedContentTask(q, fetcher))
                                           .map(t -> service.submit(t))
                                           .collect(Collectors.toList());

        // block until all task has finish.
        for (Future<Void> future : tasks) {
            try {
                future.get();
            } catch (ExecutionException e) {
                Throwable cause = e.getCause();
                if (cause instanceof IOException) {
                    throw (IOException) cause;
                } else if (cause instanceof InterruptedException) {
                    throw (InterruptedException) cause;
                } else {
                    throw new RuntimeException(e.getCause());
                }
            }
        }

    }

    /**
     * Task to accept target QualifiedContent
     */
    private class QualifiedContentTask implements Callable<Void> {

        private QualifiedContent content;
        private ClassHandler fetcher;

        QualifiedContentTask(QualifiedContent content, ClassHandler fetcher) {
            this.content = content;
            this.fetcher = fetcher;
        }

        @Override
        public Void call() throws Exception {
            provider.forEach(content, fetcher);
            return null;
        }
    }
}
