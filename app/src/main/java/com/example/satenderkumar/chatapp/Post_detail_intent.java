package com.example.satenderkumar.chatapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.satenderkumar.chatapp.R;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.satenderkumar.chatapp.Adapter.PostAdapter;
import com.example.satenderkumar.chatapp.Model.Post;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Post_detail_intent extends AppCompatActivity {
    String postid;

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> postList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail_intent);
        Bundle extras = getIntent().getExtras();
        postid = extras.getString("postid", "none");

        recyclerView = this.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        postList = new ArrayList<>();
        postAdapter = new PostAdapter(this, postList);
        recyclerView.setAdapter(postAdapter);

        readPost();

    }

        private void readPost(){
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts").child(postid);

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    postList.clear();
                    Post post = dataSnapshot.getValue(Post.class);
                    postList.add(post);

                    postAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

