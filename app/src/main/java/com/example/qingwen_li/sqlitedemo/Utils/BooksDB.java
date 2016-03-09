package com.example.qingwen_li.sqlitedemo.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Qingwen_li on 2016/3/3.
 */
public class BooksDB extends SQLiteOpenHelper {
    //用于输出的tag
    private  final static String TAG="BOOKDB extends helper";
    //数据库的名字
    private final static String DATABASE_NAME="BOOK.db";
    //数据库的版本号
    private  final static int DATABASE_VERSION=1;
    //数据库表的名字
    private final static String TABLE_NAME="book_table";
    //表中数据一列的名字id
    private final static String BOOK_ID="book_ID";
    //表中数据的列名name
    private final static String BOOK_NAME="book_name";
    //表中数据的列名作者
    private final static String BOOK_AUTHOR="bool_author";

    //创建表的操作
        /*
        * 创建表注意在TABLE之后有空格，这个地方是利用了数据库创建语句
        * CREAT TABLE (our table name),如果没有空格，就变成了CREAT　TABLE(our tabel name)
        * 会报错
        * */
   public static final String CREAtE_BOOK="CREATE TABLE "+TABLE_NAME+" ("+BOOK_ID
            +" INTEGER primary key autoincrement, "+BOOK_NAME+
            " text, "+BOOK_AUTHOR+" text )";

    public BooksDB(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREAtE_BOOK);

        Log.d(TAG,"oncreat");
    }

    @Override
    /*
    * 升级数据库
    * */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         String sql="DROP TABLE IF EXISTS "+TABLE_NAME;
         db.execSQL(sql);
         onCreate(db);
    }

    /*
    * 得到一个索引应该是
    * */
    public Cursor select(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_NAME,null,null,null,null,null,null);
        return cursor;
    }

    /*
    * 插入操作
    * 根据我们的表中的数据和插入的时候需要的参数进行设定insert要用contentvalues
    * @name:列中的元素值
    * @author：列中作者名字
    * */
    public long insert(String name,String author){
    SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(BOOK_NAME,name);
        cv.put(BOOK_AUTHOR,author);
        // 这里的insert要包含这个表单中的所有的列，如果我们的数据中不包含这个参数，null
        long raw=db.insert(TABLE_NAME, null, cv);
        return  raw;
    }

    /*
    * 删除操作
    * */
    public void delete(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        String where=BOOK_ID+"=?";
        String[] whereValue={Integer.toString(id)};
        db.delete(TABLE_NAME,where,whereValue);
    }

    /*
    * 修改操作
    * */
    public void update(int id,String bookname,String author){
        SQLiteDatabase db=this.getWritableDatabase();
        String where=BOOK_ID+"=?";
        String[] whereValue={Integer.toString(id)};

        ContentValues cv=new ContentValues();
        cv.put(BOOK_NAME,bookname);
        cv.put(BOOK_AUTHOR,author);

        db.update(TABLE_NAME,cv,where,whereValue);
    }










}
