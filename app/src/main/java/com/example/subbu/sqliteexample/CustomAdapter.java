package com.example.subbu.sqliteexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by subbu on 30-09-2017.
 */

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private List<Contact> contactsList;
    private LayoutInflater inflater;
    public CustomAdapter(Context context, List<Contact> contactList)
    {
        this.contactsList = contactList;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return contactsList.size();
    }

    @Override
    public Object getItem(int i) {
        return contactsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View view1=null;
        ViewHolder holder=new ViewHolder();
        if(view1 == null)
        {
            view1 = inflater.inflate(R.layout.item_layout,null);

            holder.personName = (TextView) view1.findViewById(R.id.person_name);
            holder.mobileNumber = (TextView) view1.findViewById(R.id.mobile_number_text);
            holder.profilePic = (ImageView) view1.findViewById(R.id.profile_pic_image);

            view1.setTag(holder);

        }
        else
        {
            holder = (ViewHolder) view1.getTag();
        }

        holder.personName.setText(contactsList.get(i).getName());
        holder.mobileNumber.setText(contactsList.get(i).getPhoneNumber());
        holder.profilePic.setImageBitmap(Utils.getImage(contactsList.get(i).getProfilePic()));
        return view1;
    }


    class ViewHolder
    {
        private TextView personName,mobileNumber;
        private ImageView profilePic;
    }
}
