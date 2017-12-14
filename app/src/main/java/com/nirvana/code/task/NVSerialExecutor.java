package com.nirvana.code.task;

import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * 执行串行任务
 * @author kriszhang
 * 2016-09-26 10:19:09
 */
public class NVSerialExecutor implements Executor
{

	private final Queue<Runnable> mQueue = new LinkedBlockingQueue<Runnable>();
	private Runnable mActive;

	public synchronized void execute(final Runnable r)
	{
		mQueue.offer(new Runnable()
		{
			public void run()
			{
				try
				{
					r.run();
				}
				finally
				{
					scheduleNext();
				}
			}
		});
		if (mActive == null)
		{
			scheduleNext();
		}
	}

	protected synchronized void scheduleNext()
	{
		if ((mActive = mQueue.poll()) != null)
		{
			NVTaskExecutor.executeTask(mActive);
		}
	}

}
