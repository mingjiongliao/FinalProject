//package com.example.finalproject;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.TextView;
//
//import androidx.fragment.app.Fragment;
//
//
//public class DetailFragment extends Fragment {
//
//    private boolean isTablet;
//    private Bundle dataFromActivity;
//    private long id;
//    private int position;
//
//    public void setTablet(boolean tablet) { isTablet = tablet; }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        dataFromActivity = getArguments();
//        id = dataFromActivity.getLong(ForCurrency_qing.ITEM_ID );
//        position = dataFromActivity.getInt(ForCurrency_qing.ITEM_POSITION );
//
//        // Inflate the layout for this fragment
//        View result =  inflater.inflate(R.layout.fragment_detail, container, false);
//
//        //show the message
//        TextView message = (TextView)result.findViewById(R.id.message);
//        message.setText(dataFromActivity.getString(ForCurrency_qing.ITEM_SELECTED));
//
//        //show the id:
//        TextView idView = (TextView)result.findViewById(R.id.idText);
//        idView.setText("ID=" + id);
//
//        // get the delete button, and add a click listener:
//        Button deleteButton = (Button)result.findViewById(R.id.deleteButton);
//        deleteButton.setOnClickListener( clk -> {
//
//            if(isTablet){
//                ChatRoomActivity parent = (ChatRoomActivity)getActivity();
//                parent.deleteMessageId(id); //this deletes the item and updates the list
//
//
//                //now remove the fragment since you deleted it from the database:
//                // this is the object to be removed, so remove(this):
//                parent.getSupportFragmentManager().beginTransaction().remove(this).commit();
//            }else{
//                ViewMessage_qing parent = (ViewMessage_qing) getActivity();
//                Intent backToFragmentExample = new Intent();
//                backToFragmentExample.putExtra(ForCurrency_qing.ITEM_ID, dataFromActivity.getLong(ForCurrency_qing.ITEM_ID ));
//
//                parent.setResult(Activity.RESULT_OK, backToFragmentExample); //send data back to FragmentExample in onActivityResult()
//                parent.finish(); //go back
//            }
//
//
//        });
//        return result;
//    }
//}
//
