package com.recodrder.baby.rk.babyrecorder;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.Inflater;


/**
 * Created by rk on 2016/3/7.
 */
public class RecorderItemAdapter extends CursorAdapter {

    public interface DBOp {
        public void updateDB(int id, ContentValues contentValues);
        public void deleteDB(int id);
    }

    private Cursor mData;
    final private Context mContext;
    private int mRsId;

    public RecorderItemAdapter(Context context, int resourceId, Cursor cursor) {
        super(context,cursor,2);
        mContext = context;
        mRsId = resourceId;
        mData = cursor;

    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(mRsId, viewGroup, false);
        return view;
    }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {

            TextView Id_v = (TextView) view.findViewById(R.id.item_id);
            TextView event_v = (TextView) view.findViewById(R.id.event_name);
            TextView tstart_v = (TextView) view.findViewById(R.id.start_time);
            TextView tend_v = (TextView) view.findViewById(R.id.end_time);
            Button b_v = (Button) view.findViewById(R.id.end);
            Button d_v = (Button) view.findViewById(R.id.delete);

            final int id = cursor.getInt(cursor.getColumnIndex("_id"));
            final String event_string = cursor.getString(cursor.getColumnIndex("event"));
            final String tstart_string = cursor.getString(cursor.getColumnIndex("tstart"));
            final String tend_string = cursor.getString(cursor.getColumnIndex("tend"));

            Id_v.setText(Integer.toString(id));
            event_v.setText(event_string);
            tstart_v.setText(tstart_string);
            tend_v.setText(tend_string);

            if (tend_string == null || tend_string.isEmpty()){
                b_v.setVisibility(View.VISIBLE);
            } else {
                b_v.setVisibility(View.GONE);
            }

            b_v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setVisibility(View.INVISIBLE);
                    long time = System.currentTimeMillis();
                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd-HH:mm");
                    Date date = new Date(time);
                    ContentValues cv = new ContentValues();
                    cv.put("tend", formatter.format(time));
                    ((DBOp) mContext).updateDB(id, cv);


                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    final AlertDialog ad = new AlertDialog.Builder(mContext).create();
                    LayoutInflater inflater = LayoutInflater.from(mContext);
                    View view2 = inflater.inflate(R.layout.delete_item, null, false);
                    ad.setView(view2);
                    ad.show();
                    final TextView idTx = (TextView) view2.findViewById(R.id.update_id);
                    final TextView eventTx = (TextView) view2.findViewById(R.id.update_event);
                    final EditText tstartEt = (EditText)  view2.findViewById(R.id.update_tstart);
                    final EditText tendEt = (EditText) view2.findViewById(R.id.update_tend);
                    final Button UpdateBt = (Button) view2.findViewById(R.id.update_db);
                    final Button DeleteBt = (Button) view2.findViewById(R.id.del_db);

                    idTx.setText(Integer.toString(id));
                    eventTx.setText(event_string);
                    tstartEt.setText(tstart_string);
                    if (tend_string == null || tend_string.isEmpty()){
                        tendEt.setVisibility(View.GONE);
                    } else {
                        tendEt.setText(tend_string);
                    }


                    UpdateBt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ContentValues cv = new ContentValues();
                            cv.put("tstart",tstartEt.getText().toString());
                            cv.put("tend", tendEt.getText().toString());
                            ((DBOp) mContext).updateDB(id, cv);
                            ad.dismiss();
                        }
                    });
                    DeleteBt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ((DBOp) mContext).deleteDB(id);
                            ad.dismiss();
                        }
                    });
                    return true;
                }
            });



    }
}
