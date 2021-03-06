package com.alibaba.cloudpushdemo.bizactivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.cloudpushdemo.R;
import com.alibaba.cloudpushdemo.component.ActivityBox;
import com.alibaba.cloudpushdemo.adapter.MessageListAdapter;
import com.alibaba.cloudpushdemo.dao.MessageDao;

import java.lang.reflect.Field;

/**
 * 主页面
 */
public class CPDMainActivity extends Activity {

    private ListView messageListView = null;
    private ListAdapter adapter;

    /**
     * 初始化消息列表
     */
    public void initMessageView() {
        messageListView = (ListView)findViewById(R.id.messagelist);
        messageListView.setDividerHeight(15);
        adapter = new MessageListAdapter(getApplicationContext(), new MessageDao(getApplicationContext()).getAll());
        messageListView.setAdapter(adapter);
    }

    /**
     * 反射拿到Google的sHasPermanentMenuKey属性，强制设置机型为无实体按键，保证出现右上角OverFlow菜单
     */
    private void setOverflowShowingAlways() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_lzlmain);
        this.setOverflowShowingAlways();

        this.initMessageView();
        ActivityBox.CPDMainActivity = this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lzlmain, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about_us:  // 点击 关于我们 选项卡
                AboutActivity.actionStart(this.getApplicationContext());
                return true;
            case R.id.action_clear_tips:  // 点击 清空消息 选项卡
                new MessageDao(getApplicationContext()).delAll();
                this.initMessageView();
                Toast.makeText(getApplicationContext(), "赞,消息列表已清空~",
                        Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_deviceid:  // 点击 查看DeviceID 选项卡
                DeviceActivity.actionStart(this.getApplicationContext());
                return true;
            case R.id.action_doctor:  // 点击 诊断专家 选项卡
                CheckerActivity.actionStart(this.getApplicationContext());
                return true;
            case R.id.action_helper:  // 点击 帮助中心 选项卡
                HelperActivity.actionStart(this.getApplicationContext());
                return true;
            case R.id.action_settings:  // 点击 设置中心 选项卡
                SettingsActivity.actionStart(this.getApplicationContext());
                return true;
            case R.id.action_settings_notice:   // 点击通知设置
                SettingNoticeActivity.actionStart(this.getApplicationContext());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
