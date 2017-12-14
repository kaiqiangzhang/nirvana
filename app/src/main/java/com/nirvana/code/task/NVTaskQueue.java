package com.nirvana.code.task;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This TaskQueue is used to queue up tasks that are supposed to run sequentially, which helps avoid problems related to thread-safety.
 * @author Kris.Zhang
 * 2016-09-26 10:18:20
 */
public class NVTaskQueue extends Thread
{
	private BlockingQueue<Object> mQueue;

	public NVTaskQueue()
	{
		init("Queue");
	}

	public NVTaskQueue(String aQueueName)
	{
		init(aQueueName);
	}

	// init queue
	private void init(String aQueueName)
	{
		setName(aQueueName);
		mQueue = new LinkedBlockingQueue<Object>();
	}

	@Override
	public synchronized void start()
	{
		super.start();
	}

	public synchronized void stopTaskQueue()
	{
		// use 'Poison Pill Shutdown' to stop the task queue
		// add a non-Runnable object, which will be recognized as the command
		// by the thread to break the infinite loop
		mQueue.add(new Object());
	}

	public synchronized void scheduleTask(Runnable aTask)
	{
		mQueue.add(aTask);
	}

	@Override
	public void run()
	{
		while (true)
		{
			try
			{
				Object obj = mQueue.take();
				if (obj instanceof Runnable)
					((Runnable) obj).run();
				else
					break;
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

}
