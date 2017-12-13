package com.example.listviewtiger;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Random;


/**
 * 简易老虎机,水果机,待完善
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ListView lv1;
    private ListView lv2;
    private ListView lv3;
    private static int time = 5;// 总共5次机会
    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        time = 5;

        lv1 = (ListView) findViewById(R.id.lv1);
        lv2 = (ListView) findViewById(R.id.lv2);
        lv3 = (ListView) findViewById(R.id.lv3);

        MyAdapter myAdapter = new MyAdapter();
        lv1.setAdapter(myAdapter);
        lv2.setAdapter(myAdapter);
        lv3.setAdapter(myAdapter);

        mMediaPlayer = MediaPlayer.create(this, R.raw.laohuji);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    /**
     * 点击按钮,开始游戏
     *
     * @param view button
     */
    public void startGame(View view) {
        time--;
        switch (time) {
            case 5:
                Toast.makeText(getApplicationContext(), "您还有5次机会!", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Toast.makeText(getApplicationContext(), "您还有4次机会!", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(getApplicationContext(), "您还有3次机会!", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(getApplicationContext(), "您还有2次机会!", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(getApplicationContext(), "您还有1次机会!", Toast.LENGTH_SHORT).show();
                break;
            case 0:
                Toast.makeText(getApplicationContext(), "游戏结束!", Toast.LENGTH_SHORT).show();
                showDialog();
        }
        // 以下代码之前没注释，都看不懂了
        Random random = new Random();
        final int nextInt = random.nextInt(5000);
        Log.d(TAG, "startGame: nextInt:" + nextInt);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 5; i++) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lv1.scrollListBy(nextInt / 5);
                            lv2.scrollListBy(nextInt / 5);
                            lv3.scrollListBy(nextInt / 5);
                        }
                    });
                    SystemClock.sleep(100);
                }
            }
        }).start();
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示:");
        builder.setMessage("游戏结束!");
        builder.setPositiveButton("退出游戏", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.finish();
            }
        });
        builder.setNegativeButton("再试一次", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                lv1.setSelection(0);
                lv2.setSelection(0);
                lv3.setSelection(0);
                time = 5;
            }
        });
        builder.setIcon(R.drawable.apple_pic);
        builder.show();
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 100;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(getApplicationContext());
            } else {
                imageView = (ImageView) convertView;
            }
            Random random = new Random();
            int nextInt = random.nextInt(10) + 1;
            switch (nextInt) {
                case 1:
                    imageView.setImageResource(R.drawable.apple_pic);
                    break;
                case 2:
                    imageView.setImageResource(R.drawable.banana_pic);
                    break;
                case 3:
                    imageView.setImageResource(R.drawable.grape_pic);
                    break;
                case 4:
                    imageView.setImageResource(R.drawable.mango_pic);
                    break;
                case 5:
                    imageView.setImageResource(R.drawable.orange_pic);
                    break;
                case 6:
                    imageView.setImageResource(R.drawable.cherry_pic);
                    break;
                case 7:
                    imageView.setImageResource(R.drawable.pear_pic);
                    break;
                case 8:
                    imageView.setImageResource(R.drawable.pineapple_pic);
                    break;
                case 9:
                    imageView.setImageResource(R.drawable.strawberry_pic);
                    break;
                case 10:
                    imageView.setImageResource(R.drawable.watermelon_pic);
                    break;
            }
            return imageView;
        }
    }

}

