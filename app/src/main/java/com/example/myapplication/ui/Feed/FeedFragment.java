package com.example.myapplication.ui.Feed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentFeedBinding;

import java.util.ArrayList;

public class FeedFragment extends Fragment {


    private FragmentFeedBinding binding;
    private FeedAdapter feedAdapter;
    private ArrayList<FeedItem> allPosts = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FeedViewModel feedViewModel = new ViewModelProvider(this).get(FeedViewModel.class);

        binding = FragmentFeedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setupAllPostsList();
        setupRecyclerView();

        binding.filterGroup.setOnCheckedChangeListener((group, checkedId) -> {
            filterData(checkedId);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void filterData(int checkedId) {

        ArrayList<FeedItem> filteredPosts = new ArrayList<>();

        if (checkedId == R.id.filterFollowing) {
            for (FeedItem item : allPosts) {
                if (checkFollowing(item)) {
                    filteredPosts.add(item);
                }
            }
            System.out.println("Following Selected!");
        } else if (checkedId == R.id.filterFriends) {
            for (FeedItem item : allPosts) {
                if (checkFriendsFollowing(item)) {
                    filteredPosts.add(item);
                }
            }
            System.out.println("Friends Selected!");
        } else {
            filteredPosts.addAll(allPosts);
            System.out.println("Latest Selected!");
        }

        if (feedAdapter != null) { feedAdapter.filterList(filteredPosts); }

    }

    boolean checkFollowing(FeedItem item){
        return item.getClubName().contains("1");
    }

    boolean checkFriendsFollowing(FeedItem item){
        return item.getClubName().contains("2");
    }

    private void setupRecyclerView(){
        RecyclerView rv = binding.feedRecyclerView;

        rv.setLayoutManager(new LinearLayoutManager(this.getContext()));

        feedAdapter = new FeedAdapter(new ArrayList<>(allPosts));
        rv.setAdapter(feedAdapter);
    }


    //---------- Temporary Functions ----------

    private void setupAllPostsList() {
        if (allPosts.isEmpty()) {
            allPosts.add(new FeedItem(R.drawable.ic_launcher_foreground, "ClubName1", lorumIpsum(0), R.drawable.ic_launcher_background));
            allPosts.add(new FeedItem(R.drawable.ic_launcher_foreground, "ClubName2", lorumIpsum(1), R.drawable.ic_launcher_background));
            allPosts.add(new FeedItem(R.drawable.ic_launcher_foreground, "ClubName3", lorumIpsum(2), R.drawable.ic_launcher_background));
            allPosts.add(new FeedItem(R.drawable.ic_launcher_foreground, "ClubName4", lorumIpsum(3), R.drawable.ic_launcher_background));
            allPosts.add(new FeedItem(R.drawable.ic_launcher_foreground, "ClubName5", lorumIpsum(4), R.drawable.ic_launcher_background));
        }
    }

    String lorumIpsum(int i){
        String[] s = {
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur auctor finibus ipsum, ut feugiat justo laoreet nec. In ut semper dolor. Pellentesque condimentum lorem eget.",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer non dolor et sapien iaculis tempus id ut orci. Aliquam porttitor fermentum magna quis pharetra. Ut.",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc iaculis tortor sem, non interdum sem vulputate vitae. Integer egestas quam nec varius condimentum. Aliquam erat.",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum porttitor diam ullamcorper vehicula sodales. Vestibulum fermentum purus enim, fringilla imperdiet felis pharetra ut. Praesent id.",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas lacinia dui eget ante scelerisque, eu congue turpis viverra. In sed vulputate nunc. Sed rhoncus nulla."
        };

        return s[i];
    }
}