package com.example.whatsappclone.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappclone.Model.MessageModel;
import com.example.whatsappclone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatAdapter extends  RecyclerView.Adapter{


    ArrayList<MessageModel> messageModels;
    Context context;
    String recId;

    int SENDER_VIEW_TYPE=1;
    int RECEIVER_VIEW_TYPE=2;

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context, String recId) {
        this.messageModels = messageModels;
        this.context = context;
        this.recId = recId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

      if(viewType==SENDER_VIEW_TYPE)
      {
          View view= LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);

          return  new SenderViewHoder(view);
      }
      else
      {
          View view= LayoutInflater.from(context).inflate(R.layout.sample_reciver,parent,false);

          return  new RecieverViewHoder(view);

      }

    }

    @Override
    public int getItemViewType(int position) {

        if(messageModels.get(position).getuId().equals(FirebaseAuth.getInstance().getUid()))
        {
            return SENDER_VIEW_TYPE;
        }
        else
        {
            return RECEIVER_VIEW_TYPE;
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MessageModel messageModel=messageModels.get(position);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Are you sure ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                FirebaseDatabase database=FirebaseDatabase.getInstance();
                                String sender=FirebaseAuth.getInstance().getUid() + recId;

                                database.getReference().child("chats").child(sender)
                                        .child(messageModel.getMessageId())
                                        .setValue(null);

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();
                    }
                }).show();


                return false;
            }
        });


        if(holder.getClass()== SenderViewHoder.class)
        {
            ((SenderViewHoder)holder).senderMsg.setText(messageModel.getMessage());
        }

        else
        {
            ((RecieverViewHoder)holder).reciverMsg.setText(messageModel.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }



    public  class RecieverViewHoder extends RecyclerView.ViewHolder {

        TextView  reciverMsg,reciverTime;

        public RecieverViewHoder(@NonNull View itemView) {
            super(itemView);

            reciverMsg=itemView.findViewById(R.id.reciverText);
            reciverTime=itemView.findViewById(R.id.reciverTime);


        }
    }


    public  class SenderViewHoder extends  RecyclerView.ViewHolder {

        TextView senderMsg,senderTime;

        public SenderViewHoder(@NonNull View itemView) {
            super(itemView);

            senderMsg=itemView.findViewById(R.id.senderText);
            senderTime=itemView.findViewById(R.id.senderTime);

        }
    }


}
