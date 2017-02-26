package com.clayons.interviewquestions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.clayons.interviewquestions.models.User;
import com.clayons.interviewquestions.services.MyIntentService;
import com.clayons.interviewquestions.unusedClasses.DetailActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class APIActivity extends AppCompatActivity {

    @BindView(R.id.recyclerViewApiList)
    public RecyclerView recyclerViewApiList;

    private UsersListAdapter usersListAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        getUsers();
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_list);
        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewApiList.setLayoutManager(linearLayoutManager);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetUsers(ArrayList<User> users) {
        updateListView(users);
    }

    private void updateListView(ArrayList<User> users) {
        if(usersListAdapter == null) {
            usersListAdapter = new UsersListAdapter(this, users);
            recyclerViewApiList.setAdapter(usersListAdapter);
        } else {
            usersListAdapter.updateData(users);
        }
    }

    private void getUsers() {
        Intent intent = new Intent(this, MyIntentService.class);
        intent.setAction(MyIntentService.ACTION_GET_USERS);
        startService(intent);
    }

    private static class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.MyViewHolder> {

        private Context context;
        private  ArrayList<User> items;

        private BottomSheetDialog bottomSheetDialog;

        private EditText fullName;
        private EditText username;
        private EditText email;
        private EditText address;
        private EditText phone;
        private EditText website;

        UsersListAdapter(Context context, ArrayList<User> items) {
            this.context = context;
            this.items = items;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private ImageView ivAvatarPerson;
            private TextView name;
            private TextView email;

            MyViewHolder(View view) {
                super(view);
                ivAvatarPerson = (ImageView) view.findViewById(R.id.ivAvatarPerson);
                name = (TextView) view.findViewById(R.id.name);
                email = (TextView) view.findViewById(R.id.email);
            }
        }

        @Override
        public UsersListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View v = layoutInflater.inflate(R.layout.user_list_item, parent, false);
            return new UsersListAdapter.MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final UsersListAdapter.MyViewHolder holder, int position) {
            final User item = this.items.get(position);
            holder.ivAvatarPerson.setImageResource(R.drawable.ic_action_account_circle_40);
            holder.name.setText(item.getName());
            holder.email.setText(item.getEmail());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showBottomSheetDialog(item);
                }
            });
        }

        @Override
        public int getItemCount() {
            return this.items.size();
        }

        private void updateData(ArrayList<User> users) {
            this.items = users;
            notifyDataSetChanged();
        }

        private void showBottomSheetDialog(User user) {
            if(bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
                bottomSheetDialog.dismiss();
            }

            if(bottomSheetDialog == null) {
                bottomSheetDialog = new BottomSheetDialog(this.context);
                bottomSheetDialog.setCancelable(true);
                bottomSheetDialog.setCanceledOnTouchOutside(true);
                bottomSheetDialog.setContentView(R.layout.user_detail);

                fullName = (EditText) bottomSheetDialog.findViewById(R.id.fullName);
                username = (EditText) bottomSheetDialog.findViewById(R.id.username);
                email = (EditText) bottomSheetDialog.findViewById(R.id.email);
                address = (EditText) bottomSheetDialog.findViewById(R.id.address);
                phone = (EditText) bottomSheetDialog.findViewById(R.id.phone);
                website = (EditText) bottomSheetDialog.findViewById(R.id.website);
            }

            fullName.setText(user.getName());
            username.setText(user.getUsername());
            email.setText(user.getEmail());
            address.setText(user.getAddress().getStreet() + " "
                    + user.getAddress().getSuite() + "\n"
                    + user.getAddress().getCity() + " "
                    + user.getAddress().getZipcode());

            phone.setText(user.getPhone());
            website.setText(user.getWebsite());

            bottomSheetDialog.show();
        }

    }
}
