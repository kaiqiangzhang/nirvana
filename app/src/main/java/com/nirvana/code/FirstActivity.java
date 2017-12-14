package com.nirvana.code;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nirvana.code.core.utils.log.NVLog;
import com.nirvana.code.core.view.particle.ParticleView;
import com.nirvana.code.utils.NVUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FirstActivity extends Activity {
    private ParticleView mParticleView;
    private ImageView mAdLaout;
    private WebView mWebView;
    private boolean adClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.first_acrivity);
        mParticleView = (ParticleView) findViewById(R.id.paticle_view);
        mAdLaout = (ImageView) findViewById(R.id.splash_ad);
        mWebView = (WebView) findViewById(R.id.daily_sentence);
        mAdLaout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adClicked = true;
            }
        });
        setAdLayoutScale(mAdLaout);
        setAdImgBg();
        getDailySentence();

        mParticleView.setOnParticleAnimListener(new ParticleView.ParticleAnimListener() {
            @Override
            public void onAnimationEnd() {

            }
        });
        mParticleView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mParticleView.startAnim();
            }
        }, 200);
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(FirstActivity.this, Splash.class);
                startActivity(intent);
                if (adClicked) {
                    gotoAdDetial();
                }
                finish();
            }
        }, 3000);

    }

    /**
     * 获取每日一句
     *
     * @return
     */
    public String getDailySentence() {
        JsonArrayRequest dailyJsonRequest = new JsonArrayRequest("http://www.haowuyun.com/api/daySay.json", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {

                String dailySentence = "";
                try {
                    dailySentence = ((JSONObject) jsonArray.get(0)).optString("value");
                    NVLog.e("FirstActivity", "json1=" + dailySentence.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (jsonArray == null || TextUtils.isEmpty(dailySentence)) {
                    return;
                }
                NVLog.e("FirstActivity", "json=" + dailySentence);
                StringBuffer sb = new StringBuffer();
                //添加html
                sb.append("<html><head><meta http-equiv='content-type' content='text/html; charset=utf-8'>");
                sb.append("<meta charset='utf-8'  content='1'></head><body bgcolor='#3F51B5'  style='color:#ffffff'>");
                //
                //< meta http-equiv="refresh" content="time" url="url" >
                //添加文件的内容
                sb.append(dailySentence);
                //加载本地文件
                // sb.append("<img src='file:///"+AContext.getFileUtil().getDownloadsPath()+"'>");
                sb.append("</body></html>");
                // webView.loadData(data, mimeType, encoding);
                //设置字符编码，避免乱码
                mWebView.getSettings().setDefaultTextEncodingName("utf-8");
                mWebView.loadDataWithBaseURL(null, sb.toString(), "text/html", "utf-8", null);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        RequestQueue mQueue = Volley.newRequestQueue(FirstActivity.this);
        mQueue.add(dailyJsonRequest);
        return "";
    }

    public void gotoAdDetial() {
        Intent intent = new Intent(FirstActivity.this, WebViewActivity.class);
        intent.putExtra("url", "https://s.click.taobao.com/x7DIABx");
        startActivity(intent);
    }

    public void setAdImgBg() {
        ImageRequest imageRequest = new ImageRequest(
                "http://www.haowuyun.com/pics/app_splash.jpg_400x400_.webp",
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        mAdLaout.setVisibility(View.VISIBLE);
                        mAdLaout.setImageBitmap(response);
                    }
                }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mAdLaout.setVisibility(View.GONE);
            }
        });
        RequestQueue mQueue = Volley.newRequestQueue(FirstActivity.this);
        mQueue.add(imageRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 设置广告显示比例
     */
    private void setAdLayoutScale(View v) {// 图片6.4:1比例显示
        int margintop = NVUtils.dip2pix(this, 0);
        int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
//        int marginTop = 10 + margintop;
        //int width = (screenWidth - 2 * marginRight);
        int adImageHeight = (int) (screenWidth / 1.0d);//1.5:1的比例，0.5d属于微调，避免精度损失 kris.zhang
        int adImageWidth = screenWidth;
        android.view.ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
        layoutParams.height = adImageHeight;
        layoutParams.width = adImageWidth;
        layoutParams.setMargins(0, margintop, 0, margintop);//左右上下间隔 各32px
        v.setLayoutParams(layoutParams);
    }
}
