/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nirvana.code.core.common.cache.imagecache;

public class ImageCache {
//
//    private static final String TAG = "ImageCache";
//
//    private static ImageCache instance;
//
//    // Default memory cache size in kilobytes
//    private static final int DEFAULT_MEM_CACHE_SIZE = 1024 * 5; // 5MB
//
//    // Default disk cache size in bytes
//    private static final int DEFAULT_DISK_CACHE_SIZE = 1024 * 1024 * 100; // 100MB
//
//    // Compression settings when writing images to disk cache
//    private static final CompressFormat DEFAULT_COMPRESS_FORMAT = CompressFormat.PNG;
//
//    private static final int DEFAULT_COMPRESS_QUALITY = 70;
//    private static final int DISK_CACHE_INDEX = 0;
//
//    // Constants to easily toggle various caches
//    private static final boolean DEFAULT_MEM_CACHE_ENABLED = true;
//    private static final boolean DEFAULT_DISK_CACHE_ENABLED = true;
//    private static final boolean DEFAULT_INIT_DISK_CACHE_ON_CREATE = false;
//
//    private DiskLruCache mDiskLruCache;
//    private LruCache<String, BitmapDrawable> mMemoryCache;
//    private ImageCacheParams mCacheParams;
//    private final Object mDiskCacheLock = new Object();
//    private boolean mDiskCacheStarting = true;
//
//    /**
//     * Create a new ImageCache object using the specified parameters. This should not be
//     * called directly by other classes, instead use
//     * {@link ImageCache#getInstance(FragmentManager, ImageCacheParams)} to fetch an ImageCache
//     * instance.
//     *
//     * @param cacheParams The cache parameters to use to initialize the cache
//     */
//    private ImageCache(ImageCacheParams cacheParams) {
//        init(cacheParams);
//    }
//
//    /**
//     * Return an {@link ImageCache} instance. A {@link RetainFragment} is used to retain the
//     * ImageCache object across configuration changes such as a change in device orientation.
//     *
//     * @param fragmentManager The fragment manager to use when dealing with the retained fragment.
//     * @param cacheParams The cache parameters to use if the ImageCache needs instantiation.
//     * @return An existing retained ImageCache object or a new one if one did not exist
//     */
//    public static ImageCache getInstance(ImageCacheParams cacheParams) {
//        // No existing ImageCache, create one and store it in RetainFragment
//        if (instance == null) {
//            instance = new ImageCache(cacheParams);
//        }
//        return instance;
//    }
//
//    /**
//     * Initialize the cache, providing all parameters.
//     *
//     * @param cacheParams The cache parameters to initialize the cache
//     */
//    private void init(ImageCacheParams cacheParams) {
//        mCacheParams = cacheParams;
//
//        // Set up memory cache
//        if (mCacheParams.memoryCacheEnabled) {
//
//            mMemoryCache = new LruCache<String, BitmapDrawable>(mCacheParams.memCacheSize) {
//
//                /**
//                 * Notify the removed entry that is no longer being cached
//                 */
//                @Override
//                protected void entryRemoved(boolean evicted, String key,
//                        BitmapDrawable oldValue, BitmapDrawable newValue) {
//                    if (RecyclingBitmapDrawable.class.isInstance(oldValue)) {
//                        // The removed entry is a recycling drawable, so notify it
//                        // that it has been removed from the memory cache
//                        ((RecyclingBitmapDrawable) oldValue).setIsCached(false);
//                    }
//                }
//
//                /**
//                 * Measure item size in kilobytes rather than units which is more practical
//                 * for a bitmap cache
//                 */
//                @Override
//                protected int sizeOf(String key, BitmapDrawable value) {
//                    final int bitmapSize = getBitmapSize(value) / 1024;
//                    return bitmapSize == 0 ? 1 : bitmapSize;
//                }
//            };
//        }
//
//        // By default the disk cache is not initialized here as it should be initialized
//        // on a separate thread due to disk access.
//        if (cacheParams.initDiskCacheOnCreate) {
//            // Set up disk cache
//            initDiskCache();
//        }
//    }
//
//    /**
//     * Initializes the disk cache.  Note that this includes disk access so this should not be
//     * executed on the main/UI thread. By default an ImageCache does not initialize the disk
//     * cache when it is created, instead you should call initDiskCache() to initialize it on a
//     * background thread.
//     */
//    public void initDiskCache() {
//        // Set up disk cache
//        synchronized (mDiskCacheLock) {
//            if (mDiskLruCache == null || mDiskLruCache.isClosed()) {
//                File diskCacheDir = mCacheParams.diskCacheDir;
//                if (mCacheParams.diskCacheEnabled && diskCacheDir != null) {
//                    if (!diskCacheDir.exists()) {
//                        diskCacheDir.mkdirs();
//                    }
//                    if (FileUtil.getUsableSpace(diskCacheDir) > mCacheParams.diskCacheSize) {
//                        try {
//                            mDiskLruCache = DiskLruCache.open(
//                                    diskCacheDir, 1, 1, mCacheParams.diskCacheSize);
//                        } catch (final IOException e) {
//                            mCacheParams.diskCacheDir = null;
//                            NVLog.log_e(TAG, "initDiskCache - " + e);
//                        }
//                    }
//                }
//            }
//            mDiskCacheStarting = false;
//            mDiskCacheLock.notifyAll();
//        }
//    }
//
//    /**
//     * Adds a bitmap to both memory and disk cache.
//     * @param data Unique identifier for the bitmap to store
//     * @param value The bitmap drawable to store
//     */
//    public void addBitmapToCache(String data, BitmapDrawable value) {
//    	addBitmapToCache(data, value, true);
//    }
//
//    /**
//     * Adds a bitmap to both memory and disk cache.
//     * @param data Unique identifier for the bitmap to store
//     * @param value The bitmap drawable to store
//     */
//    public void addBitmapToCache(String data, BitmapDrawable value, boolean addMemeryCache) {
//        if (data == null || value == null) {
//            return;
//        }
//
//        // Add to memory cache
//        if (addMemeryCache && mMemoryCache != null) {
//            if (RecyclingBitmapDrawable.class.isInstance(value)) {
//                // The removed entry is a recycling drawable, so notify it
//                // that it has been added into the memory cache
//                ((RecyclingBitmapDrawable) value).setIsCached(true);
//            }
//            mMemoryCache.put(data, value);
//        }
//
//        synchronized (mDiskCacheLock) {
//            // Add to disk cache
//            if (mDiskLruCache != null) {
//                final String key = hashKeyForDisk(data);
//                OutputStream out = null;
//                try {
//                    DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
//                    if (snapshot == null) {
//                        final DiskLruCache.Editor editor = mDiskLruCache.edit(key);
//                        if (editor != null) {
//                            out = editor.newOutputStream(DISK_CACHE_INDEX);
//                            value.getBitmap().compress(
//                                    mCacheParams.compressFormat, mCacheParams.compressQuality, out);
//                            editor.commit();
//                            out.close();
//                        }
//                    } else {
//                        snapshot.getInputStream(DISK_CACHE_INDEX).close();
//                    }
//                } catch (final IOException e) {
//                	NVLog.log_e(TAG, "addBitmapToCache - " + e);
//                } catch (Exception e) {
//                	NVLog.log_e(TAG, "addBitmapToCache - " + e);
//                } finally {
//                    try {
//                        if (out != null) {
//                            out.close();
//                        }
//                    } catch (IOException e) {}
//                }
//            }
//        }
//    }
//
//    /**
//     * Get from memory cache.
//     *
//     * @param data Unique identifier for which item to get
//     * @return The bitmap drawable if found in cache, null otherwise
//     */
//    public BitmapDrawable getBitmapFromMemCache(String data) {
//        BitmapDrawable memValue = null;
//
//        if (mMemoryCache != null) {
//            memValue = mMemoryCache.get(data);
//        }
//
//        NVLog.log_d(TAG, "Memory cache hit");
//
//        return memValue;
//    }
//
//    /**
//     * Get from disk cache.
//     *
//     * @param data Unique identifier for which item to get
//     * @return The bitmap if found in cache, null otherwise
//     */
//    public Bitmap getBitmapFromDiskCache(String data) {
//        final String key = hashKeyForDisk(data);
//        Bitmap bitmap = null;
//
//        synchronized (mDiskCacheLock) {
//            while (mDiskCacheStarting) {
//                try {
//                    mDiskCacheLock.wait();
//                } catch (InterruptedException e) {}
//            }
//            if (mDiskLruCache != null) {
//                InputStream inputStream = null;
//                try {
//                    final DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
//                    if (snapshot != null) {
//                        NVLog.log_e(TAG, "Disk cache hit");
//                        inputStream = snapshot.getInputStream(DISK_CACHE_INDEX);
//                        if (inputStream != null) {
//                            FileDescriptor fd = ((FileInputStream) inputStream).getFD();
//
//                            // Decode bitmap, but we don't want to sample so give
//                            // MAX_VALUE as the target dimensions
//                            bitmap = ImageResizer.decodeSampledBitmapFromDescriptor(
//                                    fd, Integer.MAX_VALUE, Integer.MAX_VALUE, this);
//                        }
//                    }
//                } catch (final IOException e) {
//                	NVLog.log_e(TAG, "getBitmapFromDiskCache - " + e);
//                } finally {
//                    try {
//                        if (inputStream != null) {
//                            inputStream.close();
//                        }
//                    } catch (IOException e) {}
//                }
//            }
//            return bitmap;
//        }
//    }
//
//
//    /**
//     * Clears both the memory and disk cache associated with this ImageCache object. Note that
//     * this includes disk access so this should not be executed on the main/UI thread.
//     */
//    public void clearCache() {
//        if (mMemoryCache != null) {
//            mMemoryCache.evictAll();
//            NVLog.log_e(TAG, "Memory cache cleared");
//        }
//
//        synchronized (mDiskCacheLock) {
//            mDiskCacheStarting = true;
//            if (mDiskLruCache != null && !mDiskLruCache.isClosed()) {
//                try {
//                    mDiskLruCache.delete();
//                    NVLog.log_e(TAG, "Disk cache cleared");
//                } catch (IOException e) {
//                	NVLog.log_e(TAG, "clearCache - " + e);
//                }
//                mDiskLruCache = null;
//                initDiskCache();
//            }
//        }
//    }
//
//    /**
//     * Flushes the disk cache associated with this ImageCache object. Note that this includes
//     * disk access so this should not be executed on the main/UI thread.
//     */
//    public void flush() {
//        synchronized (mDiskCacheLock) {
//            if (mDiskLruCache != null) {
//                try {
//                    mDiskLruCache.flush();
//                    NVLog.log_e(TAG, "Disk cache flushed");
//                } catch (IOException e) {
//                	NVLog.log_e(TAG, "flush - " + e);
//                }
//            }
//        }
//    }
//
//    /**
//     * Closes the disk cache associated with this ImageCache object. Note that this includes
//     * disk access so this should not be executed on the main/UI thread.
//     */
//    public void close() {
//        synchronized (mDiskCacheLock) {
//            if (mDiskLruCache != null) {
//                try {
//                    if (!mDiskLruCache.isClosed()) {
//                        mDiskLruCache.close();
//                        mDiskLruCache = null;
//                        NVLog.log_e(TAG, "Disk cache closed");
//                    }
//                } catch (IOException e) {
//                	NVLog.log_e(TAG, "close - " + e);
//                }
//            }
//        }
//    }
//
//    /**
//     * A holder class that contains cache parameters.
//     */
//    public static class ImageCacheParams {
//        public int memCacheSize = DEFAULT_MEM_CACHE_SIZE;
//        public int diskCacheSize = DEFAULT_DISK_CACHE_SIZE;
//        public File diskCacheDir;
//        public CompressFormat compressFormat = DEFAULT_COMPRESS_FORMAT;
//        public int compressQuality = DEFAULT_COMPRESS_QUALITY;
//        public boolean memoryCacheEnabled = DEFAULT_MEM_CACHE_ENABLED;
//        public boolean diskCacheEnabled = DEFAULT_DISK_CACHE_ENABLED;
//        public boolean initDiskCacheOnCreate = DEFAULT_INIT_DISK_CACHE_ON_CREATE;
//
//        /**
//         * Sets the memory cache size based on a percentage of the max available VM memory.
//         * Eg. setting percent to 0.2 would set the memory cache to one fifth of the available
//         * memory. Throws {@link IllegalArgumentException} if percent is < 0.05 or > .8.
//         * memCacheSize is stored in kilobytes instead of bytes as this will eventually be passed
//         * to construct a LruCache which takes an int in its constructor.
//         *
//         * This value should be chosen carefully based on a number of factors
//         * Refer to the corresponding Android Training class for more discussion:
//         * http://developer.android.com/training/displaying-bitmaps/
//         *
//         * @param percent Percent of available app memory to use to size memory cache
//         */
//        public void setMemCacheSizePercent(float percent) {
//            if (percent < 0.05f || percent > 0.8f) {
//                throw new IllegalArgumentException("setMemCacheSizePercent - percent must be "
//                        + "between 0.05 and 0.8 (inclusive)");
//            }
//            memCacheSize = Math.round(percent * Runtime.getRuntime().maxMemory() / 1024);
//        }
//    }
//
//    /**
//     * Get a usable cache directory (external if available, internal otherwise).
//     *
//     * @param context The context to use
//     * @param uniqueName A unique directory name to append to the cache dir
//     * @return The cache dir
//     */
//    public static File getDiskCacheDir(Context context) {
//        // Check if media is mounted or storage is built-in, if so, try and use external cache dir
//        // otherwise use internal cache dir
//        String cachePath =
//                Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !isExternalStorageRemovable()
//                        ? CacheManager.getFileDir(context, context.getString(R.string.CacheFixedImg)) :
//                                context.getCacheDir().getPath();
//        if(cachePath==null){
//        	cachePath =context.getCacheDir().getPath();
//        }
//        return new File(cachePath);
//    }
//
//    /**
//     * A hashing method that changes a string (like a URL) into a hash suitable for using as a
//     * disk filename.
//     */
//    public static String hashKeyForDisk(String key) {
//        String cacheKey;
//        try {
//            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
//            mDigest.update(key.getBytes());
//            cacheKey = bytesToHexString(mDigest.digest());
//        } catch (NoSuchAlgorithmException e) {
//            cacheKey = String.valueOf(key.hashCode());
//        }
//        return cacheKey;
//    }
//
//    private static String bytesToHexString(byte[] bytes) {
//        // http://stackoverflow.com/questions/332079
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < bytes.length; i++) {
//            String hex = Integer.toHexString(0xFF & bytes[i]);
//            if (hex.length() == 1) {
//                sb.append('0');
//            }
//            sb.append(hex);
//        }
//        return sb.toString();
//    }
//
//    /**
//     * Get the size in bytes of a bitmap in a BitmapDrawable.
//     * @param value
//     * @return size in bytes
//     */
//	public static int getBitmapSize(BitmapDrawable value) {
//        Bitmap bitmap = value.getBitmap();
//        if(bitmap==null){
//        	return 0 ;
//        }
//        if (Utils.hasHoneycombMR1()) {
//            return bitmap.getByteCount();
//        }
//        // Pre HC-MR1
//        return bitmap.getRowBytes() * bitmap.getHeight();
//    }
//
//    /**
//     * Check if external storage is built-in or removable.
//     *
//     * @return True if external storage is removable (like an SD card), false
//     *         otherwise.
//     */
//	public static boolean isExternalStorageRemovable() {
//        if (Utils.hasGingerbread()) {
//            return Environment.isExternalStorageRemovable();
//        }
//        return true;
//    }
}
