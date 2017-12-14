package com.nirvana.code.core.common.ipc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.nirvana.code.IBinderPool;

import java.util.concurrent.CountDownLatch;

/**
 * Created by kriszhang on 16/1/27.
 */
public class BinderPoolManger {
    private static final String TAG="BinderPoolManger";
    public static final int BINDER_NONE=0;
    public static final int BINDER_PREFERENCE=1;
    public Context mContext;
    private IBinderPool mBinderPool;
    private CountDownLatch mCountDownLatch;
    private volatile BinderPoolManger mInstance;

    private ServiceConnection mBinderServiceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            mBinderPool=IBinderPool.Stub.asInterface(service);
            try {
                mBinderPool.asBinder().linkToDeath(mBinderPoolDeathRecipient,0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }finally {//一定要在最后执行
                mCountDownLatch.countDown();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {


        }
    };

    private IBinder.DeathRecipient mBinderPoolDeathRecipient=new IBinder.DeathRecipient(){
        @Override
        public void binderDied() {
            mBinderPool.asBinder().unlinkToDeath(mBinderPoolDeathRecipient,0);
            mBinderPool=null;
            //重新连接
            connectBinderService();
        }
    };


    private BinderPoolManger(Context context){
        mContext=context;
        connectBinderService();
    }

    public BinderPoolManger getBinderManagerInstance(Context context){
        if (mInstance!=null){
            mInstance =new BinderPoolManger(context);
        }
        return mInstance;
    }

    public synchronized void connectBinderService(){
        mCountDownLatch=new CountDownLatch(1);
        Intent intent=new Intent(mContext, BinderService.class);
        mContext.bindService(intent,mBinderServiceConnection,Context.BIND_AUTO_CREATE);
        try {
            mCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public IBinder queryBinder(int queryCode){
        IBinder binder=null;
        try {
            if (mBinderPool!=null){
                binder=mBinderPool.queryBinder(queryCode);
            }
        } catch (RemoteException e) {
                e.printStackTrace();
        }
        return binder;
    }



}
