package com.nirvana.code.core.common.ipc;

import android.os.IBinder;
import android.os.RemoteException;

import com.nirvana.code.IBinderPool;

/**
 * Created by kriszhang on 16/1/27.
 */
public class BinderPoolImpl extends IBinderPool.Stub{
    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

    }

    @Override
    public IBinder queryBinder(int queryCode) throws RemoteException {
        switch (queryCode){
            case BinderPoolManger.BINDER_PREFERENCE:
                
                break;
            case BinderPoolManger.BINDER_NONE:
                break;
            default:
        }
        return null;
    }
}
