package com.rtovehicleinformation.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.rtovehicleinformation.Model.RTOListInfo;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper
{
    public static final String DBLOCATION = "/data/data/com.rtovehicleinformation/databases/";
    public static final String DBNAME = "rto_database.db";
    Context context;
    SQLiteDatabase database;

    public DatabaseHelper(final Context context) {
        super(context, "rto_database.db", null, 1);
        this.context = context;
    }

    public void closeDatabase() {
        final SQLiteDatabase database = this.database;
        if (database != null) {
            database.close();
        }
    }

    public List<RTOListInfo> getListData() {
        final ArrayList<RTOListInfo> list = new ArrayList<RTOListInfo>();
        this.openDatabase();
        final Cursor rawQuery = this.database.rawQuery("SELECT * FROM rto_data", null);
        rawQuery.moveToFirst();
        while (!rawQuery.isAfterLast()) {
            list.add(new RTOListInfo(rawQuery.getString(1), rawQuery.getString(2), rawQuery.getString(3), rawQuery.getString(4)));
            rawQuery.moveToNext();
        }
        rawQuery.close();
        this.closeDatabase();
        return list;
    }

    public void onCreate(final SQLiteDatabase sqLiteDatabase) {
    }

    public void onUpgrade(final SQLiteDatabase sqLiteDatabase, final int n, final int n2) {
    }

    public void openDatabase() {
        final String path = this.context.getDatabasePath("rto_database.db").getPath();
        final SQLiteDatabase database = this.database;
        if (database != null && database.isOpen()) {
            return;
        }
        this.database = SQLiteDatabase.openDatabase(path, null, 0);
    }
}
