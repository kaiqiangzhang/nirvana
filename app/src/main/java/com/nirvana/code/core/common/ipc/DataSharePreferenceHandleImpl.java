package com.nirvana.code.core.common.ipc;

import android.os.RemoteException;

import com.nirvana.code.IDataSharePreferenceHandle;

/**
 * Created by kriszhang on 16/1/27.
 */
public class DataSharePreferenceHandleImpl extends IDataSharePreferenceHandle.Stub{
    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

    }

    @Override
    public String getValueFromKey(String key) throws RemoteException {
        return null;
    }
}
