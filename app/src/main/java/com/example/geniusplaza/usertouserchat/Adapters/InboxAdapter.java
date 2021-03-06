package com.example.geniusplaza.usertouserchat.Adapters;



/**
 * Created by geniusplaza on 7/12/17.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.geniusplaza.usertouserchat.MainActivity;
import com.example.geniusplaza.usertouserchat.Model.InboxObj;
import com.example.geniusplaza.usertouserchat.Model.User;
import com.example.geniusplaza.usertouserchat.R;
import com.example.geniusplaza.usertouserchat.UserPageActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

import static android.content.Context.NOTIFICATION_SERVICE;


public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.ViewHolder> {

    private ArrayList<InboxObj> mData;
    private Context mContext;
    private DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
    private SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private SimpleDateFormat dateFormat1=new SimpleDateFormat("MMM dd,EEE HH:mm");
    public User receiverUser;
    ArrayList<User> receiverUserPassed = new ArrayList<>();
    InboxInterface inboxInterfaceListener;
    public int counter = 0;
    Random random = new Random();
    int m = random.nextInt(9999 - 1000) + 1000;
    private static final int NOTIFICATION_ID = 1;
    FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
    public InboxAdapter(final ArrayList<InboxObj> mData, Context mContext) {
        mDatabase.child("users").child(firebaseAuth.getCurrentUser().getUid()).child("inboxObjs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mData.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    InboxObj inboxObj = ds.getValue(InboxObj.class);
                    Log.d("DB Snapshot: ", "inboxMsg " + inboxObj.getSenderID().toString());
                    mData.add(inboxObj);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.d("demo","inConstructor");
        this.mData = mData;
        this.mContext = mContext;
        inboxInterfaceListener = (InboxInterface) mContext;
        Log.d("Data array in adapter", mData.toString());
        Log.d("Size", String.valueOf(mData.size()));
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("demo","onside onCreateViewHolder");
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.inbox_layout, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d("demo","onBindVieInboxAdapter"+mData.get(position).toString());
        Log.d("Position",String.valueOf(position));
        final InboxObj inboxObj=mData.get(position);
        Log.d("inbox objects order:", inboxObj.toString());
        final ImageView inboxUserPic=holder.inboxUserPic;
        final TextView inboxUserName=holder.inboxUserName;
        TextView inboxMsgTimeStamp=holder.inboxMsgTimeStamp;
        TextView inboxMsgContent=holder.inboxMsgContent;
        ImageView inboxMsgIsRead=holder.inboxMsgIsRead;
        View container=holder.container;


        if (inboxObj.getSenderID().equals(firebaseAuth.getCurrentUser().getUid())){
            Log.d("demo","bindHolder sender matched");

            mDatabase.child("users").child(inboxObj.getReceiverID()).child("profile").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    receiverUser = dataSnapshot.getValue(User.class);
                    Log.d("demo","receiverUserprofile"+receiverUser.toString());
                    inboxUserName.setText(receiverUser.getFirstName()+" "+receiverUser.getLastName());
                    receiverUserPassed.add(receiverUser);
                    if(receiverUser.getUserPicUrl() != null && !receiverUser.getUserPicUrl().isEmpty()){
                        Picasso.with(getContext()).load(receiverUser.getUserPicUrl()).into(inboxUserPic);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("ONCLICK POSITION",String.valueOf(position));
                    Log.d("ONCLICK Sender Id", inboxObj.getSenderID());
                    Log.d("ONCLICK Receiver", inboxObj.getReceiverID());
                    Log.d("ONCLICK receiverUser", receiverUser.getUid());
                    Log.d("ONCLICK receiver Name", receiverUser.getFirstName());


                    inboxInterfaceListener.onItemClick(receiverUserPassed.get(position));

                }
            });
        }else {
            Log.d("demo","bindHolder receiver matched");
            mDatabase.child("users").child(inboxObj.getSenderID()).child("profile").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    receiverUser = dataSnapshot.getValue(User.class);
                    Log.d("demo","receiverUser  profile"+receiverUser.getUserPicUrl());
                    inboxUserName.setText(receiverUser.getFirstName()+" "+receiverUser.getLastName());
                    receiverUserPassed.add(receiverUser);

                    if(inboxObj.getIslastMsgSeen() == false) {
                        ++counter;
                        popUpNotification(counter);
                    }

                    if(!receiverUser.getUserPicUrl().isEmpty()){
                        Log.d("demo","inboxpicnotnul");
                        Picasso.with(getContext()).load(receiverUser.getUserPicUrl()).into(inboxUserPic);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.d("Inbox ObjectSelected",inboxObj.toString());
                    Log.d("demo","clickedInboxItem");
                    InboxObj current_object = inboxObj;
                    current_object.setIslastMsgSeen(true);
                    Log.d("Onclick Username: ", receiverUser.getFirstName());
                    mDatabase.child("users").child(firebaseAuth.getCurrentUser().getUid()).child("inboxObjs").child(receiverUserPassed.get(position).getUid()).setValue(current_object);

                    Log.d("ONCLICK POSITION",String.valueOf(position));
                    Log.d("ONCLICK Sender Id", inboxObj.getSenderID());
                    Log.d("ONCLICK Receiver", inboxObj.getReceiverID());
                    Log.d("ONCLICK receiverUser", receiverUser.getUid());
                    Log.d("ONCLICK receiver Name", receiverUser.getFirstName());
                    counter--;

                    inboxInterfaceListener.onItemClick(receiverUserPassed.get(position));

                }
            });
        }

        inboxMsgContent.setText(inboxObj.getLastMsg().getMsgContent());
        Log.d("demo","islastmsgseen"+inboxObj.getIslastMsgSeen());
        if (inboxObj.getIslastMsgSeen()){
            inboxMsgIsRead.setVisibility(View.GONE);
        }else {
            inboxMsgIsRead.setVisibility(View.VISIBLE);
        }
        Log.d("demo","lastMsgTimeStamp"+inboxObj.getLastMsg().getMsgTimeStamp());
        try {
            inboxMsgTimeStamp.setText(dateFormat1.format(dateFormat.parse(inboxObj.getLastMsg().getMsgTimeStamp())));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView inboxUserPic;
        TextView inboxUserName;
        TextView inboxMsgTimeStamp;
        TextView inboxMsgContent;
        ImageView inboxMsgIsRead;
        View container;

        public ViewHolder(View itemView) {
            super(itemView);
            Log.d("demo","ViewHolder");
            container= (View) itemView.findViewById(R.id.rowItemContainer);
            inboxUserPic= (ImageView) itemView.findViewById(R.id.imageViewInboxUserPic);
            inboxUserName= (TextView) itemView.findViewById(R.id.textViewInboxUserName);
            inboxMsgTimeStamp= (TextView) itemView.findViewById(R.id.textViewInboxTimeStamp);
            inboxMsgContent= (TextView) itemView.findViewById(R.id.textViewInboxUserMessage);
            inboxMsgIsRead= (ImageView) itemView.findViewById(R.id.imageViewMsgIsRead);
        }
    }

    public interface InboxInterface{
        public void onItemClick(User user);
    }

    public void popUpNotification(int msgCount) {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(mContext)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle("You Have A New Message!")
                        .setContentText("You have:" + String.valueOf(msgCount) + "messages!")
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setPriority(Notification.PRIORITY_HIGH);


        NotificationManager notificationManager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(m /*ID of notification*/ , mBuilder.build());
        notificationManager.cancel(m);
    }
}