package com.example.qingwen_li.sqlitedemo.Activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.qingwen_li.sqlitedemo.Adapters.BooksListAdapter;
import com.example.qingwen_li.sqlitedemo.R;
import com.example.qingwen_li.sqlitedemo.Utils.BooksDB;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {
    //数据库对象
    private BooksDB mBooksDB;
    //
    private Cursor mCursor;
    //书名的输入框
    private EditText et_bookName;
    //作者的输入框
    private EditText et_bookAuthor;
    //书的显示列表
    private ListView lv_books;

    private int BOOK_ID=0;

    //adapter对象
    private BooksListAdapter mbookadapter;

    /*
    * 创建了一个菜单选项
    * */
    protected  final static int MENU_ADD= Menu.FIRST;
    protected  final static int MENU_DELETE=Menu.FIRST+1;
    protected  final static int MENU_UPDATE=Menu.FIRST+2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    /*
    * 初始化控件
    * */
    private void initViews() {
        mBooksDB=new BooksDB(this);
        mCursor=mBooksDB.select();

        et_bookName= (EditText) findViewById(R.id.ed_bookname);
        et_bookAuthor= (EditText) findViewById(R.id.ed_bookauthor);
        lv_books= (ListView) findViewById(R.id.lv_bookslist);

        mbookadapter=new BooksListAdapter(this,mCursor);

        //设置适配器
        lv_books.setAdapter(mbookadapter);
        lv_books.setOnItemClickListener(this);
    }

    /*
    * 创建菜单选项框
    * */
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);

        menu.add(Menu.NONE,MENU_ADD,0,"ADD");
        menu.add(Menu.NONE,MENU_DELETE,0,"DELETE");
        menu.add(Menu.NONE, MENU_UPDATE, 0, "UPDATE");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case MENU_ADD:
                add();
                break;
            case MENU_DELETE:
                delete();
                break;
            case MENU_UPDATE:
                update();
                break;
        }
        return true;
    }
    public void add(){
        String bookname=et_bookName.getText().toString();
        String bookauthor=et_bookAuthor.getText().toString();
        //如果两者有人为空，则返回
        if("".equals(bookname)||"".equals(bookauthor)){
            return;
        }
        mBooksDB.insert(bookname, bookauthor);
        //使用异步同步数据
        mCursor.requery();
  //      new ReFreshList().execute();
        lv_books.invalidateViews();
        et_bookName.setText("");
        et_bookAuthor.setText("");
        Toast.makeText(this,"Add successed!",Toast.LENGTH_LONG).show();
    }

    public void delete(){
        if(BOOK_ID==0){
            return;
        }
        mBooksDB.delete(BOOK_ID);
        mCursor.requery();
     //   new ReFreshList().execute();
        lv_books.invalidateViews();
        et_bookName.setText("");
        et_bookAuthor.setText("");
        Toast.makeText(this,"Delete successed!",Toast.LENGTH_LONG).show();
    }

    public void update() {
        String bookname = et_bookName.getText().toString();
        String bookauthor = et_bookAuthor.getText().toString();

        if ("".equals(bookname) || "".equals(bookauthor)) {
            return;
        }

        mBooksDB.update(BOOK_ID, bookname, bookauthor);
       // new ReFreshList().execute();m
        mCursor.requery();
        lv_books.invalidateViews();
        et_bookName.setText("");
        et_bookAuthor.setText("");
        Toast.makeText(this, "Update successed!", Toast.LENGTH_LONG).show();

    }



 /*   //异步后台同步数据
    private  class ReFreshList extends AsyncTask<Void ,Void,Cursor>{

        @Override
        protected Cursor doInBackground(Void... params) {
            Cursor newCursor=mBooksDB.getWritableDatabase().rawQuery("SELECT_id,Name,Weight from mytable ORDER BY Weight", null);
            return newCursor;
        }

        protected  void OnPostExecute(Cursor newCursor){
            mbookadapter.changeCursor(newCursor);
            mCursor.close();
            mCursor=newCursor;
        }
    }*/

    /*
    * 给listview的每个条目添加自己的监听事件，实现了AdapterView的OnItemClickListener接口
    * */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mCursor.moveToPosition(position);
        BOOK_ID=mCursor.getInt(0);
        et_bookName.setText(mCursor.getString(1));
        et_bookAuthor.setText(mCursor.getString(2));
    }

    //给每个iterm添加监听事件


}
