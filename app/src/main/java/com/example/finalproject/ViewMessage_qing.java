package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

public class ViewMessage_qing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_message_qing);

        Bundle dataToPass = new Bundle();
        dataToPass.putString(ForCurrency_qing.ITEM_MESSAGE, getIntent().getStringExtra("Message") );
        dataToPass.putInt(ForCurrency_qing.ITEM_FLAG1, getIntent().getIntExtra("Flag1",0));
        dataToPass.putInt(ForCurrency_qing.ITEM_FLAG2, getIntent().getIntExtra("Flag2",0));
        dataToPass.putInt(ForCurrency_qing.ITEM_POSITION, getIntent().getIntExtra("Position",0));
        dataToPass.putLong(ForCurrency_qing.ITEM_ID, getIntent().getLongExtra("Id",0));
        DetailFragmentQing dFragment = new DetailFragmentQing(); //add a DetailFragment
        dFragment.setArguments( dataToPass ); //pass it a bundle for information
        dFragment.setTablet(false);  //tell the fragment if it's running on a tablet or not
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentLocation, dFragment) //Add the fragment in FrameLayout
                .addToBackStack("Back") //make the back button undo the transaction
                .commit(); //actually load the fragment.

    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_message_qing);
//
//
//        Intent previousPage = getIntent();
//        int flag1=previousPage.getIntExtra("Flag1",0);
//        int flag2=previousPage.getIntExtra("Flag2",0);
//        String message = previousPage.getStringExtra("Message");
//        String id = previousPage.getStringExtra("Id");
//
//        EditText result = (EditText)findViewById(R.id.result);
//        ImageView imageView1=(ImageView)findViewById(R.id.imageViewFlag1);
//        ImageView imageView2=(ImageView)findViewById(R.id.imageViewFlag2);
//
//        result.setText(message);
//        imageView1.setImageResource(flag1);
//        imageView2.setImageResource(flag2);
////        imageView1.setImageResource(flag1);
//
//    }
}
