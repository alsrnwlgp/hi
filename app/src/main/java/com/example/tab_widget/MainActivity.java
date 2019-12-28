package com.example.tab_widget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Vector;
//되나 확인중....
public class MainActivity extends AppCompatActivity {

    private final int PERMISSIONS_READ_CONTACTS = 1000;
    private final int PERMISSIONS_READ_EXTERNAL_STORAGE = 1001;
    private final int PERMISSIONS_ACCESS_MEDIA_LOCATION = 1002;
    private boolean isPermission = false;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        callPermission();


        TabHost tabHost1 = findViewById(R.id.tabHost1) ;
        tabHost1.setup() ;//setup()함수를 호출하지 않으면 TabWidget이 정상적으로 표시되지 않는다

        // 첫 번째 Tab. (탭 표시 텍스트:"TAB 1"), (페이지 뷰:"content1")
        TabHost.TabSpec ts1 = tabHost1.newTabSpec("Tab Spec 1") ;
        ts1.setContent(R.id.content1) ;
        ts1.setIndicator("TAB 1") ;
        tabHost1.addTab(ts1)  ;

        // 두 번째 Tab. (탭 표시 텍스트:"TAB 2"), (페이지 뷰:"content2")
        TabHost.TabSpec ts2 = tabHost1.newTabSpec("Tab Spec 2") ;
        ts2.setContent(R.id.content2) ;
        ts2.setIndicator("TAB 2") ;
        tabHost1.addTab(ts2) ;

        // 세 번째 Tab. (탭 표시 텍스트:"TAB 3"), (페이지 뷰:"content3")
        TabHost.TabSpec ts3 = tabHost1.newTabSpec("Tab Spec 3") ;
        ts3.setContent(R.id.content3) ;
        ts3.setIndicator("TAB 3") ;
        tabHost1.addTab(ts3) ;

        //for tab1
        Vector<String> LIST_MENU = new Vector<>();
        ArrayList<ContactItem> tmp = getContactList();

        for(int i = 0; i < tmp.size(); i++) {
            LIST_MENU.add(i, tmp.get(i).getUser_Name() + " " + tmp.get(i).getUser_phNumber());
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, LIST_MENU);

        ListView listView = findViewById(R.id.listview1);
        listView.setAdapter(adapter);

        //for tab2
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        ArrayList<Item> items = new ArrayList<>();

        items.add(new Item(R.mipmap.dog1, "dog"));
        items.add(new Item(R.mipmap.dog2, "dog "));
        items.add(new Item(R.mipmap.dog3, "dog "));
        items.add(new Item(R.mipmap.dog4, "dog "));
        items.add(new Item(R.mipmap.dog5, "dog "));
        items.add(new Item(R.mipmap.dog6, "dog "));
        items.add(new Item(R.mipmap.dog7, "dog "));
        items.add(new Item(R.mipmap.dog8, "dog "));

        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyAdapter(items, getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);

    }

    public ArrayList<ContactItem> getContactList(){
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = new String[]{
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_ID,
                ContactsContract.Contacts._ID
        };
        String[] selectionArgs = null;
        String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME +
                " COLLATE LOCALIZED ASC";
        Cursor cursor = getContentResolver().query(uri, projection, null, selectionArgs, sortOrder);
        LinkedHashSet<ContactItem> hashlist = new LinkedHashSet<>();
        if (cursor.moveToFirst()){
            do{
                long photo_id = cursor.getLong(2);
                long person_id = cursor.getLong(3);
                ContactItem contactItem = new ContactItem();
                contactItem.setUser_phNumber(cursor.getString(0));
                contactItem.setUser_Name(cursor.getString(1));
                contactItem.setPhoto_id((photo_id));
                contactItem.setPerson_id((person_id));

                hashlist.add(contactItem);
            }while (cursor.moveToNext());
        }
        ArrayList<ContactItem> contactItems = new ArrayList<>(hashlist);
        for (int i = 0; i < contactItems.size(); i++){
            contactItems.get(i).setId(i);
        }
        return contactItems;

    }


    private void callPermission() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_READ_CONTACTS);

        } else {
            isPermission = true;
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_READ_EXTERNAL_STORAGE);
        }else{
            isPermission = true;
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_MEDIA_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.ACCESS_MEDIA_LOCATION}, PERMISSIONS_ACCESS_MEDIA_LOCATION);
        }else{
            isPermission = true;
        }
    }
}