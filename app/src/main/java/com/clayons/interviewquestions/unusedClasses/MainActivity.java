package com.clayons.interviewquestions.unusedClasses;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.clayons.interviewquestions.Constants;
import com.clayons.interviewquestions.R;

import java.util.ArrayList;

/**
 *
 * The goal of this exercise is to run a functional application that is architected well. It should show a list of {@link Person} items,
 * and based on user input, modifying the item. After completing, show the result on the emulator.
 * Feel free to consult web sites/ personal projects etc.
 *
 * Part I. Show a list of people and details.
 * - Show name, a heart icon, and an avatar image as a list on as part of the MainActivity.
 * - When clicking on an item, all information is displayed on DetailActivity with the ability favorite an item
 * - In addition, create a "Favorite" button on the detail page - this should change the drawable image from one to the other on the main list.
 *
 * - There are screen shots for how the layouts are supposed to look in the root directory.
 *
 *
 * Part II. show a list of items as a GET API Request to server using any kind of framework you know.
 * - Discuss how you would display and use the information retrieved from the below endpoint
 *      http://jsonplaceholder.typicode.com/users
 * - Discuss how to handle network errors
 * - Discuss how to model the object
 *
 *
 * WE PREFER FUNCTIONALITY FIRST, LAYOUT LAST
 *
 * <p/>
 * bonus points for:
 *  adding any test cases, frameworks, and code relevant
 *  using MVP structure.
 *  suggesting/using up-to-date animation and transition effects.
 *  using third party libraries.
 */

public class MainActivity extends AppCompatActivity {

    private RecyclerView messages_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        messages_list = (RecyclerView) findViewById(R.id.messages_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        messages_list.setLayoutManager(linearLayoutManager);

        updateMessageListAdapter(initPerson());
    }

    private void updateMessageListAdapter(ArrayList<Person> items) {
        MessagesListAdapter adapter = new MessagesListAdapter(this, items);
        messages_list.setAdapter(adapter);
    }

    public ArrayList<Person> initPerson() {
        final ArrayList<Person> persons = new ArrayList<>();
        persons.add(new Person("Shubhanshu", "Yadav", 5, "111-222-3337", "http://www.max2.com/img/SHUBHANSHU.png", false));
        persons.add(new Person("Atesh", "Yurdakul", 5, "111-222-3337", "http://www.max2.com/img/ATESH.png", false));
        persons.add(new Person("Daniel", "Yurdakul", 5, "111-222-3337", "http://www.max2.com/img/DANIEL.png", false));
        persons.add(new Person("Pranav", "Bhalla", 5, "111-222-3337", "http://www.max2.com/img/PRANAV.png", false));
        persons.add(new Person("Rohan", "Nagrani", 20, "111-222-3333", "http://www.max2.com/img/ROHAN.png", false));
        persons.add(new Person("Michael", "Salmasi", 30, "111-222-3334", "http://www.max2.com/img/MICHAEL.png", false));
        persons.add(new Person("Josh", "Williams", 24, "111-222-3335", "http://www.max2.com/img/josh.png", false));
        persons.add(new Person("Jing", "Guo", 15, "111-222-3336", "http://www.max2.com/img/jing.png", false));
        persons.add(new Person("Zhenyu", "Wen", 5, "111-222-3337", "http://www.max2.com/img/zhenyu.png", false));

        return persons;
    }

    /**
     * recycleView list adapter
     */
    private static class MessagesListAdapter extends RecyclerView.Adapter<MessagesListAdapter.MyViewHolder> {

        private Context context;
        private  ArrayList<Person> items;

        MessagesListAdapter(Context context, ArrayList<Person> items) {
            this.context = context;
            this.items = items;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private ImageView ivAvatarPerson;
            private TextView name;
            private ImageView ivIsLiked;

            MyViewHolder(View view) {
                super(view);
                ivAvatarPerson = (ImageView) view.findViewById(R.id.ivAvatarPerson);
                name = (TextView) view.findViewById(R.id.name);
                ivIsLiked = (ImageView) view.findViewById(R.id.ivIsLiked);
            }
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View v = layoutInflater.inflate(R.layout.item_person_summary, parent, false);
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final Person item = this.items.get(position);
            holder.ivAvatarPerson.setImageResource(R.drawable.ic_action_account_circle_40);
            holder.name.setText(item.getFirstName() + " " + item.getLastName());

            if(item.isLiked()) {
                holder.ivIsLiked.setImageResource(R.drawable.ic_heart_filled);
            } else {
                holder.ivIsLiked.setImageResource(R.drawable.ic_heart_outline);
            }

            holder.ivIsLiked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(item.isLiked()) {
                        item.setLiked(false);
                        holder.ivIsLiked.setImageResource(R.drawable.ic_heart_outline);
                    } else {
                        item.setLiked(true);
                        holder.ivIsLiked.setImageResource(R.drawable.ic_heart_filled);
                    }
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(getDetailIntent(item));
                }
            });
        }

        @Override
        public int getItemCount() {
            return this.items.size();
        }

        private Intent getDetailIntent(Person person) {
            Intent intent = new Intent(this.context, DetailActivity.class);
            intent.putExtra(Constants.BUNDLE_KEY_PERSON, person);
            return intent;
        }

    }


}
