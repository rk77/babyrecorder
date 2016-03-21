package com.recodrder.baby.rk.babyrecorder;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Handler;

public class BabyRecorderActivity extends ActionBarActivity implements RecorderItemAdapter.DBOp{

   private final static Uri CP_URI = Uri.parse("content://babyrecorder");

    private ListView lv;
    private Button mBtn_weinai;
    private Button mBtn_shuijiao;
    private Button mBtn_lashi;
    private Button mBtn_niaoniao;

    private NonUiHandler mNonUiHandler;

    private Cursor mCursor;
    private RecorderItemAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("TAG", "onCreate");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_baby_recorder);
        lv = (ListView) findViewById(R.id.lv);
        mBtn_weinai = (Button) findViewById(R.id.weinai);
        mBtn_shuijiao = (Button) findViewById(R.id.shuijiao);
        mBtn_lashi = (Button) findViewById(R.id.lashi);
        mBtn_niaoniao = (Button) findViewById(R.id.niaoniao);

        mBtn_weinai.setOnClickListener(new ButtonListener());
        mBtn_shuijiao.setOnClickListener(new ButtonListener());
        mBtn_niaoniao.setOnClickListener(new ButtonListener());
        mBtn_lashi.setOnClickListener(new ButtonListener());

        HandlerThread ht = new HandlerThread("db_op");
        ht.start();
        mNonUiHandler = new NonUiHandler(ht.getLooper());



       /* mCursor = getContentResolver().query(CP_URI,
                new String[] {"_id","event","tstart","tend"},
                null,null,null);*/

        mAdapter = new RecorderItemAdapter(this,R.layout.item_layout,mCursor);

        lv.setAdapter(mAdapter);

        Message msg = mNonUiHandler.obtainMessage(NonUiHandler.MSG_LV_UPDATE);
        mNonUiHandler.sendMessage(msg);


    }

    private class NonUiHandler extends android.os.Handler {
        public final static int MSG_DB_QUERY = 0;
        public final static int MSG_DB_INSERT = 1;
        public final static int MSG_DB_UPDATE = 2;
        public final static int MSG_DB_DELETE = 3;
        public final static int MSG_LV_UPDATE = 4;

        NonUiHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_DB_QUERY: {
                    break;
                }
                case MSG_DB_DELETE: {
                    int id = msg.arg1;
                    String where = "_id=" + id;
                    getContentResolver().delete(CP_URI,where,null);
                    sendMessage(obtainMessage(MSG_LV_UPDATE));
                    break;
                }
                case MSG_DB_INSERT: {
                    ContentValues cv = (ContentValues) msg.obj;
                    getContentResolver().insert(CP_URI, cv);
                    sendMessage(obtainMessage(MSG_LV_UPDATE));
                    break;
                }
                case MSG_DB_UPDATE: {
                    ContentValues cv = (ContentValues) msg.obj;
                    int id = msg.arg1;
                    String where = "_id="+id;
                    getContentResolver().update(CP_URI,cv,where,null);
                    sendMessage(obtainMessage(MSG_LV_UPDATE));
                    break;
                }
                case MSG_LV_UPDATE: {
                    mCursor = getContentResolver().query(CP_URI,
                            new String[] {"_id","event","tstart","tend"},
                            null,null,null);
                    runOnUiThread(new Runnable() {
                                      @Override
                                      public void run() {
                                          mAdapter.changeCursor(mCursor);
                                      }
                                  }

                    );
                    break;
                }
                default:{
                    break;
                }
            }
        }
    }
    private class ButtonListener implements OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.weinai: {
                    String event_name_weinai = (String) ((Button) view).getText();

                    long currentTime =  System.currentTimeMillis();
                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd-HH:mm");
                    Date date = new Date(currentTime);
                    ContentValues cv = new ContentValues();
                    cv.put("event", event_name_weinai);
                    cv.put("tstart",formatter.format(date));
                    Message msg = new Message();
                    msg.what = NonUiHandler.MSG_DB_INSERT;
                    msg.obj = cv;
                    mNonUiHandler.sendMessage(msg);
                    /*getContentResolver().insert(CP_URI, cv);
                    mCursor = getContentResolver().query(CP_URI,
                            new String[] {"_id","event","tstart","tend"},
                            null,null,null);
                    mAdapter.changeCursor(mCursor);*/
                    break;}
                case R.id.shuijiao:{
                    String event_name_shuijiao = (String) ((Button) view).getText();
                    long currentTime =  System.currentTimeMillis();

                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd-HH:mm");
                    Date date = new Date(currentTime);
                    ContentValues cv = new ContentValues();
                    cv.put("event", event_name_shuijiao);
                    cv.put("tstart",formatter.format(date));
                    Message msg = new Message();
                    msg.what = NonUiHandler.MSG_DB_INSERT;
                    msg.obj = cv;
                    mNonUiHandler.sendMessage(msg);
                    /*getContentResolver().insert(CP_URI, cv);
                    mCursor = getContentResolver().query(CP_URI,
                            new String[] {"_id","event","tstart","tend"},
                            null,null,null);
                    mAdapter.changeCursor(mCursor);*/
                    break;}
                case R.id.lashi:{
                    String event_name_lashi = (String) ((Button) view).getText();
                    long currentTime =  System.currentTimeMillis();

                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd-HH:mm");
                    Date date = new Date(currentTime);
                    ContentValues cv = new ContentValues();
                    cv.put("event", event_name_lashi);
                    cv.put("tstart",formatter.format(date));
                    Message msg = new Message();
                    msg.what = NonUiHandler.MSG_DB_INSERT;
                    msg.obj = cv;
                    mNonUiHandler.sendMessage(msg);
                    /*getContentResolver().insert(CP_URI, cv);
                    mCursor = getContentResolver().query(CP_URI,
                            new String[] {"_id","event","tstart","tend"},
                            null,null,null);
                    mAdapter.changeCursor(mCursor);*/
                    break;}
                case R.id.niaoniao:{
                    String event_name_niaoniao = (String) ((Button) view).getText();
                    long currentTime =  System.currentTimeMillis();

                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd-HH:mm");
                    Date date = new Date(currentTime);

                    ContentValues cv = new ContentValues();
                    cv.put("event", event_name_niaoniao);
                    cv.put("tstart",formatter.format(date));
                    Message msg = new Message();
                    msg.what = NonUiHandler.MSG_DB_INSERT;
                    msg.obj = cv;
                    mNonUiHandler.sendMessage(msg);
                    /*getContentResolver().insert(CP_URI, cv);
                    mCursor = getContentResolver().query(CP_URI,
                            new String[] {"_id","event","tstart","tend"},
                            null,null,null);
                    mAdapter.changeCursor(mCursor);*/
                    break;}
                default:
                    break;
            }


        }
    }


    public void updateDB(int id, ContentValues contentValues) {
        Message msg = new Message();
        msg.what = NonUiHandler.MSG_DB_UPDATE;
        msg.arg1 = id;
        msg.obj = contentValues;
        mNonUiHandler.sendMessage(msg);


    }

    public void deleteDB(int id) {

        Message msg = new Message();
        msg.what = NonUiHandler.MSG_DB_DELETE;
        msg.arg1 = id;
        mNonUiHandler.sendMessage(msg);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_baby_recorder, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
