package com.example.nich_sez.gymadmin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
/**
 * This Holds data of Meal Diet program and makes it ready to show in a recycler view.
 * https://www.youtube.com/watch?v=a4o9zFfyIM4
 * https://www.simplifiedcoding.net/android-recyclerview-cardview-tutorial/
 */
public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {

    //We want context to get layout from it !! :/
    private Context mCtx;

    private List<User> userList;

    public UserListAdapter(Context mCtx, List<User> userList) {
        this.mCtx = mCtx;
        this.userList = userList;
    }


    //Let's override create view holder
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflating layout from context
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        //TODO:maybe changing null to parent?! https://stackoverflow.com/questions/30691150/match-parent-width-does-not-work-in-recyclerview
        View view = inflater.inflate(R.layout.users_list_item_layout, parent,false);
        //Finally creating view holder

        /**
         * For perfomance we combine these lines.
         * MealDietListViewHolder vholder = new MealDietListViewHolder(view);
         * return vholder;
         */
        return new UserListAdapter.UserViewHolder(view);

    }

    //Supress is not necessary I hate warnings so I Suppress it
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.UserViewHolder holder, int position) {
        //Now we are setting each row element data
        User user = userList.get(position);

        holder.txtNameFamily.setText(user.getName() + " " + user.getFamily());
        holder.txtAddress.setText(user.getAddress());
        holder.txtID.setText(user.getId());
        holder.txtLockerCode.setText(user.getLockerCode());
        holder.txtNationalCode.setText(user.getNationalCode());
        holder.txtTel.setText(user.getTel());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{

        //TODO:Image
        TextView txtNameFamily,txtAddress,txtID,txtLockerCode,txtNationalCode,txtTel;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            //Finding list elements
            //TODO: ID

            txtNameFamily = itemView.findViewById(R.id.txtNameFamily);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtID = itemView.findViewById(R.id.txtID);
            txtLockerCode = itemView.findViewById(R.id.txtLockerCode);
            txtNationalCode = itemView.findViewById(R.id.txtNationalCode);
            txtTel = itemView.findViewById(R.id.txtTel);

        }
    }
}
