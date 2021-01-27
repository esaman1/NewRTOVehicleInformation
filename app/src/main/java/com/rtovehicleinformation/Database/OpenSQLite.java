package com.rtovehicleinformation.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rtovehicleinformation.Model.MileageModel;
import com.rtovehicleinformation.Model.VehicleExpenseModel;

import java.util.ArrayList;

public class OpenSQLite extends SQLiteOpenHelper {
    SQLiteDatabase database;

    public OpenSQLite(final Context context) {
        super(context, "Bill_reminder.db", null, 1);
        this.database = this.getWritableDatabase();
    }

    private VehicleExpenseModel add(final Cursor cursor) {
        final VehicleExpenseModel vehicleExpenseModel = new VehicleExpenseModel("catname", "payee", "amount", "notes", "duedate", 1);
        vehicleExpenseModel.setI1((int) cursor.getLong(cursor.getColumnIndexOrThrow("bid")));
        vehicleExpenseModel.setStr2(cursor.getString(cursor.getColumnIndexOrThrow("catname")));
        vehicleExpenseModel.setStr3(cursor.getString(cursor.getColumnIndexOrThrow("payee")));
        vehicleExpenseModel.setStr(cursor.getString(cursor.getColumnIndexOrThrow("amount")));
        vehicleExpenseModel.setStr4(cursor.getString(cursor.getColumnIndexOrThrow("notes")));
        vehicleExpenseModel.setStr5(cursor.getString(cursor.getColumnIndexOrThrow("duedate")));
        vehicleExpenseModel.setI(cursor.getInt(cursor.getColumnIndexOrThrow("caticon")));
        return vehicleExpenseModel;
    }

    private MileageModel addmileage(final Cursor cursor) {
        final MileageModel mileageModel = new MileageModel();
        mileageModel.setId((int) cursor.getLong(cursor.getColumnIndexOrThrow("id")));
        mileageModel.setFromKms(cursor.getString(cursor.getColumnIndexOrThrow("lreserve")));
        mileageModel.setToKms(cursor.getString(cursor.getColumnIndexOrThrow("creserve")));
        mileageModel.setPrice(cursor.getString(cursor.getColumnIndexOrThrow("price")));
        mileageModel.setFuel(cursor.getString(cursor.getColumnIndexOrThrow("fuel")));
        mileageModel.setNotedDate(cursor.getString(cursor.getColumnIndexOrThrow("date")));
        mileageModel.setMileage(cursor.getString(cursor.getColumnIndexOrThrow("km")));
        mileageModel.setPerKms(cursor.getString(cursor.getColumnIndexOrThrow("inr")));
        return mileageModel;
    }

    private VehicleExpenseModel set(final Cursor cursor) {
        final VehicleExpenseModel vehicleExpenseModel = new VehicleExpenseModel("catname", "payee", "amount", "notes", "duedate", 1);
        vehicleExpenseModel.setI1((int) cursor.getLong(cursor.getColumnIndexOrThrow("bid")));
        vehicleExpenseModel.setStr2(cursor.getString(cursor.getColumnIndexOrThrow("catname")));
        vehicleExpenseModel.setStr3(cursor.getString(cursor.getColumnIndexOrThrow("payee")));
        vehicleExpenseModel.setStr(cursor.getString(cursor.getColumnIndexOrThrow("amount")));
        vehicleExpenseModel.setStr4(cursor.getString(cursor.getColumnIndexOrThrow("notes")));
        vehicleExpenseModel.setStr5(cursor.getString(cursor.getColumnIndexOrThrow("duedate")));
        vehicleExpenseModel.setI(cursor.getInt(cursor.getColumnIndexOrThrow("caticon")));
        vehicleExpenseModel.setTotalAmount(this.expensetotalModels(cursor.getString(cursor.getColumnIndexOrThrow("duedate"))));
        vehicleExpenseModel.setExpenseModels(this.expenseModels(cursor.getString(cursor.getColumnIndexOrThrow("duedate"))));
        return vehicleExpenseModel;
    }

    private MileageModel setmileage(final Cursor cursor) {
        final MileageModel mileageModel = new MileageModel();
        mileageModel.setId((int) cursor.getLong(cursor.getColumnIndexOrThrow("id")));
        mileageModel.setFromKms(cursor.getString(cursor.getColumnIndexOrThrow("lreserve")));
        mileageModel.setToKms(cursor.getString(cursor.getColumnIndexOrThrow("creserve")));
        mileageModel.setPrice(cursor.getString(cursor.getColumnIndexOrThrow("price")));
        mileageModel.setFuel(cursor.getString(cursor.getColumnIndexOrThrow("fuel")));
        mileageModel.setNotedDate(cursor.getString(cursor.getColumnIndexOrThrow("date")));
        mileageModel.setMileage(cursor.getString(cursor.getColumnIndexOrThrow("km")));
        mileageModel.setPerKms(cursor.getString(cursor.getColumnIndexOrThrow("inr")));
        return mileageModel;
    }

    public int dlt(final int n) {
        return this.database.delete("bill", "bid = ?", new String[]{String.valueOf(n)});
    }

    public int dltmileage(final int n) {
        return this.database.delete("mileage", "id = ?", new String[] { String.valueOf(n) });
    }

    ArrayList<VehicleExpenseModel> expenseModels(final String s) {
        final Cursor query = this.database.query("bill", new String[]{"bid", "catname", "payee", "amount", "notes", "duedate", "caticon"}, "duedate=?", new String[]{s}, null, null, null);
        final ArrayList<VehicleExpenseModel> list = new ArrayList<VehicleExpenseModel>();
        if (query.moveToFirst()) {
            do {
                if (query.getString(query.getColumnIndexOrThrow("duedate")).equals(s)) {
                    list.add(this.add(query));
                }
            } while (query.moveToNext());
        }
        return list;
    }

    String expensetotalModels(final String s) {
        final Cursor query = this.database.query("bill", new String[]{"bid", "catname", "payee", "amount", "notes", "duedate", "caticon"}, "duedate=?", new String[]{s}, null, null, null);
        final boolean moveToFirst = query.moveToFirst();
        float n = 0.0f;
        float n2 = 0.0f;
        if (moveToFirst) {
            do {
                n = n2;
                if (query.getString(query.getColumnIndexOrThrow("duedate")).equals(s)) {
                    n = n2 + Float.parseFloat(query.getString(query.getColumnIndexOrThrow("amount")));
                }
                n2 = n;
            } while (query.moveToNext());
        }
        return String.valueOf(n);
    }

    public long insrt(final VehicleExpenseModel vehicleExpenseModel) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put("catname", vehicleExpenseModel.getStr2());
        contentValues.put("payee", vehicleExpenseModel.getStr3());
        contentValues.put("amount", vehicleExpenseModel.getStr());
        contentValues.put("notes", vehicleExpenseModel.getStr4());
        contentValues.put("duedate", vehicleExpenseModel.getStr5());
        contentValues.put("caticon", Integer.valueOf(vehicleExpenseModel.getI()));
        return this.database.insert("bill", null, contentValues);
    }

    public long insrtMileage(final MileageModel mileageModel) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put("lreserve", mileageModel.getFromKms());
        contentValues.put("creserve", mileageModel.getToKms());
        contentValues.put("price", mileageModel.getPrice());
        contentValues.put("fuel", mileageModel.getFuel());
        contentValues.put("date", mileageModel.getNotedDate());
        contentValues.put("km", mileageModel.getMileage());
        contentValues.put("inr", mileageModel.getPerKms());
        return this.database.insert("mileage", null, contentValues);
    }

    public ArrayList<VehicleExpenseModel> list() {
        final Cursor query = this.database.query("bill", new String[]{"bid", "catname", "payee", "amount", "notes", "duedate", "caticon"}, null, null, "duedate", null, null);
        final ArrayList<VehicleExpenseModel> list = new ArrayList<VehicleExpenseModel>();
        if (query.moveToFirst()) {
            do {
                list.add(this.set(query));
            } while (query.moveToNext());
        }
        return list;
    }

    public ArrayList<MileageModel> listmileage() {
        final Cursor query = this.database.query("mileage", new String[]{"id", "lreserve", "creserve", "price", "fuel", "date", "km", "inr"}, null, null, "date", null, null);
        final ArrayList<MileageModel> list = new ArrayList<MileageModel>();
        if (query.moveToFirst()) {
            do {
                list.add(this.setmileage(query));
            } while (query.moveToNext());
        }
        return list;
    }

    ArrayList<MileageModel> mileageModels(final String s) {
        final Cursor query = this.database.query("mileage", new String[]{"id", "lreserve", "creserve", "price", "fuel", "date", "km", "inr"}, "date=?", new String[]{s}, null, null, null);
        final ArrayList<MileageModel> list = new ArrayList<MileageModel>();
        if (query.moveToFirst()) {
            do {
                if (query.getString(query.getColumnIndexOrThrow("date")).equals(s)) {
                    list.add(this.addmileage(query));
                }
            } while (query.moveToNext());
        }
        return list;
    }

    public void onCreate(final SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE bill (bid INTEGER PRIMARY KEY autoincrement, catname TEXT, payee TEXT, amount TEXT, notes TEXT, duedate TEXT, caticon INTEGER )");
        sqLiteDatabase.execSQL("CREATE TABLE mileage (id INTEGER PRIMARY KEY autoincrement, lreserve INTEGER, creserve INTEGER, price INTEGER, fuel INTEGER, date TEXT, km TEXT, inr TEXT )");
    }

    public void onUpgrade(final SQLiteDatabase sqLiteDatabase, final int n, final int n2) {
        this.onCreate(sqLiteDatabase);
    }

    public String rawQuery() {
        final SQLiteDatabase database = this.database;
        String string = null;
        final Cursor rawQuery = database.rawQuery("SELECT SUM(amount) AS total FROM bill", null);
        if (rawQuery.moveToFirst()) {
            string = rawQuery.getString(rawQuery.getColumnIndex("total"));
        }
        rawQuery.close();
        return string;
    }

    public int updt(final int n, final VehicleExpenseModel vehicleExpenseModel) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put("catname", vehicleExpenseModel.getStr2());
        contentValues.put("payee", vehicleExpenseModel.getStr3());
        contentValues.put("amount", vehicleExpenseModel.getStr());
        contentValues.put("notes", vehicleExpenseModel.getStr4());
        contentValues.put("duedate", vehicleExpenseModel.getStr5());
        contentValues.put("caticon", Integer.valueOf(vehicleExpenseModel.getI()));
        return this.database.update("bill", contentValues, "bid = ?", new String[]{String.valueOf(n)});
    }

    public int updtMileage(final int n, final MileageModel mileageModel) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put("lreserve", mileageModel.getFromKms());
        contentValues.put("creserve", mileageModel.getToKms());
        contentValues.put("price", mileageModel.getPrice());
        contentValues.put("fuel", mileageModel.getFuel());
        contentValues.put("date", mileageModel.getNotedDate());
        contentValues.put("km", mileageModel.getMileage());
        contentValues.put("inr", mileageModel.getPerKms());
        return this.database.update("mileage", contentValues, "id = ?", new String[]{String.valueOf(n)});
    }
}
