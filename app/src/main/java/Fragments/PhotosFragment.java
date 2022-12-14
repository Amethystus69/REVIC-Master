package Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.revic_capstone.R;
import com.example.revic_capstone.photo_view_page;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Adapters.AdapterPhotoItem;
import Models.Photos;
import Models.Posts;

public class PhotosFragment extends Fragment {

    private RecyclerView rv_photos;
    private AdapterPhotoItem adapterPhotoItem;
    private TextView tv_noPhotos;

    private DatabaseReference userDatabase, postsDatabase;
    private FirebaseUser user;

    private String userID, userIdFromSearch;

    private List<Posts> arrPosts = new ArrayList<Posts>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos, container, false);


        postsDatabase = FirebaseDatabase.getInstance().getReference("Posts");
        user = FirebaseAuth.getInstance().getCurrentUser();

        userIdFromSearch = getActivity().getIntent().getStringExtra("userID");

        if (userIdFromSearch != null) {

            userID = userIdFromSearch;
        }
        else
        {
            userID = user.getUid();
        }


        setRef(view);
        generateRecyclerLayout();

        return view;
    }



    private void generateRecyclerLayout() {
        rv_photos.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        rv_photos.setLayoutManager(gridLayoutManager);

        adapterPhotoItem = new AdapterPhotoItem(arrPosts, userIdFromSearch);
        rv_photos.setAdapter(adapterPhotoItem);

        getViewHolderValues();
    }

    private void getViewHolderValues() {


        Query query = postsDatabase
                .orderByChild("fileType")
                .equalTo("photo");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Posts posts = dataSnapshot.getValue(Posts.class);
                    String postsUserId = posts.getUserId();

                    if(userID.equals(postsUserId))
                    {
                        arrPosts.add(posts);
                    }

                }

                if(arrPosts.isEmpty())
                {
                    rv_photos.setVisibility(View.GONE);
                    tv_noPhotos.setVisibility(View.VISIBLE);
                }


                adapterPhotoItem.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void setRef(View view) {

        rv_photos = view.findViewById(R.id.rv_photos);
        tv_noPhotos = view.findViewById(R.id.tv_noPhotos);
    }
}