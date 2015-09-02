package com.pluu.androidcrashsample.fabric;

import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import com.pluu.androidcrashsample.exception.Frantic;

/**
 * Fbaric fork ExecutorUtils
 */
public final class ExecutorUtils {
	private static final long DEFAULT_TERMINATION_TIMEOUT = 2L;

	private ExecutorUtils() {
	}

	public static ExecutorService buildSingleThreadExecutorService(String name) {
		ThreadFactory threadFactory = getNamedThreadFactory(name);
		ExecutorService executor = Executors.newSingleThreadExecutor(threadFactory);
		addDelayedShutdownHook(name, executor);
		return executor;
	}

	public static final ThreadFactory getNamedThreadFactory(final String threadNameTemplate) {
		final AtomicLong count = new AtomicLong(1L);
		return new ThreadFactory() {
			public Thread newThread(final Runnable runnable) {
				Thread thread = Executors.defaultThreadFactory().newThread(new BackgroundPriorityRunnable() {
					public void onRun() {
						runnable.run();
					}
				});
				thread.setName(threadNameTemplate + count.getAndIncrement());
				return thread;
			}
		};
	}

	private static final void addDelayedShutdownHook(String serviceName, ExecutorService service) {
		addDelayedShutdownHook(serviceName, service, 2L, TimeUnit.SECONDS);
	}

	public static final void addDelayedShutdownHook(final String serviceName, final ExecutorService service, final long terminationTimeout, final TimeUnit timeUnit) {
		Runtime.getRuntime().addShutdownHook(new Thread(new BackgroundPriorityRunnable() {
			public void onRun() {
				try {
					Frantic.getLogger().d(Frantic.LOG_TAG, "Executing shutdown hook for " + serviceName);
					service.shutdown();
					if(!service.awaitTermination(terminationTimeout, timeUnit)) {
						Frantic.getLogger().d(Frantic.LOG_TAG, serviceName + " did not shut down in the" + " allocated time. Requesting immediate shutdown.");
						service.shutdownNow();
					}
				} catch (InterruptedException var2) {
					Frantic.getLogger()
						   .d(Frantic.LOG_TAG, String.format(Locale.US, "Interrupted while waiting for %s to shut down. Requesting immediate shutdown.", new Object[]{serviceName}));
					service.shutdownNow();
				}

			}
		}, "Crashlytics Shutdown Hook for " + serviceName));
	}
}