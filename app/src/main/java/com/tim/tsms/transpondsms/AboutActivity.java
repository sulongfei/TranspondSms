package com.tim.tsms.transpondsms;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tim.tsms.transpondsms.BroadCastReceiver.RebootBroadcastReceiver;
import com.tim.tsms.transpondsms.model.vo.FeedBackResult;
import com.tim.tsms.transpondsms.utils.aUtil;

import java.util.HashMap;
import java.util.Map;


public class AboutActivity extends AppCompatActivity {
    private String TAG = "AboutActivity";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"oncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Log.d(TAG, "onCreate: "+RebootBroadcastReceiver.class.getName());

        Switch check_with_reboot = (Switch)findViewById(R.id.switch_with_reboot);
        checkWithReboot(check_with_reboot);

        TextView version_now = (TextView)findViewById(R.id.version_now);
        Button check_version_now = (Button)findViewById(R.id.check_version_now);
        try {
            version_now.setText(aUtil.getVersionName(AboutActivity.this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        check_version_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkNewVersion();
            }
        });

    }

    //检查重启广播接受器状态并设置
    private void checkWithReboot(Switch withrebootSwitch){
        //获取组件
        final ComponentName cm = new ComponentName(this.getPackageName(), RebootBroadcastReceiver.class.getName());

        final PackageManager pm = getPackageManager();
        int state = pm.getComponentEnabledSetting(cm);
        if (state != PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                && state != PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER) {

            withrebootSwitch.setChecked(true);
        }else{
            withrebootSwitch.setChecked(false);
        }
        withrebootSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int newState = (Boolean)isChecked ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                        : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
                pm.setComponentEnabledSetting(cm, newState,PackageManager.DONT_KILL_APP);
                Log.d(TAG,"onCheckedChanged:"+isChecked);
            }
        });
    }

    private void checkNewVersion(){

    }

    public void feedbackcommit(View view) {

    }

}
